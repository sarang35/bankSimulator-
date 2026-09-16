import java.util.ArrayList;
import java.util.Scanner;

/** Displays the ATM menu and performs actions for the logged-in account. */
public class ATM {
    private final Bank bank;
    private final Scanner scanner;
    private static final double MINIMUM_BALANCE = 500.00;
    private static final double MAX_WITHDRAWAL = 20000.00;

    public ATM(Bank bank, Scanner scanner) { this.bank = bank; this.scanner = scanner; }

    public void start(Account account) {
        boolean loggedIn = true;
        while (loggedIn) {
            printMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: checkBalance(account); break;
                case 2: deposit(account); break;
                case 3: withdraw(account); break;
                case 4: transfer(account); break;
                case 5: showHistory(account); break;
                case 6: showDetails(account); break;
                case 7: changePin(account); break;
                case 8: System.out.println("\nYou have been logged out safely.\n"); loggedIn = false; break;
                default: System.out.println("Invalid menu choice. Please choose 1 to 8.");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n====================================\n              ATM\n====================================");
        System.out.println("1. Check Balance\n2. Deposit Money\n3. Withdraw Money\n4. Transfer Money\n5. Transaction History\n6. Account Details\n7. Change PIN\n8. Logout\n====================================");
    }

    private void checkBalance(Account a) { System.out.printf("\nCurrent Balance: ₹%.2f%n", a.getBalance()); }

    private void deposit(Account a) {
        double amount = readAmount("Enter deposit amount: ");
        if (amount <= 0) { System.out.println("Amount must be greater than zero."); return; }
        a.deposit(amount); record(a, "DEPOSIT", amount, "Cash deposit"); bank.save();
        System.out.printf("₹%.2f deposited successfully.%nNew Balance: ₹%.2f%n", amount, a.getBalance());
    }

    private void withdraw(Account a) {
        double amount = readAmount("Enter withdrawal amount: ");
        if (amount <= 0) { System.out.println("Amount must be greater than zero."); return; }
        if (amount > MAX_WITHDRAWAL) { System.out.println("Maximum single withdrawal is ₹20,000.00."); return; }
        if (a.getBalance() - amount < MINIMUM_BALANCE) { System.out.println("Withdrawal denied. You must maintain ₹500.00 minimum balance."); return; }
        a.withdraw(amount); record(a, "WITHDRAW", amount, "Cash withdrawal"); bank.save();
        System.out.printf("Withdrawal successful!%nAmount: ₹%.2f%nRemaining Balance: ₹%.2f%n", amount, a.getBalance());
    }

    private void transfer(Account sender) {
        int recipientNumber = readInt("Enter recipient account number: ");
        Account recipient = bank.findAccount(recipientNumber);
        if (recipient == null) { System.out.println("Recipient account was not found."); return; }
        if (recipient == sender) { System.out.println("You cannot transfer money to yourself."); return; }
        double amount = readAmount("Enter amount: ");
        if (amount <= 0) { System.out.println("Amount must be greater than zero."); return; }
        if (sender.getBalance() - amount < MINIMUM_BALANCE) { System.out.println("Transfer denied. You must maintain ₹500.00 minimum balance."); return; }
        String answer = readText("Confirm transfer? (Y/N): ");
        if (!answer.equalsIgnoreCase("Y")) { System.out.println("Transfer cancelled."); return; }
        sender.withdraw(amount); recipient.deposit(amount);
        record(sender, "TRANSFER", amount, "Transfer to account " + recipient.getAccountNumber());
        record(recipient, "TRANSFER", amount, "Transfer received from account " + sender.getAccountNumber());
        bank.save();
        System.out.printf("Transfer successful!%nFrom: %d%nTo: %d%nAmount: ₹%.2f%nRemaining Balance: ₹%.2f%n", sender.getAccountNumber(), recipient.getAccountNumber(), amount, sender.getBalance());
    }

    private void showHistory(Account a) {
        System.out.println("\n========== TRANSACTION HISTORY ==========");
        ArrayList<Transaction> history = a.getTransactions();
        if (history.isEmpty()) System.out.println("No transactions yet.");
        for (Transaction transaction : history) System.out.println("\n" + transaction + "\n");
        System.out.println("==========================================");
    }

    private void showDetails(Account a) {
        String phone = a.getPhone();
        String maskedPhone = phone.length() > 2 ? "********" + phone.substring(phone.length() - 2) : "********";
        String email = a.getEmail();
        String maskedEmail = email.contains("@") && email.length() > 3 ? email.charAt(0) + "****" + email.substring(email.indexOf('@')) : "****";
        System.out.printf("\n========== ACCOUNT DETAILS ==========%nName: %s%nAccount Number: %d%nPhone: %s%nEmail: %s%nBalance: ₹%.2f%n======================================%n", a.getName(), a.getAccountNumber(), maskedPhone, maskedEmail, a.getBalance());
    }

    private void changePin(Account a) {
        String oldPin = readText("Enter current PIN: ");
        if (!a.getPin().equals(oldPin)) { System.out.println("Current PIN is incorrect."); return; }
        String newPin = readText("Enter new 4-digit PIN: ");
        String confirm = readText("Confirm new PIN: ");
        if (!newPin.matches("\\d{4}")) { System.out.println("PIN must contain exactly 4 digits."); return; }
        if (!newPin.equals(confirm)) { System.out.println("New PINs do not match."); return; }
        a.setPin(newPin); bank.save(); System.out.println("PIN changed successfully.");
    }

    private void record(Account a, String type, double amount, String description) {
        a.addTransaction(new Transaction(bank.nextTransactionId(), type, amount, description, java.time.LocalDateTime.now(), a.getBalance()));
    }

    private int readInt(String prompt) {
        while (true) try { System.out.print(prompt); return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid input! Please enter a whole number."); }
    }
    private double readAmount(String prompt) {
        while (true) try { System.out.print(prompt); return Double.parseDouble(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Invalid input! Please enter a valid number."); }
    }
    private String readText(String prompt) { System.out.print(prompt); return scanner.nextLine().trim(); }
}
