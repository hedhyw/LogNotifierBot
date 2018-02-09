package ru.hedhyw.lognotifierbot.log.model;

import java.util.ArrayList;

public interface OnNewLogsReceivedListener {

  public void logsReceived(ArrayList<LogEntry> logs);

}