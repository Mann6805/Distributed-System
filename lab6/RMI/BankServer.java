//   Patel
// 22BCP107

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// BankServer class that implements the BankInterface, enabling remote method calls.
public class BankServer extends UnicastRemoteObject implements BankInterface {
    
    // A HashMap to store account details where the key is the account ID and the value is the Account object.
    private Map<Integer, Account> accounts = new HashMap<>();
    
    // AtomicInteger to generate unique account IDs in a thread-safe manner.
    private AtomicInteger accountIdCounter = new AtomicInteger(1);

    // Constructor that throws RemoteException, required for RMI implementation.
    protected BankServer() throws RemoteException {
        super(); // Call to the superclass UnicastRemoteObject constructor.
    }

    // Synchronized method to create a new account. Returns a unique account ID.
    @Override
    public synchronized int createAccount(String accountHolderName) throws RemoteException {
        int accountId = accountIdCounter.getAndIncrement(); // Generate a unique account ID.
        accounts.put(accountId, new Account(accountId, accountHolderName)); // Store the account in the HashMap.
        return accountId; // Return the newly generated account ID.
    }

    // Synchronized method to get the balance of an account. Throws RemoteException if the account is not found.
    @Override
    public synchronized double getBalance(int accountId) throws RemoteException {
        Account account = accounts.get(accountId); // Retrieve the account by ID.
        if (account == null) {
            throw new RemoteException("Account not found"); // If account doesn't exist, throw an exception.
        }
        return account.getBalance(); // Return the account balance.
    }

    // Synchronized method to deposit an amount into an account. Throws RemoteException if the account is not found.
    @Override
    public synchronized void deposit(int accountId, double amount) throws RemoteException {
        Account account = accounts.get(accountId); // Retrieve the account by ID.
        if (account == null) {
            throw new RemoteException("Account not found"); // If account doesn't exist, throw an exception.
        }
        account.deposit(amount); // Perform the deposit.
    }

    // Synchronized method to withdraw an amount from an account. Returns false if insufficient funds.
    @Override
    public synchronized boolean withdraw(int accountId, double amount) throws RemoteException {
        Account account = accounts.get(accountId); // Retrieve the account by ID.
        if (account == null) {
            throw new RemoteException("Account not found"); // If account doesn't exist, throw an exception.
        }
        return account.withdraw(amount); // Attempt to withdraw, return success or failure.
    }

    // Inner Account class representing individual bank accounts.
    private static class Account {
        // Variables to store the account ID, account holder's name, and balance.
        @SuppressWarnings("unused")
        private int id;
        @SuppressWarnings("unused")
        private String holderName;
        private double balance;

        // Constructor for the Account class, initializing the account ID and holder's name.
        public Account(int id, String holderName) {
            this.id = id;
            this.holderName = holderName;
            this.balance = 0.0; // Default balance is 0.
        }

        // Getter method to return the account balance.
        public double getBalance() {
            return balance;
        }

        // Method to deposit an amount into the account.
        public void deposit(double amount) {
            if (amount > 0) {
                balance += amount; // Add the amount to the balance if it is positive.
            }
        }

        // Method to withdraw an amount from the account. Returns true if successful, false if insufficient funds.
        public boolean withdraw(double amount) {
            if (amount > 0 && balance >= amount) { // Ensure the withdrawal amount is positive and sufficient balance is available.
                balance -= amount; // Deduct the amount from the balance.
                return true;
            }
            return false; // Return false if the withdrawal cannot be processed.
        }
    }

    // Main method to start the RMI server.
    public static void main(String[] args) {
        try {
            BankServer server = new BankServer(); // Create a new instance of the BankServer.
            Registry registry = LocateRegistry.createRegistry(1099); // Create an RMI registry on port 1099.
            registry.rebind("BankService", server); // Bind the BankServer instance to the name "BankService".
            System.out.println("Bank Server is running..."); // Inform that the server is running.
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e.toString()); // Handle and print any server-related exceptions.
            e.printStackTrace();
        }
    }
}
