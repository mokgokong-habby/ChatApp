package com.mycompany.chatapppoe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ChatApp POE - Parts 1, 2 and 3.
 *
 * Part 1: register and log in.
 * Part 2: create and send / disregard / store messages.
 * Part 3: arrays, JSON storage and the "Stored Messages" menu option.
 */
public class ChatAppPOE {

    private static final String JSON_FILE = "storedMessages.json";

    // Numbers the messages created this session (used in the message hash).
    private static int messageNumber = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        // ---------------- Part 1: registration and login ----------------
        System.out.println("=== Registration ===");
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        String username;
        do {
            System.out.print("Enter a username (contains '_', max 5 characters): ");
            username = scanner.nextLine();
            login.checkUserName(username);
            System.out.println(login.getUsernameMessage());
        } while (!login.checkUserName(username));

        String password;
        do {
            System.out.print("Enter a password (8+ chars, capital, number, special char): ");
            password = scanner.nextLine();
            login.checkPasswordComplexity(password);
            System.out.println(login.getPasswordMessage());
        } while (!login.checkPasswordComplexity(password));

        String cellNumber;
        do {
            System.out.print("Enter your South African cell number (e.g. +27838968976): ");
            cellNumber = scanner.nextLine();
            login.checkCellPhoneNumber(cellNumber);
            System.out.println(login.getCellPhoneMessage());
        } while (!login.checkCellPhoneNumber(cellNumber));

        login.registerUser(username, password, cellNumber, firstName, lastName);

        System.out.println();
        System.out.println("=== Login ===");
        boolean loggedIn;
        do {
            System.out.print("Enter your username: ");
            String loginUsername = scanner.nextLine();
            System.out.print("Enter your password: ");
            String loginPassword = scanner.nextLine();
            loggedIn = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.getLoginMessage());
        } while (!loggedIn);

        String senderName = firstName + " " + lastName;

        // ---------------- Part 3: load previously stored messages ----------------
        MessageManager manager = new MessageManager();
        try {
            MessageStorage.loadStoredMessages(JSON_FILE, manager);
        } catch (IOException | RuntimeException e) {
            System.out.println("Could not read the stored messages file: " + e.getMessage());
        }

        // ---------------- Main menu ----------------
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("Welcome to ChatApp!");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> sendMessages(scanner, manager, senderName);

                case "2" -> System.out.println("Coming Soon.");

                case "3" -> {
                    running = false;
                    System.out.println();
                    System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
                    System.out.println("Goodbye!");
                }

                case "4" -> storedMessagesMenu(scanner, manager);

                default -> System.out.println("Please select 1, 2, 3, or 4.");
            }
        }
        scanner.close();
    }

    // ------------------------------------------------------------------
    // Option 1: send messages (Part 2 behaviour feeding the Part 3 arrays)
    // ------------------------------------------------------------------
    private static void sendMessages(Scanner scanner, MessageManager manager, String senderName) {
        int numMessages = readPositiveNumber(scanner, "How many messages do you want to enter? ");

        for (int created = 0; created < numMessages; created++) {
            String recipient;
            do {
                System.out.print("Enter the recipient's cell number (e.g. +27834557896): ");
                recipient = scanner.nextLine().trim();
                if (!Message.isValidRecipientCell(recipient)) {
                    System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                }
            } while (!Message.isValidRecipientCell(recipient));

            String text;
            while (true) {
                System.out.print("Enter your message (max 250 characters): ");
                text = scanner.nextLine();
                if (text.trim().isEmpty()) {
                    System.out.println("A message cannot be empty.");
                    continue;
                }
                String lengthResult = Message.checkMessageLength(text);
                System.out.println(lengthResult);
                if (text.length() <= Message.MAX_LENGTH) {
                    break;
                }
            }

            // Generate a unique 10-digit message ID.
            String id;
            do {
                id = String.valueOf(ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L));
            } while (manager.getMessageIDs().contains(id));

            messageNumber++;
            Message message = new Message(id, messageNumber, recipient, text);
            System.out.println("Message ID generated: " + message.getMessageID());
            System.out.println("Message Hash: " + message.getMessageHash());

            String action;
            while (true) {
                System.out.println("1) Send Message");
                System.out.println("2) Disregard Message");
                System.out.println("3) Store Message to send later");
                System.out.print("Choose an option: ");
                action = scanner.nextLine().trim();
                if (action.equals("1") || action.equals("2") || action.equals("3")) {
                    break;
                }
                System.out.println("Please select 1, 2, or 3.");
            }

            System.out.println(message.sentMessage(action));

            String flag = switch (action) {
                case "1" -> MessageManager.FLAG_SENT;
                case "2" -> MessageManager.FLAG_DISREGARD;
                default -> MessageManager.FLAG_STORED;
            };
            manager.addMessage(message.getMessageID(), message.getMessageHash(), senderName,
                    message.getRecipient(), message.getMessageText(), flag);

            if (flag.equals(MessageManager.FLAG_STORED)) {
                saveStored(manager);
            }
            if (flag.equals(MessageManager.FLAG_SENT)) {
                System.out.println("Message ID: " + message.getMessageID());
                System.out.println("Message Hash: " + message.getMessageHash());
                System.out.println("Recipient: " + message.getRecipient());
                System.out.println("Message: " + message.getMessageText());
            }
        }

        System.out.println();
        System.out.println("Total number of messages sent: " + Message.returnTotalMessages());
    }

    // ------------------------------------------------------------------
    // Option 4: Stored Messages (Part 3)
    // ------------------------------------------------------------------
    private static void storedMessagesMenu(Scanner scanner, MessageManager manager) {
        boolean inMenu = true;
        while (inMenu) {
            System.out.println();
            System.out.println("=== Stored Messages ===");
            System.out.println("a) Display the sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search for all messages stored for a recipient");
            System.out.println("e) Delete a message using the message hash");
            System.out.println("f) Display a report of all stored messages");
            System.out.println("g) Back to main menu");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a" -> {
                    ArrayList<String> lines = manager.getStoredSendersAndRecipients();
                    if (lines.isEmpty()) {
                        System.out.println("No stored messages.");
                    }
                    for (String line : lines) {
                        System.out.println(line);
                    }
                }

                case "b" -> System.out.println("Longest stored message: " + manager.getLongestStoredMessage());

                case "c" -> {
                    System.out.print("Enter the message ID: ");
                    System.out.println(manager.describeMessageByID(scanner.nextLine().trim()));
                }

                case "d" -> {
                    System.out.print("Enter the recipient's cell number: ");
                    ArrayList<String> results = manager.searchByRecipient(scanner.nextLine().trim());
                    if (results.isEmpty()) {
                        System.out.println("No messages found for that recipient.");
                    }
                    for (String result : results) {
                        System.out.println("\"" + result + "\"");
                    }
                }

                case "e" -> {
                    System.out.print("Enter the message hash: ");
                    System.out.println(manager.deleteByHash(scanner.nextLine().trim()));
                    saveStored(manager);
                }

                case "f" -> System.out.println(manager.getStoredReport());

                case "g" -> inMenu = false;

                default -> System.out.println("Please select a, b, c, d, e, f, or g.");
            }
        }
    }

    private static void saveStored(MessageManager manager) {
        try {
            MessageStorage.saveStoredMessages(JSON_FILE, manager);
        } catch (IOException e) {
            System.out.println("Could not save the stored messages file: " + e.getMessage());
        }
    }

    private static int readPositiveNumber(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int number = Integer.parseInt(input);
                if (number > 0) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // fall through to the message below
            }
            System.out.println("Please enter a whole number greater than 0.");
        }
    }
}
