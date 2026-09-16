import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/** Reads and writes account and transaction data in simple text files. */
public class FileHandler {
    private static final String ACCOUNT_FILE = "accounts.txt";
    private static final String TRANSACTION_FILE = "transactions.txt";

    public static ArrayList<Account> loadAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        File file = new File(ACCOUNT_FILE);
        if (!file.exists()) return accounts;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|", -1);
                if (p.length == 6) {
                    accounts.add(new Account(p[0], Integer.parseInt(p[1]), p[2],
                            Double.parseDouble(p[3]), p[4], p[5]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not load accounts: " + e.getMessage());
        }
        loadTransactions(accounts);
        return accounts;
    }

    private static void loadTransactions(ArrayList<Account> accounts) {
        File file = new File(TRANSACTION_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|", -1);
                if (p.length == 7) {
                    int number = Integer.parseInt(p[0]);
                    for (Account account : accounts) {
                        if (account.getAccountNumber() == number) {
                            account.addTransaction(new Transaction(p[1], p[2], Double.parseDouble(p[3]),
                                    p[4], LocalDateTime.parse(p[5]), Double.parseDouble(p[6])));
                            break;
                        }
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println("Could not load transactions: " + e.getMessage());
        }
    }

    public static void saveAll(ArrayList<Account> accounts) {
        try (FileWriter accountWriter = new FileWriter(ACCOUNT_FILE);
             FileWriter transactionWriter = new FileWriter(TRANSACTION_FILE)) {
            for (Account account : accounts) {
                accountWriter.write(account.getName() + "|" + account.getAccountNumber() + "|"
                        + account.getPin() + "|" + account.getBalance() + "|"
                        + account.getPhone() + "|" + account.getEmail() + System.lineSeparator());
                for (Transaction transaction : account.getTransactions()) {
                    transactionWriter.write(transaction.toFileString(account.getAccountNumber())
                            + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("Could not save data: " + e.getMessage());
        }
    }
}
