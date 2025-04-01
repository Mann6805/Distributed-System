// Mann Patel - 22BCP107
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient 
{
    public static void main(String[] args) throws InterruptedException 
    {
        try
        {
            // Create a socket to connect to the server at localhost on port 6060
            Socket socket = new Socket("localhost", 6060);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Output stream to send messages to the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Input stream to receive messages from the server
            Scanner scanner = new Scanner(System.in); // Scanner for reading user input

            // Start a new thread to listen for incoming messages from the server
            new Thread(() -> 
            {
                String response;
                try 
                {
                    // Continuously read messages from the server
                    while ((response = in.readLine()) != null) 
                    {
                        System.out.println(response); // Print the received message
                    }
                } 
                catch (IOException e) 
                {
                    System.err.println("Connection closed: " + e.getMessage()); // Handle connection closure
                }
            }).start(); // Start the thread

            String message;
            while (true) 
            {
                // Prompt the user to enter a command or exit
                System.out.println("Enter a command (ECHO, ARITH, FILE) or type 'exit' to quit:");
                message = scanner.nextLine(); // Read user input
                if (message.equalsIgnoreCase("exit")) // Check if the user wants to exit
                {
                    break; // Exit the loop
                }
                out.println(message); // Send the message to the server
                Thread.sleep(1000); // Sleep for a second before allowing the next input
            }

            // Close the socket and scanner
            socket.close();
            scanner.close();
        } 
        catch (IOException e) 
        {
            System.err.println("Client error: " + e.getMessage()); // Handle any IO exceptions
        }
    }
}