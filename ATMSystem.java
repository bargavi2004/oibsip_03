import java.util.ArrayList;
import java.util.Scanner;
class Account {
    private String userId;
    private String pin;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account(String userId, String pin) {
        this.userId = userId;
        this.pin = pin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdrawal", amount));
            return true;
        } else {
            System.out.println("Insufficient funds");
            return false;
        }
    }

    public boolean transfer(Account recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
            return true;
        } else {
            System.out.println("Insufficient funds for transfer");
            return false;
        }
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}
public class ATMSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Create accounts (for demonstration purposes)
        Account account1 = new Account("user1", "1234");
        Account account2 = new Account("user2", "5678");

        // ATM system
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        Account currentAccount = null;

        // Validate user credentials
        if (userId.equals(account1.getUserId()) && account1.validatePin(pin)) {
            currentAccount = account1;
        } else if (userId.equals(account2.getUserId()) && account2.validatePin(pin)) {
            currentAccount = account2;
        } else {
            System.out.println("Invalid User ID or PIN. Exiting...");
            System.exit(0);
        }

        int choice;
        do {
            System.out.println("\nATM Menu:");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    currentAccount.displayTransactionHistory();
                    break;
                case 2:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    currentAccount.withdraw(withdrawAmount);
                    break;
                case 3:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    currentAccount.deposit(depositAmount);
                    break;
                case 4:
                    System.out.print("Enter recipient's User ID: ");
                    String recipientId = scanner.next();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();

                    // Find the recipient account
                    Account recipientAccount = null;
                    if (recipientId.equals(account1.getUserId())) {
                        recipientAccount = account1;
                    } else if (recipientId.equals(account2.getUserId())) {
                        recipientAccount = account2;
                    } else {
                        System.out.println("Recipient not found. Transfer canceled.");
                        break;
                    }

                    // Perform transfer
                    currentAccount.transfer(recipientAccount, transferAmount);
                    break;
                case 5:
                    System.out.println("Exiting ATM. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

        } while (choice != 5);

        scanner.close();
    }
}
