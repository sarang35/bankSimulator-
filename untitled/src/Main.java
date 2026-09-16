import java.util.Scanner;

/** Entry point for the Java Bank ATM Simulator. */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        ATM atm = new ATM(bank, scanner);
        boolean running = true;

        while (running) {
            System.out.println("\n====================================\n       WELCOME TO JAVA BANK\n====================================\n1. Create Account\n2. Login\n3. Exit");
            int choice = readInt(scanner, "\nEnter choice: ");
            switch (choice) {
                case 1: createAccount(bank, scanner); break;
                case 2:
                    Account account = login(bank, scanner);
                    if (account != null) { System.out.println("\nLogin successful!\nWelcome, " + account.getName()); atm.start(account); }
                    break;
                case 3: running = false; System.out.println("Thank you for using Java Bank."); break;
                default: System.out.println("Invalid menu choice. Please select 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    private static void createAccount(Bank bank, Scanner scanner) {
        System.out.println("\n========== CREATE ACCOUNT ==========");
        System.out.print("Enter your full name: "); String name = scanner.nextLine().trim();
        if (name.isEmpty() || name.contains("|")) { System.out.println("Name cannot be empty or contain |."); return; }
        String pin = readPin(scanner, "Create 4-digit PIN: ");
        double initialDeposit = readDouble(scanner, "Initial deposit: ");
        if (initialDeposit < 0) { System.out.println("Initial deposit cannot be negative."); return; }
        System.out.print("Phone number: "); String phone = scanner.nextLine().trim();
        System.out.print("Email: "); String email = scanner.nextLine().trim();
        if (!phone.matches("[0-9+ -]{7,}") || !email.contains("@") || email.contains("|")) { System.out.println("Please enter a valid phone number and email."); return; }
        Account account = bank.createAccount(name, pin, initialDeposit, phone, email);
        System.out.println("\nAccount created successfully!\nAccount Number: " + account.getAccountNumber());
    }

    private static Account login(Bank bank, Scanner scanner) {
        int number = readInt(scanner, "Enter Account Number: ");
        Account account = bank.findAccount(number);
        if (account == null) { System.out.println("Account number not found."); return null; }
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Enter PIN: "); String pin = scanner.nextLine().trim();
            if (account.getPin().equals(pin)) return account;
            System.out.println("Incorrect PIN. Attempts remaining: " + (3 - attempt));
        }
        System.out.println("Too many incorrect attempts. Login blocked for this session.");
        return null;
    }

    private static String readPin(Scanner scanner, String prompt) {
        while (true) { System.out.print(prompt); String pin = scanner.nextLine().trim(); if (pin.matches("\\d{4}")) return pin; System.out.println("PIN must contain exactly 4 digits."); }
    }
    private static int readInt(Scanner scanner, String prompt) {
        while (true) try { System.out.print(prompt); return Integer.parseInt(scanner.nextLine().trim()); } catch (NumberFormatException e) { System.out.println("Invalid input! Please enter a whole number."); }
    }
    private static double readDouble(Scanner scanner, String prompt) {
        while (true) try { System.out.print(prompt); return Double.parseDouble(scanner.nextLine().trim()); } catch (NumberFormatException e) { System.out.println("Invalid input! Please enter a valid number."); }
    }
}
