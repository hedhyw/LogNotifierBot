package log.factory;

import log.model.AuthLogEntry;
import log.model.LogEntry;
import log.model.LogParseException;

public class AuthLogEntryCreator implements LogEntryFactory {

  public LogEntry create(String arg0) throws LogParseException {
    return new AuthLogEntry(arg0);
  }

}