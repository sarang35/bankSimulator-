# Java ATM & Banking Simulator

This is a console-based banking simulator for a Java programming course. It demonstrates classes, objects, encapsulation, constructors, getters and setters, overloaded-style input methods, `ArrayList`, `Scanner`, conditionals, `switch`, loops, exception handling, file handling, and `LocalDateTime`. It is a simulation only and is **not suitable for real banking or financial security**.

## Files

| File | Responsibility |
|---|---|
| `Main.java` | Starts the program, displays the bank menu, creates accounts, and performs login. |
| `Account.java` | Models an account and protects its fields using `private` variables and public methods. |
| `Transaction.java` | Models one deposit, withdrawal, or transfer with an ID, amount, description, time, and resulting balance. |
| `ATM.java` | Displays the logged-in ATM menu and performs banking actions. |
| `Bank.java` | Maintains the `ArrayList<Account>`, finds accounts, generates account/transaction IDs, and calls saving. |
| `FileHandler.java` | Loads and saves accounts and transactions in plain text files. |

## Compile and run

From this directory:

```bash
javac *.java
java Main
```

The first run creates `accounts.txt` and `transactions.txt` when data is saved. These files are intentionally simple text files so their contents can be inspected during a viva. Do not commit them if they contain personal data.

## How the classes connect

`Main` creates one `Bank`, one `Scanner`, and one `ATM`. The `Bank` loads its `ArrayList<Account>` through `FileHandler`. After a successful login, `Main` passes the selected `Account` to `ATM.start(account)`. The ATM changes that account, creates `Transaction` objects, and asks `Bank` to save all data. Each `Account` owns an `ArrayList<Transaction>`.

## Viva explanation

### Objects and constructors

An object is a usable instance of a class. For example, `new Account(...)` creates one account object and `new Transaction(...)` creates one transaction object. A constructor has the same name as its class and runs automatically when `new` is used. Constructors initialize the fields so the object starts in a valid state.

### Encapsulation

Account fields such as `pin` and `balance` are `private`, so other classes cannot change them directly. Public getters provide controlled reading, while methods such as `deposit`, `withdraw`, and `setPin` provide controlled changes. The PIN is never printed by the application.

### ArrayLists and for-each loops

`Bank` stores multiple accounts in `ArrayList<Account> accounts`. An account stores its activities in `ArrayList<Transaction> transactions`. An `ArrayList` grows dynamically, unlike a fixed-size array. A for-each loop such as `for (Account account : accounts)` visits every account, one at a time, without manually managing an index. The same technique is used to display every transaction and to search for an account.

### PIN verification and security simulation

Login first finds the account number and then compares the entered PIN with `account.getPin()`. The loop permits only three incorrect attempts. Change PIN checks the old PIN, validates that the new PIN has exactly four digits, and confirms it twice. These are educational simulation features, not real security.

### Money transfers

The ATM finds the recipient in the bank list, rejects a missing recipient or self-transfer, validates the amount and minimum balance, and asks for `Y` confirmation. It withdraws from the sender, deposits into the recipient, creates a transaction for each account, and saves both accounts.

### Transactions and LocalDateTime

After a successful operation, `ATM.record` creates a `Transaction`. `Bank.nextTransactionId()` produces IDs such as `TXN001`. `LocalDateTime.now()` records the current date and time. The transaction's `balanceAfterTransaction` makes the history easier to understand.

### Exception handling

Numeric input is inside `try-catch` blocks. If the user enters `abc` where a number is expected, `NumberFormatException` is caught and a useful message is shown instead of allowing the application to crash. File operations catch `IOException`, and malformed saved numeric fields are handled with `NumberFormatException`.

### File handling

`FileHandler` first checks whether each file exists. `FileReader` and `BufferedReader` read one line at a time. Each line is split into fields using `|`, and the fields are converted back into objects. `FileWriter` rewrites both files after a change, so the next application launch can load the latest account balances and transaction histories. The PIN is stored only because this is a beginner-level local simulation; a real banking program must never store PINs this way.

## Example evaluation points

* Deposit rejects zero, negative, and non-numeric amounts.
* Withdrawal rejects amounts over ₹20,000, amounts that exceed the balance, and amounts that would reduce the balance below ₹500.
* Transfer requires an existing different recipient, sufficient funds while maintaining ₹500, and confirmation.
* Account details mask the phone and email and do not display the PIN.
* Logout returns to the bank menu.
