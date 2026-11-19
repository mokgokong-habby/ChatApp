package com.mycompany.chatapppoe;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/** Part 3: stored messages are written to and read back from a JSON file. */
public class MessageStorageTest {

    @Test
    void testStoredMessagesSurviveASaveAndLoad() throws IOException {
        Path file = Files.createTempFile("storedMessages", ".json");

        MessageManager original = new MessageManager();
        original.addMessage("0838884561", "08:2:WHERETIME.", "Kyle Naidoo", "+27838884567",
                "Where are you? You are late! I have asked you to be on time.", MessageManager.FLAG_STORED);
        original.addMessage("0838884565", "08:5:OK,YOU.", "Kyle Naidoo", "+27838884567",
                "Ok, I am leaving without you.", MessageManager.FLAG_STORED);
        original.addMessage("0834557896", "08:1:DIDCAKE?", "Kyle Naidoo", "+27834557896",
                "Did you get the cake?", MessageManager.FLAG_SENT);

        MessageStorage.saveStoredMessages(file.toString(), original);

        MessageManager reloaded = new MessageManager();
        MessageStorage.loadStoredMessages(file.toString(), reloaded);

        // Only the two stored messages are written to the file.
        assertEquals(Arrays.asList("Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."), reloaded.getStoredMessages());
        assertEquals(Arrays.asList("0838884561", "0838884565"), reloaded.getMessageIDs());
        assertEquals(Arrays.asList("08:2:WHERETIME.", "08:5:OK,YOU."), reloaded.getMessageHashes());

        Files.deleteIfExists(file);
    }

    @Test
    void testQuotesAndNewLinesAreEscapedCorrectly() throws IOException {
        Path file = Files.createTempFile("storedMessages", ".json");

        MessageManager original = new MessageManager();
        original.addMessage("1000000001", "10:1:SHESAIDHI", "Kyle Naidoo", "+27834557896",
                "She said \"hi\"\nthen left \\ quickly", MessageManager.FLAG_STORED);
        MessageStorage.saveStoredMessages(file.toString(), original);

        MessageManager reloaded = new MessageManager();
        MessageStorage.loadStoredMessages(file.toString(), reloaded);

        assertEquals(Arrays.asList("She said \"hi\"\nthen left \\ quickly"), reloaded.getStoredMessages());

        Files.deleteIfExists(file);
    }

    @Test
    void testLoadingAMissingFileLeavesArraysEmpty() throws IOException {
        MessageManager manager = new MessageManager();
        MessageStorage.loadStoredMessages("this-file-does-not-exist.json", manager);
        assertEquals(0, manager.getStoredMessages().size());
    }
}
