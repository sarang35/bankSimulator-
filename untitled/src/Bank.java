import java.util.ArrayList;

/** Coordinates all accounts and login-related bank operations. */
public class Bank {
    private ArrayList<Account> accounts;
    private int nextAccountNumber;
    private int nextTransactionNumber;

    public Bank() {
        accounts = FileHandler.loadAccounts();
        nextAccountNumber = 100001;
        nextTransactionNumber = 1;
        for (Account account : accounts) {
            if (account.getAccountNumber() >= nextAccountNumber)
                nextAccountNumber = account.getAccountNumber() + 1;
            for (Transaction transaction : account.getTransactions()) {
                try {
                    int id = Integer.parseInt(transaction.getTransactionId().substring(3));
                    if (id >= nextTransactionNumber) nextTransactionNumber = id + 1;
                } catch (NumberFormatException | StringIndexOutOfBoundsException ignored) {
                    // Ignore old or incorrectly formatted transaction IDs.
                }
            }
        }
    }

    public ArrayList<Account> getAccounts() { return accounts; }

    public Account createAccount(String name, String pin, double deposit,
                                 String phone, String email) {
        Account account = new Account(name, nextAccountNumber++, pin, deposit, phone, email);
        accounts.add(account);
        FileHandler.saveAll(accounts);
        return account;
    }

    public Account findAccount(int number) {
        for (Account account : accounts)
            if (account.getAccountNumber() == number) return account;
        return null;
    }

    public Account login(int number, String pin) {
        Account account = findAccount(number);
        return account != null && account.getPin().equals(pin) ? account : null;
    }

    public String nextTransactionId() {
        return String.format("TXN%03d", nextTransactionNumber++);
    }

    public void save() { FileHandler.saveAll(accounts); }
}
