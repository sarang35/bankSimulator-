import java.util.ArrayList;

/** Represents one bank account. */
public class Account {
    private String name;
    private int accountNumber;
    private String pin;
    private double balance;
    private String phone;
    private String email;
    private ArrayList<Transaction> transactions;

    public Account(String name, int accountNumber, String pin, double balance,
                   String phone, String email) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.phone = phone;
        this.email = email;
        this.transactions = new ArrayList<>();
    }

    public String getName() { return name; }
    public int getAccountNumber() { return accountNumber; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public ArrayList<Transaction> getTransactions() { return transactions; }

    public void setPin(String pin) { this.pin = pin; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setTransactions(ArrayList<Transaction> transactions) { this.transactions = transactions; }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
