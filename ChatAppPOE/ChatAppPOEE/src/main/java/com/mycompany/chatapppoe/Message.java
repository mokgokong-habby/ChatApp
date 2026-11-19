package com.mycompany.chatapppoe;

import java.util.regex.Pattern;

/**
 * Part 2 - A single chat message and its validation rules.
 *
 * Reference: regular expression syntax follows the java.util.regex.Pattern
 * API documentation.
 * https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
 */
public class Message {

    public static final int MAX_LENGTH = 250;

    // Counts the messages the user actually chose to send this session.
    private static int totalMessagesSent = 0;

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;

    public Message(String messageID, int messageNumber, String recipient, String messageText) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageHash = createMessageHash(messageID, messageNumber, messageText);
    }

    /** The message ID must not be empty and may be no more than ten characters long. */
    public boolean checkMessageID() {
        return messageID != null && !messageID.isEmpty() && messageID.length() <= 10;
    }

    /** International code (+) followed by the number, no more than ten digits after the code. */
    public static boolean isValidRecipientCell(String cell) {
        return cell != null && Pattern.matches("^\\+\\d{1,3}\\d{1,10}$", cell);
    }

    public boolean checkRecipientCell() {
        return isValidRecipientCell(recipient);
    }

    /** Messages may be at most 250 characters long. */
    public static String checkMessageLength(String text) {
        if (text.length() <= MAX_LENGTH) {
            return "Message ready to send.";
        }
        int excess = text.length() - MAX_LENGTH;
        return "Message exceeds 250 characters by " + excess + ", please reduce size.";
    }

    /**
     * Hash = first two characters of the ID, ":", the message number, ":",
     * then the first and last word of the message joined together in capitals.
     */
    public static String createMessageHash(String messageID, int messageNumber, String messageText) {
        String[] words = messageText.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String idStart = messageID.length() >= 2 ? messageID.substring(0, 2) : messageID;
        return (idStart + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    /**
     * Applies the user's choice: 1 = send, 2 = disregard, 3 = store.
     * Only sending increases the total of messages sent.
     */
    public String sentMessage(String choice) {
        switch (choice) {
            case "1":
                totalMessagesSent++;
                return "Message successfully sent.";
            case "2":
                return "Press 0 to delete message.";
            case "3":
                return "Message successfully stored.";
            default:
                return "Invalid choice.";
        }
    }

    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    /** Resets the counter (used by the unit tests so they do not affect each other). */
    public static void resetTotalMessages() {
        totalMessagesSent = 0;
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }
}
