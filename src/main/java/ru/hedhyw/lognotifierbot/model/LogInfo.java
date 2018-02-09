package ru.hedhyw.lognotifierbot.model;

import java.util.Collections;
import java.util.List;

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

  public String getParserNames() {
    return parserName;
  }

}