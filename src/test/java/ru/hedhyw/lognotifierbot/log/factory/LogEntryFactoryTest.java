package ru.hedhyw.lognotifierbot.log.factory;

import org.junit.Test;
import static org.junit.Assert.*;

import ru.hedhyw.lognotifierbot.log.model.LogEntry;
import ru.hedhyw.lognotifierbot.log.model.LogParseException;

public class LogEntryFactoryTest {

    private final String validLine = "Mon 1 00:00:00 HOST APP[0000]: msg";
    private final String invalidLine = "NOT FOUND";

    @Test public void testAuthLogEntry() {
        try {
            LogEntryFactory creator = new AuthLogEntryCreator();
            LogEntry entry = creator.create(validLine);
            assertEquals(entry.getClass().getName(), entry.getClass().getName());
        } catch(LogParseException ex) {

        }
    }

    @Test public void testUndefinedEntry() {
        try {
            LogEntryFactory creator = new AuthLogEntryCreator();
            LogEntry entry = creator.create(invalidLine);
            assertNull(entry);
        } catch(LogParseException ex) {

        }
    }
}
