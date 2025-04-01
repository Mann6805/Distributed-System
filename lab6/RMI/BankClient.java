// Mann Patel
// 22BCP107

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class BankClient {
    // This variable holds the reference to the remote BankInterface service.
    private BankInterface bankService;

    // Constructor for the BankClient. It connects to the RMI Registry and looks up the BankService.
    public BankClient() {
        try {
            // Get the RMI registry running on localhost and port 1099.
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            
            // Look up the "BankService" in the registry and cast it to BankInterface.
            bankService = (BankInterface) registry.lookup("BankService");
        } catch (Exception e) {
            // Print any exceptions that occur during the lookup process.
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    // Method to start the interaction with the user.
    public void start() {
        // Create a Scanner object for reading user input.
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // Display the menu options to the user.
            System.out.println("\n1. Create Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit");
            System.out.println("4. Withdraw");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            // Read the user's choice from the menu.
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character left by nextInt()

            try {
                // Handle the user's choice using a switch statement.
                switch (choice) {
                    case 1:
                        // Create a new account.
                        createAccount(scanner);
                        break;
                    case 2:
                        // Check the balance of an existing account.
                        checkBalance(scanner);
                        break;
                    case 3:
                        // Deposit money into an account.
                        deposit(scanner);
                        break;
                    case 4:
                        // Withdraw money from an account.
                        withdraw(scanner);
                        break;
                    case 5:
                        // Exit the client application.
                        System.out.println("Exiting...");
                        return; // Exit the method, ending the client.
                    default:
                        // Handle invalid choices.
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                // Handle any exceptions that occur during the transaction with the bank service.
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    // Method to create a new account by taking user input for account holder's name.
    private void createAccount(Scanner scanner) throws Exception {
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine(); // Read the account holder's name.
        
        // Call the remote method to create an account, passing the name.
        int accountId = bankService.createAccount(name);
        
        // Display the new account ID to the user.
        System.out.println("Account created successfully. Account ID: " + accountId);
    }

    // Method to check the balance of an account.
    private void checkBalance(Scanner scanner) throws Exception {
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt(); // Read the account ID from the user.
        
        // Call the remote method to get the balance of the specified account.
        double balance = bankService.getBalance(accountId);
        
        // Display the current balance.
        System.out.println("Current balance: $" + balance);
    }

    // Method to deposit money into an account.
    private void deposit(Scanner scanner) throws Exception {
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt(); // Read the account ID.
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble(); // Read the deposit amount.
        
        // Call the remote method to deposit money into the account.
        bankService.deposit(accountId, amount);
        
        // Notify the user of the successful deposit.
        System.out.println("Deposit successful.");
    }

    // Method to withdraw money from an account.
    private void withdraw(Scanner scanner) throws Exception {
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt(); // Read the account ID.
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble(); // Read the withdrawal amount.
        
        // Call the remote method to withdraw money from the account.
        boolean success = bankService.withdraw(accountId, amount);
        
        // Notify the user if the withdrawal was successful or failed due to insufficient funds.
        if (success) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed. Insufficient funds.");
        }
    }

    // Main method to run the client program.
    public static void main(String[] args) {
        // Create a new BankClient instance.
        BankClient client = new BankClient();
        
        // Start the client interaction.
        client.start();
    }
}
