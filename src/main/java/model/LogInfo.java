package model;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import log.LogParser;
import log.factory.LogEntryFactory;
import log.factory.LogParserFactory;

public class LogInfo {

  private final String parserName;
  private final String fileName;
  private final List<String> processNames;

  LogInfo(String parserName, String fileName, List<String> processNames) {
    this.parserName = parserName;
    this.fileName = fileName;
    this.processNames = Collections.unmodifiableList(processNames);
  }

  public String getFileName() {
    return fileName;
  }

  public List<String> getprocessNames() {
    return processNames;
  }

  public LogParserFactory getLogParserCreator() {

    return new LogParserFactory() {
      @Nullable
      @Override
      public LogParser createLogParser() {
        LogEntryFactory logEntryCreator =
          LogEntryFactory.getCreatorByParserName(parserName);
        if (logEntryCreator == null) return null;
        return new LogParser(fileName, processNames, logEntryCreator);
      }
    };

  }

}