package com.mycompany.chatapppoe;

import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Part 3 unit tests using the test data from the brief.
 *
 * Test data messages 1-5 are loaded as "developer entries": the developer
 * supplies the message ID, because real IDs are random. Message 4 uses the
 * developer value 0838884567 as its ID, as in the brief.
 */
public class MessageManagerTest {

    private static final String MSG1 = "Did you get the cake?";
    private static final String MSG2 = "Where are you? You are late! I have asked you to be on time.";
    private static final String MSG3 = "Yohoooo, I am at your gate.";
    private static final String MSG4 = "It is dinner time!";
    private static final String MSG5 = "Ok, I am leaving without you.";

    private MessageManager manager;

    @BeforeEach
    void setUp() {
        manager = new MessageManager();
        addDeveloperEntry("0834557896", 1, "+27834557896", MSG1, MessageManager.FLAG_SENT);
        addDeveloperEntry("0838884561", 2, "+27838884567", MSG2, MessageManager.FLAG_STORED);
        addDeveloperEntry("0834484567", 3, "+27834484567", MSG3, MessageManager.FLAG_DISREGARD);
        addDeveloperEntry("0838884567", 4, "Developer", MSG4, MessageManager.FLAG_SENT);
        addDeveloperEntry("0838884565", 5, "+27838884567", MSG5, MessageManager.FLAG_STORED);
    }

    private void addDeveloperEntry(String id, int number, String recipient, String text, String flag) {
        String hash = Message.createMessageHash(id, number, text);
        manager.addMessage(id, hash, "Developer", recipient, text, flag);
    }

    // ---------------------------------------------------------------
    // Test: (assertEquals) - rows from the brief
    // ---------------------------------------------------------------

    /** Sent Messages array correctly populated (test data messages 1-4). */
    @Test
    void testSentMessagesArrayCorrectlyPopulated() {
        assertEquals(Arrays.asList(MSG1, MSG4), manager.getSentMessages());
    }

    /** Display the longest message (test data messages 1-4). */
    @Test
    void testDisplayTheLongestMessage() {
        assertEquals("Where are you? You are late! I have asked you to be on time.",
                manager.getLongestMessage());
    }

    /** Search for messageID "0838884567" (message 4). */
    @Test
    void testSearchForMessageID() {
        assertEquals("It is dinner time!", manager.searchByMessageID("0838884567"));
    }

    /** Search all the messages sent or stored regarding a particular recipient. */
    @Test
    void testSearchAllMessagesForARecipient() {
        assertEquals(Arrays.asList(MSG2, MSG5), manager.searchByRecipient("+27838884567"));
    }

    /** Delete a message using a message hash (Test Message 2). */
    @Test
    void testDeleteAMessageUsingAMessageHash() {
        String hashOfMessage2 = manager.getMessageHashes().get(1);
        assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                manager.deleteByHash(hashOfMessage2));
    }

    /** Display Report: hash, recipient and message of all the sent messages. */
    @Test
    void testDisplayReport() {
        String expected = "Message Hash: 08:1:DIDCAKE?\n"
                + "Recipient: +27834557896\n"
                + "Message: Did you get the cake?\n"
                + "\n"
                + "Message Hash: 08:4:ITTIME!\n"
                + "Recipient: Developer\n"
                + "Message: It is dinner time!";
        assertEquals(expected, manager.getSentReport());
    }

    // ---------------------------------------------------------------
    // Extra checks for the remaining arrays and features
    // ---------------------------------------------------------------

    @Test
    void testDisregardedMessagesArrayCorrectlyPopulated() {
        assertEquals(Arrays.asList(MSG3), manager.getDisregardedMessages());
    }

    @Test
    void testStoredMessagesArrayCorrectlyPopulated() {
        assertEquals(Arrays.asList(MSG2, MSG5), manager.getStoredMessages());
    }

    @Test
    void testMessageIDAndHashArraysCorrectlyPopulated() {
        assertEquals(Arrays.asList("0834557896", "0838884561", "0834484567", "0838884567", "0838884565"),
                manager.getMessageIDs());
        assertEquals(Arrays.asList("08:1:DIDCAKE?", "08:2:WHERETIME.", "08:3:YOHOOOO,GATE.",
                "08:4:ITTIME!", "08:5:OK,YOU."), manager.getMessageHashes());
    }

    @Test
    void testLongestStoredMessage() {
        assertEquals(MSG2, manager.getLongestStoredMessage());
    }

    @Test
    void testDeleteRemovesMessageFromAllArrays() {
        manager.deleteByHash("08:2:WHERETIME.");
        assertEquals(Arrays.asList(MSG5), manager.getStoredMessages());
        assertEquals(4, manager.getMessageIDs().size());
        assertEquals(4, manager.getMessageTexts().size());
    }

    @Test
    void testDeleteWithUnknownHash() {
        assertEquals("Message hash not found.", manager.deleteByHash("00:0:NOPE"));
    }

    @Test
    void testSearchForUnknownMessageID() {
        assertEquals("Message ID not found.", manager.searchByMessageID("9999999999"));
    }

    @Test
    void testStoredSendersAndRecipients() {
        assertEquals(Arrays.asList("Sender: Developer | Recipient: +27838884567",
                "Sender: Developer | Recipient: +27838884567"),
                manager.getStoredSendersAndRecipients());
    }

    @Test
    void testStoredReport() {
        String expected = "Message Hash: 08:2:WHERETIME.\n"
                + "Recipient: +27838884567\n"
                + "Message: Where are you? You are late! I have asked you to be on time.\n"
                + "\n"
                + "Message Hash: 08:5:OK,YOU.\n"
                + "Recipient: +27838884567\n"
                + "Message: Ok, I am leaving without you.";
        assertEquals(expected, manager.getStoredReport());
    }
}
