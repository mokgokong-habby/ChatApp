

# 💬 QuickChat — Java Messaging Application

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/IDE-NetBeans-blue?style=for-the-badge&logo=apachenetbeanside" />
  <img src="https://img.shields.io/badge/Build-Maven-C71A36?style=for-the-badge&logo=apachemaven" />
  <img src="https://img.shields.io/badge/Module-PROG5121-purple?style=for-the-badge" />
  <img src="https://img.shields.io/badge/Project-Portfolio%20of%20Evidence-success?style=for-the-badge" />
</p>

<p align="center">
  <strong>A structured, console-based messaging application developed in Java.</strong>
  <br/>
  Programming 1A · PROG5121 · Portfolio of Evidence
</p>

<p align="center">
 
</p>

---

## 📖 1. Project Overview

**QuickChat** is a Java-based console messaging application developed as part of the Programming 1A (PROG5121) Portfolio of Evidence.

The application demonstrates fundamental software development principles through a practical messaging workflow involving user registration, authentication, message creation, validation, and message management.

The project focuses on applying Java programming concepts, including object-oriented programming, conditional statements, loops, methods, regular expressions, data structures, and automated testing.

Designed around a modular architecture, QuickChat separates authentication and messaging responsibilities to promote readable, maintainable, and testable code.

##  2. Core Features

| Feature | Description |
|:---|:---|
| 🔐 User Authentication | User registration and login validation |
| 🔑 Login Security | Maximum of three login attempts |
| 🆔 Message Identification | Automatic generation of 10-digit message IDs |
| 📱 Recipient Validation | Cellphone number validation |
| 📝 Message Validation | Maximum message length of 250 characters |
| 💬 Message Processing | Send, disregard, or store messages |
| #️⃣ Message Hashing | Message hash generation according to assessment requirements |
| 🔎 Message Management | Access and manage message information |
| 💾 Data Persistence | JSON-based message storage, where implemented |
| 📋 Menu Navigation | Interactive console menu and application controls |

---

##  3. Application Development

The application is structured around three main development components.

### Part 1 — User Registration and Authentication

This component handles account creation and user authentication.

**Implemented requirements include:**

- Username validation requiring an underscore (`_`).
- Maximum username length of five characters.
- Password validation requiring at least eight characters.
- At least one uppercase letter.
- At least one numerical digit.
- At least one special character.
- South African cellphone number validation using the `+27` prefix.
- User registration feedback.
- Login authentication with a maximum of three attempts.

### Part 2 — Messaging Engine

The messaging component handles message creation, identification, validation, and processing.

**Key functionality includes:**

- Automatically generated 10-digit message IDs.
- Message hash generation.
- Recipient cellphone number validation.
- A maximum message length of 250 characters.
- Feedback when a message exceeds the permitted length.
- Three message actions:

| Action | Function |
|:---|:---|
| 📤 Send | Processes a message as sent |
| 🚫 Disregard | Discards the message |
| 💾 Store | Saves a message for later access |

- Tracking the number of successfully sent messages.

### Part 3 — Message Management and Data Persistence

This component focuses on message organisation and information retrieval.

Depending on the implemented project requirements, functionality includes:

- Managing message information using parallel arrays.
- Searching messages by message ID.
- Retrieving messages using recipient cellphone numbers.
- Deleting messages using message hashes.
- Identifying the longest message.
- Generating formatted message reports.
- Saving and retrieving stored messages through JSON.

---

## 🛠️ 4. Technology Stack

<p align="center">
  <img src="https://skillicons.dev/icons?i=java,maven,git,github,netbeans" />
</p>

| Technology | Purpose |
|:---|:---|
| ☕ Java (JDK 17+) | Core application development |
| 💻 Apache NetBeans | Integrated development environment |
| 📦 Apache Maven | Project configuration and build management |
| 🧪 JUnit 5 | Automated unit testing |
| 🔍 Java Regular Expressions | Input validation |
| 📄 JSON | Structured message storage |
| 🔧 Git | Version control |
| 🐙 GitHub | Source code hosting and project documentation |

---

## 🧩 5. Application Architecture

QuickChat uses a modular class structure to separate application responsibilities.

