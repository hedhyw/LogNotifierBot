import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import log.LogParser;
import log.factory.LogParserFactory;
import model.BotInfo;
import model.Constants;
import model.LogInfo;
import model.AppProperties;

public class App {

    private static void configure(File paramsDir) {
        if (!paramsDir.mkdir()) {
            System.err.println(String.format("Could not create %s directory.",
                paramsDir.getAbsolutePath()));
            return;
        }
        InputStream res = App.class.getResourceAsStream(
            Constants.PROPERTIES_XML_RES);
        try {
            java.nio.file.Files.copy(res,
               Paths.get(Constants.PROPERTIES_XML_FILE),
               StandardCopyOption.REPLACE_EXISTING);
        } catch(IOException ex) {
            System.err.println(String.format("Could not create %s file.",
                Constants.PROPERTIES_XML_FILE));
            return;
        }
        System.out.println(String.format(
            "For continue configuration, edit %s file.",
            Constants.PROPERTIES_XML_FILE));
    }

    public static void main(String[] args) {
        File paramsDir = new File(Constants.APP_PARAMS_DIRECTORY);
        if (!paramsDir.exists()) {
            configure(paramsDir);
            return;
        }

        AppProperties prop = null;
        try {
            prop = new AppProperties();
        } catch (IOException ex) {
            System.err.println(String.format("Could not read %s file.",
                Constants.PROPERTIES_XML_FILE));
        } catch (SAXException | ParserConfigurationException ex) {
            System.err.println(String.format("Could not parse %s file.",
                Constants.PROPERTIES_XML_FILE));
        }
        if (prop == null) return;
        BotInfo botInfo = prop.getBotInfo();
        List<String> superUsers = prop.getSuperUsers();

        List<LogInfo> logs = prop.getLogs();
        List<LogParser> parsers = new ArrayList<LogParser>();
        for (LogInfo log : logs) {
            LogParserFactory creator = log.getLogParserCreator();
            LogParser parser = creator.createLogParser();
            if (parser != null) parsers.add(parser);
        }

        if (superUsers.isEmpty()) {
            System.err.println("There are no super users.");
            System.err.println(
                String.format("Check %s file.",
                    Constants.PROPERTIES_XML_FILE));
            return;
        }
        if (parsers.isEmpty()) {
            System.err.println("There are no log parsers.");
            System.err.println(
                String.format("Check %s file.",
                    Constants.PROPERTIES_XML_FILE));
            return;
        }

        new LogNotifyerBot(botInfo, parsers, superUsers);

        try {
            while (true) Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }
}
