package ru.hedhyw.lognotifierbot.log.factory;

import ru.hedhyw.lognotifierbot.log.model.AuthLogEntry;
import ru.hedhyw.lognotifierbot.log.model.LogEntry;
import ru.hedhyw.lognotifierbot.log.model.LogParseException;

public class AuthLogEntryCreator implements LogEntryFactory {

  public LogEntry create(String arg0) throws LogParseException {
    return new AuthLogEntry(arg0);
  }

}