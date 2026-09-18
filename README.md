# Java ATM & Banking Simulator

A console-based Java application that simulates common ATM and banking operations. The project is designed for a Java programming course and demonstrates classes, objects, encapsulation, constructors, getters and setters, `ArrayList`, `Scanner`, `if-else`, `switch`, loops, exception handling, file handling, and `LocalDateTime`.

> **Important:** This is an educational simulation only. It is not suitable for real banking, real financial transactions, or production security. Account PINs are stored in plain text by design so that the file-handling example remains easy to inspect.

## 1. Project Contents

The repository contains the following Java source files at its root:

| File | Purpose |
|---|---|
| `Main.java` | Program entry point, main menu, account creation, and login. |
| `Account.java` | Encapsulated account data, balance operations, and transaction list. |
| `Transaction.java` | Data model for deposits, withdrawals, and transfers. |
| `ATM.java` | Authenticated ATM menu and banking operations. |
| `Bank.java` | Account collection, account lookup, ID generation, and saving coordination. |
| `FileHandler.java` | Loading and saving account and transaction data as text files. |
| `statement.md` | Project statement. |
| `projectReport.docx` | Formatted project report containing the five system diagrams. |

The diagram source files, compressed diagram images, and report-generation helper files are supplementary documentation assets. They are not required to compile or run the ATM application.

## 2. Requirements

Install the following software before running the project:

- **Java Development Kit (JDK) 17 or newer.** JDK 21 is also supported and was used for validation.
- A terminal or command prompt.
- A text editor or Java IDE such as IntelliJ IDEA, Eclipse, or Visual Studio Code is optional.

No database, external Java library, build tool, web server, environment variable, or API key is required.

## 3. Environment Setup

### Windows

