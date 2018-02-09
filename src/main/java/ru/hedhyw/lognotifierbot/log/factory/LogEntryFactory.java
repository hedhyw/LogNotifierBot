package ru.hedhyw.lognotifierbot.log.factory;

import ru.hedhyw.lognotifierbot.log.model.LogEntry;
import ru.hedhyw.lognotifierbot.log.model.LogParseException;
import ru.hedhyw.lognotifierbot.model.Constants;

public interface LogEntryFactory {

  public LogEntry create(String arg0) throws LogParseException;

  public static LogEntryFactory getCreatorByParserName(String str) {
    switch (str) {
      case Constants.AUTH_LOG_PARSER_NAME:
        return new AuthLogEntryCreator();
      default:
        return null;
    }
  }

}