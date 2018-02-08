package model;

import java.nio.file.Paths;

public class Constants {

  public static final String APP_PARAMS_DIRECTORY =
    Paths.get(System.getProperty("user.home"), ".LogNotifyerBot").toString();
  public static final String CHATS_XML_FILE =
    Paths.get(APP_PARAMS_DIRECTORY, "chats.auto.xml").toString();
  public static final String PROPERTIES_XML_FILE =
    Paths.get(APP_PARAMS_DIRECTORY, "properties.xml").toString();
  public static final String PROPERTIES_XML_RES = "properties.xml";
  public static final String AUTH_LOG_PARSER_NAME = "AuthLog";
  public static final String NOT_DEFINED = "Not defined.";
  public static final int LOG_THREAD_SLEEP_TIME = 200;
}