1. Install a JDK from [Eclipse Temurin](https://adoptium.net/) or another trusted JDK provider.
2. Open PowerShell or Command Prompt.
3. Verify that Java is available:

   ```powershell
   java -version
   javac -version
   ```

4. Both commands should print a version number. If `java` or `javac` is not recognized, add the JDK `bin` directory to the system `PATH`, then reopen the terminal.

### macOS

1. Install a JDK from [Eclipse Temurin](https://adoptium.net/) or use Homebrew:

   ```bash
   brew install openjdk
   ```

2. Verify the installation:

   ```bash
   java -version
   javac -version
   ```

### Ubuntu or Debian Linux

Install OpenJDK with the system package manager:

```bash
sudo apt update
sudo apt install -y default-jdk
```

Then verify the installation:

```bash
java -version
javac -version
```

## 4. Dependency Installation

This project uses only the Java Standard Library. There are **no external Java dependencies** to install.

The application uses standard classes such as:

- `java.util.ArrayList`
- `java.util.Scanner`
- `java.time.LocalDateTime`
- `java.io.File`
- `java.io.FileReader`
- `java.io.FileWriter`
- `java.io.BufferedReader`

Do not install Spring Boot, JavaFX, a database driver, or another framework for this project.

## 5. Obtain and Enter the Project

If the repository has already been downloaded, open a terminal in the directory containing `Main.java`:

```bash
cd ATM-Simulator
```

Confirm that the source files are present:

```bash
ls Main.java Account.java Transaction.java ATM.java Bank.java FileHandler.java
```

On Windows PowerShell, use:

```powershell
Get-ChildItem Main.java, Account.java, Transaction.java, ATM.java, Bank.java, FileHandler.java
```

If the project was cloned from Git, use the repository's clone URL with your normal Git command, then enter the resulting directory. The important requirement is that the terminal's current directory is the project root containing `Main.java`.

## 6. Configuration

No configuration file is required before the first run. The application uses the following default settings in the source code:

- First generated account number: `100001`
- Maximum incorrect login attempts: `3`
- Minimum permitted remaining balance: `₹500.00`
- Maximum single withdrawal: `₹20,000.00`
- Account data file: `accounts.txt`
- Transaction data file: `transactions.txt`

The two text files are created automatically in the current working directory when an account is created or another account-changing operation is completed. To keep data between runs, always start the program from the same project directory. These files may contain personal information and should not be committed to a public repository.

If a clean demonstration is required, stop the program and remove the generated files before starting again:

```bash
rm -f accounts.txt transactions.txt
```

On Windows PowerShell:

```powershell
Remove-Item accounts.txt, transactions.txt -ErrorAction SilentlyContinue
```

## 7. Compile the Application

From the project root, compile all Java source files together:

```bash
javac Main.java Account.java Transaction.java ATM.java Bank.java FileHandler.java
```

A successful compilation creates `.class` files and prints no error message. The shorter wildcard command is also supported:

```bash
javac *.java
```

If compilation fails with `javac: command not found` or a similar message, return to the environment setup section and install/configure a JDK. A Java Runtime Environment alone is not enough because compilation requires `javac`.

## 8. Run the Application

After compilation, run the main class:

```bash
java Main
```

The application displays:

```text
====================================
       WELCOME TO JAVA BANK
====================================
1. Create Account
2. Login
3. Exit
```

Follow the prompts. A simple first-run test is:

1. Select `1` to create an account.
2. Enter a name such as `Test User`.
3. Enter a four-digit PIN such as `1234`.
4. Enter an initial deposit such as `5000`.
5. Enter a valid phone number and email address.
6. Note the generated account number.
7. Return to the main menu and select `2` to log in.
8. Enter the generated account number and PIN.
9. Select `1` to check the balance.
10. Select `8` to log out, then select `3` to exit.

## 9. Application Features

After login, the ATM menu provides these operations:

1. **Check Balance** displays the current balance.
2. **Deposit Money** validates a positive amount, updates the balance, records a transaction, and saves the data.
3. **Withdraw Money** validates the amount, available balance, ₹500 minimum balance, and ₹20,000 maximum withdrawal limit.
4. **Transfer Money** validates the recipient, rejects self-transfers, checks the minimum balance, requests confirmation, updates both accounts, and records both sides of the transfer.
5. **Transaction History** displays transaction IDs, types, amounts, descriptions, balances, and timestamps.
6. **Account Details** displays the account number and masked phone/email details without displaying the PIN.
7. **Change PIN** verifies the current PIN and requires a matching new four-digit PIN.
8. **Logout** returns to the Java Bank main menu.

## 10. Persistence and Generated Files

`FileHandler.java` manages two files:

- `accounts.txt` stores account details and current balances.
- `transactions.txt` stores transaction records associated with account numbers.

The files are plain text and use the pipe character (`|`) as a field separator. When the application starts, `Bank` calls `FileHandler.loadAccounts()`. After an account or transaction changes, `Bank.save()` calls `FileHandler.saveAll(...)` to rewrite the files.

Do not manually edit these files while the application is running. If they are deleted, the application starts with an empty account list and new accounts begin again at account number `100001`.

## 11. Clean Rebuild

To remove compiled classes and generated runtime data, then compile from scratch, use:

```bash
rm -f *.class accounts.txt transactions.txt
javac *.java
java Main
```

On Windows PowerShell:

```powershell
Remove-Item *.class, accounts.txt, transactions.txt -ErrorAction SilentlyContinue
javac *.java
java Main
```

## 12. Troubleshooting

### `javac` is not recognized

The JDK is missing or its `bin` directory is not on `PATH`. Install a full JDK, reopen the terminal, and run `javac -version`.

### `java Main` cannot find the class

Compile first with `javac *.java`, and make sure the terminal is in the directory containing `Main.java` and the generated `Main.class`.

### The account list appears empty

The program loads files from the current working directory. Start `java Main` from the project root. Also check whether `accounts.txt` exists and contains the saved account data.

### The program rejects a phone number or email

Use a phone number with at least seven characters containing digits, spaces, `+`, or hyphens. The email must contain `@`.

### Numeric input is rejected

Enter a normal decimal number without a currency symbol or comma. For example, enter `5000` rather than `₹5,000`.

## 13. Academic Scope and Security Notice

This project is intended to demonstrate Java programming concepts. It uses a local text-file format and stores PINs without encryption so that the implementation remains understandable to a beginner. It must not be connected to real bank accounts, used to process real money, or treated as a secure banking system.
