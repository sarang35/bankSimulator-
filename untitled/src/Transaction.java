import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/** Stores one account activity. */
public class Transaction {
    private String transactionId;
    private String type;
    private double amount;
    private String description;
    private LocalDateTime dateTime;
    private double balanceAfterTransaction;

    public Transaction(String transactionId, String type, double amount,
                       String description, LocalDateTime dateTime,
                       double balanceAfterTransaction) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.dateTime = dateTime;
        this.balanceAfterTransaction = balanceAfterTransaction;
    }

    public String getTransactionId() { return transactionId; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public LocalDateTime getDateTime() { return dateTime; }
    public double getBalanceAfterTransaction() { return balanceAfterTransaction; }

    public String toFileString(int accountNumber) {
        return accountNumber + "|" + transactionId + "|" + type + "|" + amount + "|"
                + description.replace("|", "/") + "|" + dateTime + "|" + balanceAfterTransaction;
    }

    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return transactionId + " | " + type + "\n"
                + "Amount: ₹" + String.format("%.2f", amount) + "\n"
                + "Description: " + description + "\n"
                + "Balance: ₹" + String.format("%.2f", balanceAfterTransaction) + "\n"
                + "Date: " + dateTime.format(format);
    }
}