| Class | Responsibility |
|:---|:---|
| `ChatAppPOE.java` | Main application entry point and menu navigation |
| `Login.java` | User registration, validation, and authentication |
| `Message.java` | Message creation, validation, identification, and hashing |
| `MessageManager.java` | Message management, searching, deletion, and reporting |
| `MessageStorage.java` | JSON-based message storage and retrieval |

### Application Workflow

```text
             ┌──────────────────────┐
             │      QUICKCHAT       │
             │   Application Start  │
             └──────────┬───────────┘
                        │
             ┌──────────▼───────────┐
             │   User Registration  │
             │      / Login         │
             └──────────┬───────────┘
                        │
             ┌──────────▼───────────┐
             │    Main Menu         │
             └──────────┬───────────┘
                        │
             ┌──────────▼───────────┐
             │   Message Creation   │
             │    & Validation      │
             └──────────┬───────────┘
                        │
              ┌─────────┼─────────┐
              │         │         │
          ┌───▼───┐ ┌──▼─────┐ ┌─▼──────┐
          │ Send  │ │ Store  │ │Discard │
          └───┬───┘ └──┬─────┘ └────────┘
              │        │
              │   ┌────▼──────────┐
              │   │ JSON Storage  │
              │   └───────────────┘
              │
       ┌──────▼──────────┐
       │ Message Records │
       │ & Reporting     │
       └─────────────────┘
```

*The diagram illustrates the intended messaging workflow; individual operations depend on the implemented application.*

---

## 📂 6. Project Structure

```text
ChatAppPOE/
│
├── .github/
│   └── workflows/
│       └── maven.yml
│
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── mycompany/
│   │               └── chatapppoe/
│   │                   ├── ChatAppPOE.java
│   │                   ├── Login.java
│   │                   ├── Message.java
│   │                   ├── MessageManager.java
│   │                   └── MessageStorage.java
│   │
│   └── test/
│       └── java/
│           └── com/
│               └── mycompany/
│                   └── chatapppoe/
│                       ├── LoginTest.java
│                       ├── MessageTest.java
│                       ├── MessageManagerTest.java
│                       └── MessageStorageTest.java
│
├── pom.xml
├── .gitignore
├── storedMessages.json
└── README.md
```

*Note: This represents the expanded project structure. Remove or adjust entries for files and folders that are not present in your actual repository.*

---

## 🚀 7. Getting Started

### Prerequisites

Ensure that the following development tools are installed:

- Java Development Kit (JDK) 17 or later.
- Apache NetBeans IDE.
- Apache Maven.
- Git (optional).

### Installation

