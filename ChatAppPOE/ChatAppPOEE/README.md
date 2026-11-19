# ChatApp POE

### A complete console-based messaging system built in pure Java

**ChatApp POE** is a fully functional backend messaging application that demonstrates professional software engineering practices. It covers user authentication, message validation, intelligent data management with parallel arrays, and persistent JSON storage — all without external libraries.

---

## Overview

This project implements a realistic messaging workflow in three progressive parts:

| Part | Focus | What it delivers |
|------|-------|------------------|
| **1** | Authentication | Secure registration & login with strict validation rules |
| **2** | Messaging Engine | Message creation, ID generation, hashing, send / store / disregard logic |
| **3** | Data Intelligence | Parallel arrays, search, delete, reports + JSON persistence |

The application is intentionally console-based so the focus remains on **clean logic, robust validation, and solid architecture**.

---

## Key Features

### Authentication (Part 1)
- Username must contain an underscore and be no longer than 5 characters
- Strong password enforcement (8+ characters, uppercase, digit, special character)
- South African cell number validation (`+27` format)
- Clear, user-friendly feedback messages for every validation rule

### Messaging Core (Part 2)
- Auto-generated unique 10-digit Message IDs
- Intelligent Message Hash generation
- Recipient cell number validation (international format)
- 250-character message limit with precise excess reporting
- Three message actions: **Send**, **Disregard**, or **Store for later**
- Live counter of total messages successfully sent

### Data Management & Reporting (Part 3)
- Parallel arrays for complete message tracking
- Search by Message ID
- Search all messages by recipient
- Delete messages by hash
- Longest message detection
- Full formatted reports for Sent and Stored messages
- Persistent storage using a clean, hand-written JSON engine (`storedMessages.json`)

---

## Project Structure

```
ChatAppPOE/
├── src/main/java/com/mycompany/chatapppoe/
│   ├── ChatAppPOE.java          # Main application & menu system
│   ├── Login.java               # Registration & authentication
│   ├── Message.java             # Single message + validation rules
│   ├── MessageManager.java      # Parallel arrays & reporting logic
│   └── MessageStorage.java      # JSON save / load (no external libraries)
│
├── src/test/java/com/mycompany/chatapppoe/
│   ├── LoginTest.java
│   ├── MessageTest.java
│   ├── MessageManagerTest.java
│   └── MessageStorageTest.java
│
├── pom.xml
└── README.md
```

---

## How to Run

### Option 1: NetBeans (Recommended for students)
1. Open NetBeans
2. **File → Open Project** and select the `ChatAppPOE` folder
3. Right-click the project → **Test** to run all unit tests
4. Right-click `ChatAppPOE.java` → **Run File** to launch the application

### Option 2: Maven (Command Line)
```bash
# Run all unit tests
mvn test

# Build the project
mvn package

# Launch the application
java -cp target/ChatAppPOE-1.0-SNAPSHOT.jar com.mycompany.chatapppoe.ChatAppPOE
```

> **Note:** The first time you store a message, the application automatically creates `storedMessages.json` in the project root.

---

## Unit Testing

The project includes a comprehensive suite of **40 unit tests** covering every requirement from the brief:

- Validation rules (username, password, cell number)
- Message ID & hash generation
- Parallel array population
- Search, delete and report functionality
- JSON persistence (including special character escaping)

All tests pass successfully.

```bash
mvn test
```

---

## Technical Highlights

- **Zero external dependencies** for core functionality (JSON is hand-parsed)
- Clean separation of concerns across five focused classes
- Defensive programming with clear validation feedback
- Professional use of regular expressions for input validation
- Parallel array design exactly as required by the assignment brief
- Fully testable architecture with JUnit 5

---

## References

- [Java Pattern API](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)
- [JSON Specification](https://www.json.org)

---

**Built with precision. Designed for clarity. Ready for demonstration.**
