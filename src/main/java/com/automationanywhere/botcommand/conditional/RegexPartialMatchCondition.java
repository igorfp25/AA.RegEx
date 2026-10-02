/*
 * Copyright (c) 2020 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */

/**
 * 
 */
package com.automationanywhere.botcommand.conditional;

import static com.automationanywhere.commandsdk.annotations.BotCommand.CommandType.Condition;
import static com.automationanywhere.commandsdk.model.DataType.STRING;
import static com.automationanywhere.commandsdk.model.DataType.BOOLEAN;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.ConditionTest;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.annotations.rules.VariableType;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;

/**
 * 
 * Condition to check if a string contains a substring that matches a given regex pattern,
 * with optional case sensitivity.
 * 
 */
@BotCommand(commandType = Condition)
@CommandPkg(name = "regexPartialMatchCondition", 
    label = "[[RegexPartialMatchCondition.label]]",
    description = "[[RegexPartialMatchCondition.description]]",
    node_label = "{{input}} partially matches pattern {{pattern}}"
)
public class RegexPartialMatchCondition {

    @ConditionTest
    public Boolean validate(
            @Idx(index = "1", type = AttributeType.TEXT)
            @VariableType(STRING)
            @Pkg(label = "Input string") @NotEmpty String input, //Input String

            @Idx(index = "2", type = AttributeType.TEXT)
            @Pkg(label = "Regex pattern") @NotEmpty String pattern, //RegEx pattern

            @Idx(index = "3", type = AttributeType.CHECKBOX)
            @VariableType(BOOLEAN)
            @Pkg(label = "Match case", default_value = "false", default_value_type = DataType.BOOLEAN) Boolean matchCase //MatchCase
    ) {
        if (input == null || pattern == null) {
            return false;
        }

        // Default to true if null
        boolean isCaseSensitive = (matchCase == null) ? true : matchCase;

        int flags = isCaseSensitive ? 0 : Pattern.CASE_INSENSITIVE;

		try {
    		return Pattern.compile(pattern, flags).matcher(input).find();
		} catch (PatternSyntaxException e) { //Chatch if RegEx pattern is invalid
			throw new IllegalArgumentException(
    			"Invalid regex pattern: '" + pattern + "'. Error: " + e.getMessage()
			);
		}

    }
}