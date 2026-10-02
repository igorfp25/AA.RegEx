package com.automationanywhere.botcommand.commands;

import com.automationanywhere.bot.service.GlobalSessionContext;
import com.automationanywhere.botcommand.BotCommand;
import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.exception.BotCommandException;
import com.automationanywhere.commandsdk.i18n.Messages;
import com.automationanywhere.commandsdk.i18n.MessagesFactory;
import java.lang.Boolean;
import java.lang.ClassCastException;
import java.lang.Deprecated;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ExtractNamedRegexGroupCommand implements BotCommand {
  private static final Logger logger = LogManager.getLogger(ExtractNamedRegexGroupCommand.class);

  private static final Messages MESSAGES_GENERIC = MessagesFactory.getMessages("com.automationanywhere.commandsdk.generic.messages");

  @Deprecated
  public Optional<Value> execute(Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return execute(null, parameters, sessionMap);
  }

  public Optional<Value> execute(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    logger.traceEntry(() -> parameters != null ? parameters.entrySet().stream().filter(en -> !Arrays.asList( new String[] {}).contains(en.getKey()) && en.getValue() != null).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)).toString() : null, ()-> sessionMap != null ?sessionMap.toString() : null);
    ExtractNamedRegexGroup command = new ExtractNamedRegexGroup();
    HashMap<String, Object> convertedParameters = new HashMap<String, Object>();
    if(parameters.containsKey("input") && parameters.get("input") != null && parameters.get("input").get() != null) {
      convertedParameters.put("input", parameters.get("input").get());
      if(convertedParameters.get("input") !=null && !(convertedParameters.get("input") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","input", "String", parameters.get("input").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("input") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","input"));
    }

    if(parameters.containsKey("pattern") && parameters.get("pattern") != null && parameters.get("pattern").get() != null) {
      convertedParameters.put("pattern", parameters.get("pattern").get());
      if(convertedParameters.get("pattern") !=null && !(convertedParameters.get("pattern") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","pattern", "String", parameters.get("pattern").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("pattern") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","pattern"));
    }

    if(parameters.containsKey("groupName") && parameters.get("groupName") != null && parameters.get("groupName").get() != null) {
      convertedParameters.put("groupName", parameters.get("groupName").get());
      if(convertedParameters.get("groupName") !=null && !(convertedParameters.get("groupName") instanceof String)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","groupName", "String", parameters.get("groupName").get().getClass().getSimpleName()));
      }
    }
    if(convertedParameters.get("groupName") == null) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.validation.notEmpty","groupName"));
    }

    if(parameters.containsKey("matchCase") && parameters.get("matchCase") != null && parameters.get("matchCase").get() != null) {
      convertedParameters.put("matchCase", parameters.get("matchCase").get());
      if(convertedParameters.get("matchCase") !=null && !(convertedParameters.get("matchCase") instanceof Boolean)) {
        throw new BotCommandException(MESSAGES_GENERIC.getString("generic.UnexpectedTypeReceived","matchCase", "Boolean", parameters.get("matchCase").get().getClass().getSimpleName()));
      }
    }

    try {
      Optional<Value> result =  Optional.ofNullable(command.action((String)convertedParameters.get("input"),(String)convertedParameters.get("pattern"),(String)convertedParameters.get("groupName"),(Boolean)convertedParameters.get("matchCase")));
      return logger.traceExit(result);
    }
    catch (ClassCastException e) {
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.IllegalParameters","action"));
    }
    catch (BotCommandException e) {
      logger.fatal(e.getMessage(),e);
      throw e;
    }
    catch (Throwable e) {
      logger.fatal(e.getMessage(),e);
      throw new BotCommandException(MESSAGES_GENERIC.getString("generic.NotBotCommandException",e.getMessage()),e);
    }
  }

  public Map<String, Value> executeAndReturnMany(GlobalSessionContext globalSessionContext,
      Map<String, Value> parameters, Map<String, Object> sessionMap) {
    return null;
  }
}
