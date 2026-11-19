package com.mycompany.chatapppoe;

import java.util.regex.Pattern;

/**
 * Part 1 - Registration and login feature.
 *
 * Models a single user account and provides the validation, registration
 * and login behaviour required by the task brief. Each check method also
 * has a matching "message" method, so both the condition/message tables
 * and the unit test tables in the brief can be satisfied directly.
 *
 * Reference: regular expression syntax used below follows the
 * java.util.regex.Pattern API documentation.
 * https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html
 */
public class Login {

    // Stored account details (this task models a single registered user).
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    // Remembers the outcome of the last check of each type, so the
    // matching "message" method can report the correct text.
    private boolean lastUsernameValid;
    private boolean lastPasswordValid;
    private boolean lastCellPhoneValid;
    private boolean lastLoginSuccessful;

    public Login() {
    }

    /** Username must contain an underscore and be no more than five characters long. */
    public boolean checkUserName(String username) {
        boolean valid = username != null && username.contains("_") && username.length() <= 5;
        this.lastUsernameValid = valid;
        return valid;
    }

    public String getUsernameMessage() {
        return lastUsernameValid
                ? "Username successfully captured."
                : "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
    }

    /** Password: at least eight characters, a capital letter, a number and a special character. */
    public boolean checkPasswordComplexity(String password) {
        boolean valid;
        if (password == null) {
            valid = false;
        } else {
            String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";
            valid = Pattern.matches(regex, password);
        }
        this.lastPasswordValid = valid;
        return valid;
    }

    public String getPasswordMessage() {
        return lastPasswordValid
                ? "Password successfully captured."
                : "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
    }

    /** South African cell number: +27 followed by no more than ten digits. */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        boolean valid;
        if (cellPhoneNumber == null) {
            valid = false;
        } else {
            String regex = "^\\+27\\d{1,10}$";
            valid = Pattern.matches(regex, cellPhoneNumber);
        }
        this.lastCellPhoneValid = valid;
        return valid;
    }

    public String getCellPhoneMessage() {
        return lastCellPhoneValid
                ? "Cell number successfully captured."
                : "Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.";
    }

    /** Validates username then password; if both are valid the account details are stored. */
    public String registerUser(String username, String password, String cellPhoneNumber,
                                String firstName, String lastName) {
        checkUserName(username);
        if (!lastUsernameValid) {
            return getUsernameMessage();
        }

        checkPasswordComplexity(password);
        if (!lastPasswordValid) {
            return getPasswordMessage();
        }

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

        checkCellPhoneNumber(cellPhoneNumber);
        this.cellPhoneNumber = cellPhoneNumber;

        return getUsernameMessage() + " " + getPasswordMessage();
    }

    /** Verifies the entered details match those captured at registration. */
    public boolean loginUser(String username, String password) {
        boolean success = this.username != null && this.username.equals(username)
                && this.password != null && this.password.equals(password);
        this.lastLoginSuccessful = success;
        return success;
    }

    public String getLoginMessage() {
        return lastLoginSuccessful
                ? "Welcome " + firstName + ", " + lastName + " it is great to see you."
                : "Username or password incorrect, please try again.";
    }

    public String getUsername() {
        return username;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
