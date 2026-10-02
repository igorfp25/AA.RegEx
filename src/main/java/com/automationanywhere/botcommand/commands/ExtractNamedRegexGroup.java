package com.automationanywhere.botcommand.commands;

import static com.automationanywhere.commandsdk.model.DataType.STRING;
import static com.automationanywhere.commandsdk.model.DataType.BOOLEAN;
import static com.automationanywhere.commandsdk.model.DataType.LIST;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.ListValue;
import com.automationanywhere.botcommand.data.impl.StringValue;

import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;
import com.automationanywhere.commandsdk.annotations.Execute;
import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;

import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.annotations.rules.VariableType;

import com.automationanywhere.commandsdk.model.AttributeType;


@BotCommand
@CommandPkg(
        name = "extractNamedRegexGroup",
        label = "Extract named RegEx group",
        node_label = "Extract RegEx group {{groupName}}",
        description = "Extracts all matches for a specified named group from a RegEx pattern.",
        return_required = true,
        return_label = "List of matches",
        return_description = "list of strings",
        return_name = "listMatches",
        return_type = LIST,
        return_sub_type = STRING,
        return_Direct = true,
        documentation_url = "",
        icon = "RegEx.svg"
)
public class ExtractNamedRegexGroup {

    @Execute
    public ListValue<String> action(

            @Idx(index = "1", type = AttributeType.TEXT)
            @VariableType(STRING)
            @Pkg(label = "Input string") @NotEmpty String input,

            @Idx(index = "2", type = AttributeType.TEXT)
            @Pkg(label = "Regex pattern", description = "Must contain named group") @NotEmpty String pattern,

            @Idx(index = "3", type = AttributeType.TEXT)
            @Pkg(label = "Group name", description = "The named group to extract, must be included in the pattern") @NotEmpty String groupName,

            @Idx(index = "4", type = AttributeType.CHECKBOX)
            @VariableType(BOOLEAN)
            @Pkg(label = "Match case", default_value = "false", default_value_type = BOOLEAN) Boolean matchCase
    ) {

        ListValue<String> output = new ListValue<String>();
        List<Value> results = new ArrayList<Value>();

        if (input == null || pattern == null || groupName == null) {
            output.set(results);
            return output;
        }

        boolean isCaseSensitive = (matchCase == null) ? true : matchCase;
        int flags = isCaseSensitive ? 0 : Pattern.CASE_INSENSITIVE;

        try {
            Pattern compiled = Pattern.compile(pattern, flags);
            Matcher matcher = compiled.matcher(input);

            while (matcher.find()) {
                try {
                    String value = matcher.group(groupName);
                    results.add(new StringValue(value));
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Group name '" + groupName + "' does not exist in the pattern.", e
                    );
                }
            }

            output.set(results);
            return output;

        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(
                    "Invalid regex pattern: '" + pattern + "'. Error: " + e.getMessage(), e
            );
        }
    }
}