**Step 1 — Clone the Repository**

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
```

**Step 2 — Navigate to the Project**

```bash
cd ChatAppPOE
```

**Step 3 — Open the Project in NetBeans**

1. Launch Apache NetBeans.
2. Select **File → Open Project**.
3. Locate the project directory containing `pom.xml`.
4. Open the project.
5. Allow Maven to resolve the project configuration.

**Step 4 — Build the Application**

Right-click the project and select:

`Clean and Build`

**Step 5 — Launch QuickChat**

Open `ChatAppPOE.java`, right-click, and select:

`Run File`

---

## ▶️ 8. Running with Maven

Open a terminal in the project directory.

**Run automated tests:**

```bash
mvn test
```

**Clean and build the project:**

```bash
mvn clean package
```

**Run the application using compiled classes:**

```bash
java -cp target/classes com.mycompany.chatapppoe.ChatAppPOE
```

If the Maven Exec plugin is configured, the application can also be launched using:

```bash
mvn exec:java
```

---

## ✅ 9. Validation Requirements

The following validation rules are based on the documented assessment requirements.

### Username Validation

- Must contain an underscore (`_`).
- Must not exceed five characters.

Example:

```text
kyl_1
```

### Password Validation

- Minimum of eight characters.
- At least one uppercase letter.
- At least one digit.
- At least one special character.

### South African Cellphone Validation

- Must begin with the international prefix `+27`.
- The documented rule allows between one and ten digits following the prefix.

Examples:

```text
+27838968976
+27718693002
```

### Message Validation

- Maximum message length: 250 characters.
- Message ID: Automatically generated 10-digit identifier.
- Message hash: Generated according to the official assessment format.

**Documented message hash structure:**

```text
firstTwoOfID:messageNumber:FIRSTWORDLASTWORD
```

Example:

```text
00:0:HITONIGHT
```

For exact implementation requirements, consult the official PROG5121 assessment brief and the corresponding Java classes.

---

## 🧪 10. Testing and Quality Assurance

The project uses JUnit 5 where configured to verify application behaviour and validation requirements.

### Testing Coverage

| Test Class | Testing Area |
|:---|:---|
| `LoginTest.java` | Username, password, cellphone validation and authentication |
| `MessageTest.java` | Message IDs, hashes, recipient validation and message length |
| `MessageManagerTest.java` | Message searching, deletion, arrays and reporting |
| `MessageStorageTest.java` | JSON storage and retrieval |

Execute the complete test suite:

```bash
mvn clean test
```

Testing helps identify incorrect input handling, unexpected application behaviour, and potential implementation errors.

*Only test classes present in the repository and successfully executed should be reported as completed.*

---

## ⚙️ 11. Technical Highlights

The project demonstrates practical application of the following programming concepts:

- **Object-Oriented Programming:** Separating functionality into dedicated Java classes.
- **Encapsulation:** Organising data and controlling access to class functionality.
- **Conditional Logic:** Controlling application behaviour based on user input.
- **Loops:** Supporting repeated menu interactions and validation.
- **Regular Expressions:** Enforcing structured input requirements.
- **Parallel Arrays:** Maintaining related message information.
- **Data Processing:** Implementing search, deletion, and reporting operations.
- **JSON Persistence:** Saving and retrieving message records where implemented.
- **Unit Testing:** Verifying functionality using JUnit 5.

---

## 🔧 12. Troubleshooting

| Problem | Recommended Solution |
|:---|:---|
| Project fails to build | Verify the JDK configuration and Maven dependencies |
| Application does not launch | Check the main class and package declaration |
| Maven tests fail | Review test output and investigate failing assertions |
| Messages are not displayed | Verify message processing and menu implementation |
| Stored messages are missing | Check the JSON file location and storage implementation |

---

## 🎓 13. Learning Objectives

QuickChat provides practical experience with:

1. Java syntax, variables, and data types.
2. Methods and modular programming.
3. Conditional statements and iteration.
4. User input and validation.
5. Object-oriented programming principles.
6. Message identification and processing.
7. Data management and retrieval.
8. Software testing and debugging.
9. Project organisation using Maven.

---

## 📚 14. References

- [Oracle Java Pattern API](https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html)
- [Oracle Java Documentation](https://docs.oracle.com/en/java/)
- [Apache Maven Documentation](https://maven.apache.org/guides/index.html)
- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [JSON Official Website](https://www.json.org/json-en.html)


---

<p align="center">
  <strong>💻 QUICKCHAT</strong>
  <br/>
  <em>Built with Java. Driven by logic. Developed through learning.</em>
  <br/><br/>
  Programming 1A · PROG5121 · Portfolio of Evidence
</p>


### A Java Console-Based Messaging Application

**Programming 1A · PROG5121 · Portfolio of Evidence**
</div>
---
registration failed
<img width="1285" height="207" alt="image" src="https://github.com/user-attachments/assets/81791a5e-cf23-40f8-b546-166fa36b61e3" />
login failed
<img width="730" height="213" alt="image" src="https://github.com/user-attachments/assets/ccbf9b45-a441-4824-bd2e-fed7b3c2c7e1" />
part 2
<img width="902" height="515" alt="image" src="https://github.com/user-attachments/assets/447f6f73-651e-4df7-8fe4-c03f0a390c85" />
<img width="632" height="252" alt="image" src="https://github.com/user-attachments/assets/54c9a83a-a0fc-48e0-b934-7f5229deefa6" />
part 3
<img width="1560" height="711" alt="image" src="https://github.com/user-attachments/assets/8c9f6b0f-a130-49d5-b437-11a105cba11c" />
<img width="1598" height="810" alt="image" src="https://github.com/user-attachments/assets/f254eb1f-583c-4382-a9f5-7bc6fbac903a" />





⭐ **This repository is a work in progress. Follow the project as it evolves from its initial authentication foundation into a more complete chat application.**
