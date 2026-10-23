package com.mycompany.chatapppoe;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Message handling for QuickChat (PROG5121 PoE Part 2).
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Generate a 10-digit Message ID</li>
 *   <li>Validate recipient cell number and message text</li>
 *   <li>Create a message hash (firstTwoOfID : messageNumber : FIRSTLAST)</li>
 *   <li>Send / Disregard / Store (JSON) options</li>
 *   <li>Track total messages sent across the session</li>
 * </ul>
 */
public class Message {

    private String messageID;
    private String recipient;
    private String messageText;
    private final int messageNumber; // 0-based order this message was created in

    // --- Class-wide (static) tracking across all Message objects ---
    private static int messageSequence = 0;      // increments every time a Message is created
    private static int totalMessagesSent = 0;    // only counts messages the user chose to SEND
    private static final List<String> sentMessageLog = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();

    private static final String JSON_FILE_NAME = "storedMessages.json";

    // Same cell-phone rule as Login.checkCellPhoneNumber
    private static final Pattern CELL_PATTERN = Pattern.compile("^\\+27\\d{1,10}$");

    /** Used by the app: ID is random. */
    public Message() {
        this.messageNumber = messageSequence++;
        this.messageID = generateMessageID();
    }

    /** Used by unit tests so the ID and number are predictable. */
    Message(String messageID, int messageNumber) {
        this.messageNumber = messageNumber;
        this.messageID = messageID;
    }

    // ---------------------------------------------------------------
    // Message ID
    // ---------------------------------------------------------------
    private String generateMessageID() {
        Random random = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(random.nextInt(10));
        }
        return id.toString();
    }

    public String getMessageID() {
        return messageID;
    }

    /** Ensures that the message ID is not more than ten characters. */
    public boolean checkMessageID(String id) {
        return id != null && id.length() <= 10;
    }

    // ---------------------------------------------------------------
    // Recipient cell number (same rule as Login.checkCellPhoneNumber)
    // ---------------------------------------------------------------
    public String checkRecipientCell(String cellNumber) {
        boolean valid = cellNumber != null && CELL_PATTERN.matcher(cellNumber).matches();
        if (valid) {
            this.recipient = cellNumber;
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an international code. "
                + "Please correct the number and try again.";
    }

    // ---------------------------------------------------------------
    // Message text
    // ---------------------------------------------------------------
    /** Used by the app. Saves the text when it is valid. */
    public String checkMessageText(String text) {
        if (text != null && !text.trim().isEmpty() && text.length() <= 250) {
            this.messageText = text;
            return "Message sent";
        }
        return "Please enter a message of less than 250 characters.";
    }

    /** Used by the unit tests (success and failure messages). */
    public String checkMessageLength(String text) {
        if (text == null) {
            return "Message exceeds 250 characters by 0; please reduce the size.";
        }
        int length = text.length();
        if (length <= 250) {
            return "Message ready to send.";
        }
        return "Message exceeds 250 characters by " + (length - 250) + "; please reduce the size.";
    }

    // ---------------------------------------------------------------
    // Message hash: first two digits of ID : message number : FIRSTLAST
    // ---------------------------------------------------------------
    public String createMessageHash() {
        return createMessageHash(messageText);
    }

    public String createMessageHash(String text) {
        if (text == null || text.trim().isEmpty()) {
            String firstTwo = messageID != null && messageID.length() >= 2
                    ? messageID.substring(0, 2) : "00";
            return (firstTwo + ":" + messageNumber + ":EMPTY").toUpperCase();
        }
        String firstTwo = messageID != null && messageID.length() >= 2
                ? messageID.substring(0, 2) : (messageID != null ? messageID : "00");
        String[] words = text.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (firstTwo + ":" + messageNumber + ":" + first + last).toUpperCase();
    }

    // ---------------------------------------------------------------
    // Send / disregard / store
    // ---------------------------------------------------------------
    /** Asks the user what to do with the message (used by the app). */
    public String SentMessage(Scanner scanner) {
        System.out.println();
        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message to send later");

        int choice = 0;
        while (choice < 1 || choice > 3) {
            System.out.print("Choose an option: ");
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                choice = 0;
            }
            if (choice < 1 || choice > 3) {
                System.out.println("Please choose 1, 2 or 3.");
            }
        }

        String outcome = SentMessage(choice);

        if (choice == 1) {
            return outcome + "\n\n" + getDetails();
        }
        if (choice == 2) {
            System.out.println(outcome);              // "Press 0 to delete the message."
            String zero = scanner.nextLine().trim();
            return zero.equals("0") ? "Message deleted." : "Message not deleted.";
        }
        return outcome;
    }

    /** Does the work for a choice: 1 = send, 2 = disregard, 3 = store (used by the tests too). */
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                totalMessagesSent++;
                sentMessageLog.add(getDetails());
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storedMessages.add(this);
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // ---------------------------------------------------------------
    // Details / printing / totals
    // ---------------------------------------------------------------
    /** Order: Message ID, Message Hash, Recipient, Message. */
    public String getDetails() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + createMessageHash()
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    /** Returns all the messages sent while the program is running. */
    public String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (String details : sentMessageLog) {
            sb.append(details).append("\n\n");
        }
        return sb.toString();
    }

    /** Total number of messages sent. (Method name matches brief / existing tests.) */
    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // ---------------------------------------------------------------
    // JSON storage
    // ---------------------------------------------------------------
    public void storeMessage() {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            json.append("  {\n")
                .append("    \"messageID\": \"").append(esc(m.messageID)).append("\",\n")
                .append("    \"messageHash\": \"").append(esc(m.createMessageHash())).append("\",\n")
                .append("    \"recipient\": \"").append(esc(m.recipient)).append("\",\n")
                .append("    \"message\": \"").append(esc(m.messageText)).append("\"\n")
                .append("  }").append(i < storedMessages.size() - 1 ? "," : "").append("\n");
        }
        json.append("]");

        try (FileWriter writer = new FileWriter(JSON_FILE_NAME)) {
            writer.write(json.toString());
        } catch (IOException e) {
            System.out.println("Could not write JSON file: " + e.getMessage());
        }
    }

    private static String esc(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
    }

    /** For unit tests: clears all the shared counters and lists. */
    public static void resetAll() {
        messageSequence = 0;
        totalMessagesSent = 0;
        sentMessageLog.clear();
        storedMessages.clear();
    }
}
