# 💬 Chat App



![Status](https://img.shields.io/badge/Status-In%20Development-orange)
![Language](https://img.shields.io/badge/Language-Java-blue)
![Build](https://img.shields.io/badge/Build-Maven-red)
![Testing](https://img.shields.io/badge/Testing-JUnit-green)

# 💬 QuickChat

### A Java Console-Based Messaging Application

**Programming 1A · PROG5121 · Portfolio of Evidence**

*Register. Sign in. Connect.*

---

## 📌 About the Project

QuickChat is a Java console application developed as part of the **Programming 1A (PROG5121)** Portfolio of Evidence. It demonstrates introductory programming concepts through account registration, login validation, message creation, and message management.

## Features

-  **User Registration** — captures account and personal details.
-  **Login System** — validates credentials with a maximum of three attempts.
-  **Message Identification** — generates a 10-digit message ID.
-  **Recipient Validation** — validates recipient cell-number input.
-  **Message Validation** — enforces a maximum message length of 250 characters.
-  **Message Actions** — supports sending, disregarding, or storing messages, as implemented.
- #️ **Message Hashing** — follows the format described in the project requirements.
-  **Message Storage** — supports JSON storage if implemented.
-  **Menu Navigation** — provides messaging, recent-message, and exit options.

> **Note:** Feature availability depends on the current implementation. Test each feature before describing it as complete.

## 🛠️ Technology Stack

| Technology | Purpose |
|---|---|
| Java (JDK 17+) | Application development |
| Apache NetBeans | Integrated development environment |
| Maven | Build and dependency management |
| JUnit 5 | Unit testing, if configured |
| JSON | Message storage, if implemented |

## 🚀 Getting Started

### Prerequisites

Before running QuickChat, ensure you have:

- Java Development Kit (JDK) 17 or later.
- Apache NetBeans with Maven support.
- The complete project source files.

### Installation in NetBeans

1. Open Apache NetBeans.
2. Select **File → Open Project**.
3. Choose the project folder containing `pom.xml`.
4. Right-click the project and select **Clean and Build**.
5. Run `ChatAppPOE.java` or right-click the project and select **Run**.
6. If tests are configured, right-click the project and select **Test**.

### Running with Maven

Open a terminal in the project directory and execute:

```bash
mvn clean test
```

If the Maven Exec plugin is configured, launch the application with:

```bash
mvn exec:java
```

Otherwise, run the main class directly through NetBeans.

## 🧑‍💻 How to Use QuickChat

1. **Register:** Enter your username, password, cell number, first name, and last name.
2. **Log in:** Enter your registered credentials. You have up to three login attempts.
3. **Select a menu option:**
   - `1` — Send messages.
   - `2` — Show recently sent messages, if implemented.
   - `3` — Quit the application.
4. **Create a message:** Enter the recipient's cell number and your message.
5. **Choose an action:** Follow the prompts to send, disregard, or store the message.

## ✅ Validation Rules

The following rules are documented in the project requirements. Confirm that the Java implementation matches them before submission.

### Username

- Must contain an underscore (`_`).
- Maximum length: **5 characters**.
- Example: `kyl_1`

### Password

- Minimum length: **8 characters**.
- Must contain at least one uppercase letter.
- Must contain at least one digit.
- Must contain at least one special character.

### South African Cell Number

- Must begin with the international prefix `+27`.
- The existing project documentation specifies 1–10 digits after the prefix.
- Examples: `+27838968976`, `+27718693002`

### Message

- Maximum length: **250 characters**.
- Message ID: **10 digits**, generated automatically.
- Documented hash format:

```text
firstTwoOfID:messageNumber:FIRSTWORDLASTWORD
```

Example:

```text
00:0:HITONIGHT
```

Refer to `Message.java` and the official assessment brief for the exact implementation requirements.

## 🗂️ Project Structure

```text
ChatAppPOE/
├── pom.xml
├── README.md
├── .gitignore
├── .github/
│   └── workflows/
│       └── maven.yml
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── mycompany/
    │               └── chatapppoe/
    │                   ├── ChatAppPOE.java
    │                   ├── Login.java
    │                   └── Message.java
    └── test/
        └── java/
            └── com/
                └── mycompany/
                    └── chatapppoe/
                        ├── LoginTest.java
                        └── MessageTest.java
```

*This is the intended structure. Some files, such as tests and GitHub Actions workflows, may not exist in your current project.*

## 🧪 Testing

If JUnit tests are configured, run:

```bash
mvn test
```

The documented test areas are:

- **`LoginTest`** — username, password, cell-number validation, registration, and login.
- **`MessageTest`** — message length, recipient validation, hash generation, message IDs, message actions, and total messages sent.

Only report tests as passing after running them successfully.

## 🧰 Troubleshooting

<details>
<summary><strong>❌ The project does not build</strong></summary>

- Ensure all required Java source files are present.
- Check that package declarations match the folder structure.
- Confirm that the configured JDK supports your code.
- Use **Clean and Build** and resolve the first compiler error before addressing subsequent errors.

</details>

<details>
<summary><strong>⚙️ Maven cannot run the application</strong></summary>

The Maven Exec plugin may not be configured. Run the main class through NetBeans or configure the plugin in `pom.xml`.

</details>

<details>
<summary><strong>🕒 Recent messages are not displayed</strong></summary>

Check whether the recent-message functionality has been implemented. A placeholder message means the feature still requires development.

</details>

<details>
<summary><strong>💾 Stored messages cannot be found</strong></summary>

If JSON storage is implemented, inspect `Message.java` for the output filename and check the application's working directory.

</details>

## 🎯 Learning Objectives

QuickChat is intended to demonstrate:

- Variables, data types, and methods.
- Conditional statements and loops.
- Input validation and exception handling.
- Object-oriented programming.
- Unit testing, where configured.
- Basic data storage, where implemented.

## 👨‍🎓 Author & Academic Details

| Detail | Information |
|---|---|
| **Author** | Add your full name |
| **Module** | PROG5121 — Programming 1A |
| **Project** | QuickChat / ChatAppPOE |
| **Language** | Java |

---

<div align="center">

**Built as part of a Java programming learning journey.** ☕💻

</div>
---
registration failed
<img width="1285" height="207" alt="image" src="https://github.com/user-attachments/assets/81791a5e-cf23-40f8-b546-166fa36b61e3" />
login failed
<img width="730" height="213" alt="image" src="https://github.com/user-attachments/assets/ccbf9b45-a441-4824-bd2e-fed7b3c2c7e1" />


⭐ **This repository is a work in progress. Follow the project as it evolves from its initial authentication foundation into a more complete chat application.**
