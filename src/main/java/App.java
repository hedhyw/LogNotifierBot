import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import ru.hedhyw.lognotifierbot.LogNotifierBot;
import ru.hedhyw.lognotifierbot.log.LogParser;
import ru.hedhyw.lognotifierbot.model.AppProperties;
import ru.hedhyw.lognotifierbot.model.BotInfo;
import ru.hedhyw.lognotifierbot.model.Constants;
import ru.hedhyw.lognotifierbot.model.LogInfo;

public class App {

    public static void main(String[] args) {

        if (!AppProperties.isPropertiesFileExist()) {
            if (AppProperties.createPropertiesSkeleton()) {
                System.out.println(String.format(
                    "For continue configuration, edit %s file.",
                    Constants.PROPERTIES_XML_FILE));
            } else {
                System.err.println(String.format("Could not create %s file.",
                    Constants.PROPERTIES_XML_FILE));
            }
            return;
        }

        AppProperties prop = null;
        try {
            prop = AppProperties.load();
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
            LogParser parser = LogParser.of(log);
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

        LogNotifierBot bot =
            new LogNotifierBot(botInfo, parsers, superUsers);
        bot.start();
        try {
            while (true) Thread.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }
}
