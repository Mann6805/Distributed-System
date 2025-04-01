// Mann Patel - 22BCP107
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer 
{
    // Set to hold PrintWriter objects for each connected client
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) 
    {
        System.out.println("Chat server started..."); // Notify that the server has started
        try (ServerSocket serverSocket = new ServerSocket(6060)) // Create a server socket on port 6060
        {
            // Start a new thread to handle server messages
            new Thread(new ServerMessageHandler()).start();

            while (true) 
            {
                // Wait for a client to connect
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client Connected"); // Notify that a client has connected
                // Start a new thread to handle the connected client
                new ClientHandler(clientSocket).start();
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Server error: " + e.getMessage()); // Handle server errors
        }
    }

    // Inner class to handle individual client connections
    private static class ClientHandler extends Thread 
    {
        private Socket socket; // Socket for the client connection
        private PrintWriter out; // Output stream to send messages to the client
        private BufferedReader in; // Input stream to receive messages from the client

        public ClientHandler(Socket socket) 
        {
            this.socket = socket; // Initialize the client socket
        }

        public void run() 
        {
            try 
            {
                // Initialize input and output streams
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
                
                // Add the client's PrintWriter to the set of writers
                synchronized (clientWriters) 
                {
                    clientWriters.add(out);
                }

                String message;
                // Continuously read messages from the client
                while ((message = in.readLine()) != null) 
                {
                    System.out.println("Received: " + message); // Print the received message
                    processRequest(message); // Process the client's request
                }
            } 
            catch (IOException e) 
            {
                System.err.println("Client disconnected: " + e.getMessage()); // Handle client disconnection
            } 
            finally 
            {
                try 
                {
                    socket.close(); // Close the client socket
                } 
                catch (IOException e) 
                {
                    System.err.println("Error closing socket: " + e.getMessage()); // Handle socket closure errors
                }
                // Remove the client's PrintWriter from the set
                synchronized (clientWriters) 
                {
                    clientWriters.remove(out);
                }
            }
        }

        // Process the client's request based on the message received
        private void processRequest(String message) 
        {
            try 
            {
                if (message.startsWith("ECHO:")) // Check for ECHO command
                {
                    String echoMessage = message.substring(5).trim(); // Extract the message to echo
                    out.println(echoMessage); // Send the echo message back to the client
                } 
                else if (message.startsWith("ARITH:")) // Check for ARITH command
                {
                    String expression = message.substring(6).trim(); // Extract the arithmetic expression
                    String result = evaluateArithmetic(expression); // Evaluate the expression
                    out.println("ARITH RESULT: " + result); // Send the result back to the client
                } 
                else if (message.startsWith("FILE:")) // Check for FILE command
                {
                    String filename = message.substring(5).trim(); // Extract the filename
                    sendFileContents(filename); // Send the contents of the file to the client
                } 
                else 
                {
                    out.println("ERROR: Invalid request format"); // Handle invalid request format
                }
            } 
            catch (Exception e) 
            {
                out.println("ERROR: " + e.getMessage()); // Handle exceptions
            }
        }

        // Evaluate a simple arithmetic expression
        private String evaluateArithmetic(String expression) 
        {
            try 
            {
                // Split the expression into tokens
                String[] tokens = expression.split(" ");
                if (tokens.length != 3) 
                {
                    return "ERROR: Invalid arithmetic format"; // Check for valid format
                }
                double num1 = Double.parseDouble(tokens[0]); // Parse the first number
                double num2 = Double.parseDouble(tokens[2]); // Parse the second number
                String operator = tokens[1]; // Get the operator

                // Perform the arithmetic operation based on the operator
                switch (operator) 
                {
                    case "+":
                        return String.valueOf(num1 + num2);
                    case "-":
                        return String.valueOf(num1 - num2);
                    case "*":
                        return String.valueOf(num1 * num2);
                    case "/":
                        if (num2 == 0) 
                        {
                            return "ERROR: Division by zero"; // Handle division by zero
                        }
                        return String.valueOf(num1 / num2);
                    default:
                        return "ERROR: Unsupported operator"; // Handle unsupported operators
                }
            } 
            catch (NumberFormatException e) 
            {
                return "ERROR: Invalid number format"; // Handle invalid number format
            }
        }

        // Send the contents of a specified file to the client
        private void sendFileContents(String filename) 
        {
            try (BufferedReader fileReader = new BufferedReader(new FileReader(filename))) 
            {
                String line;
                // Read the file line by line and send each line to the client
                while ((line = fileReader.readLine()) != null) 
                {
                    out.println(line); // Send the line to the client
                }
            } 
            catch (IOException e) 
            {
                out.println("ERROR: Unable to read file - " + e.getMessage()); // Handle file reading errors
            }
        }
    }

    // Inner class to handle server-side console input
    private static class ServerMessageHandler implements Runnable 
    {
        private BufferedReader consoleInput; // BufferedReader for reading console input

        public ServerMessageHandler() 
        {
            consoleInput = new BufferedReader(new InputStreamReader(System.in)); // Initialize the console input reader
        }

        @Override
        public void run() 
        {
            String serverMessage;
            try 
            {
                // Continuously read messages from the server console
                while ((serverMessage = consoleInput.readLine()) != null) 
                {
                    // Send the server message to all connected clients
                    synchronized (clientWriters) 
                    {
                        for (PrintWriter writer : clientWriters) 
                        {
                            writer.println("Server: " + serverMessage);
                        }
                    }
                }
            } 
            catch (IOException e) 
            {
                System.err.println("Error reading server input: " + e.getMessage()); // Handle console input errors
            }
        }
    }
}
