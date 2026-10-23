package com.mycompany.chatapppoe;

import java.util.regex.Pattern;

/**
 * Handles user registration and login for QuickChat (PROG5121 PoE Part 1).
 * <p>
 * Validation rules:
 * <ul>
 *   <li>Username: contains an underscore and is at most 5 characters long</li>
 *   <li>Password: at least 8 characters, one capital letter, one digit, one special character</li>
 *   <li>Cell phone: South African number with international code (+27) followed by up to 10 digits</li>
 * </ul>
 */
public class Login {

    private String storedUsername;
    private String storedPassword;
    private String storedCellPhone;
    private String firstName;
    private String lastName;
    private boolean registered;
    private boolean loggedIn;
    private String lastCellPhoneMessage = "";
    private String lastLoginMessage = "";

    // Regex source: South African mobile numbers with international dialling code (+27)
    // Pattern allows +27 followed by 1-10 digits (covers common 9-digit national numbers).
    private static final Pattern CELL_PATTERN = Pattern.compile("^\\+27\\d{1,10}$");

    // ------------------------------------------------------------------
    // Validation helpers
    // ------------------------------------------------------------------

    /**
     * Username must contain an underscore and be no more than five characters.
     */
    public boolean checkUserName(String username) {
        return username != null
                && username.contains("_")
                && username.length() <= 5;
    }

    /**
     * Password must be at least 8 characters and contain a capital letter,
     * a digit, and a special character.
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }
        return hasUpper && hasDigit && hasSpecial;
    }

    /**
     * Cell number must start with +27 and be followed by 1-10 digits.
     * (Same rule used by Message.checkRecipientCell.)
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        return cellPhoneNumber != null && CELL_PATTERN.matcher(cellPhoneNumber).matches();
    }

    // ------------------------------------------------------------------
    // Registration
    // ------------------------------------------------------------------

    /**
     * Validates all three credentials and, on success, stores the user details.
     *
     * @return success message or the first validation failure message
     */
    public String registerUser(String username, String password, String cellPhoneNumber,
                               String firstName, String lastName) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains "
                    + "an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains "
                    + "at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            lastCellPhoneMessage = "Cell phone number incorrectly formatted or does not contain international code.";
            return lastCellPhoneMessage;
        }

        this.storedUsername = username;
        this.storedPassword = password;
        this.storedCellPhone = cellPhoneNumber;
        this.firstName = firstName != null ? firstName : "";
        this.lastName = lastName != null ? lastName : "";
        this.registered = true;
        this.lastCellPhoneMessage = "Cell phone number successfully captured.";

        return "Username successfully captured\n"
                + "Password successfully captured\n"
                + "Cell phone number successfully added\n"
                + "User registered successfully.";
    }

    /**
     * Message produced by the most recent cell-phone validation during registration.
     */
    public String getCellPhoneMessage() {
        return lastCellPhoneMessage;
    }

    public boolean isRegistered() {
        return registered;
    }

    // ------------------------------------------------------------------
    // Login
    // ------------------------------------------------------------------

    /**
     * Compares the supplied credentials with the stored registration details.
     *
     * @return true only when both username and password match a registered user
     */
    public boolean loginUser(String username, String password) {
        if (!registered || storedUsername == null || storedPassword == null) {
            loggedIn = false;
            lastLoginMessage = "Username or password incorrect, please try again.";
            return false;
        }
        loggedIn = storedUsername.equals(username) && storedPassword.equals(password);
        if (loggedIn) {
            lastLoginMessage = "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            lastLoginMessage = "Username or password incorrect, please try again.";
        }
        return loggedIn;
    }

    /**
     * Human-readable result of the most recent login attempt.
     */
    public String getLoginMessage() {
        return lastLoginMessage;
    }

    /**
     * Convenience method that performs login and returns the status message.
     */
    public String returnLoginStatus(String username, String password) {
        loginUser(username, password);
        return lastLoginMessage;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    // Getters used by tests / optional UI
    public String getStoredUsername() {
        return storedUsername;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
