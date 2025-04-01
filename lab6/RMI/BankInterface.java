// Mann Patel
// 22BCP107

import java.rmi.Remote;
import java.rmi.RemoteException;

// The BankInterface defines the methods that can be invoked remotely by the client.
public interface BankInterface extends Remote {
    
    // Method to create a new account. Takes the account holder's name as input 
    // and returns a unique account ID. Throws RemoteException in case of any issues during remote invocation.
    int createAccount(String accountHolderName) throws RemoteException;
    
    // Method to get the balance of an account. Takes the account ID as input 
    // and returns the current balance in the account. Throws RemoteException in case of issues.
    double getBalance(int accountId) throws RemoteException;
    
    // Method to deposit money into an account. Takes the account ID and the amount to deposit as inputs.
    // Throws RemoteException in case of any issues during the remote call.
    void deposit(int accountId, double amount) throws RemoteException;
    
    // Method to withdraw money from an account. Takes the account ID and the amount to withdraw as inputs.
    // Returns true if the withdrawal is successful, or false if there are insufficient funds.
    // Throws RemoteException if there are any issues during the remote call.
    boolean withdraw(int accountId, double amount) throws RemoteException;
}
