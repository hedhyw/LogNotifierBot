package log.factory;

import log.model.LogEntry;
import log.model.LogParseException;
import model.Constants;

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