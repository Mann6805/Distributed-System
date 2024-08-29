import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class multiclient {

    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;

    // Constructor to initialize the client socket and set up I/O streams
    public multiclient(Socket clientSocket, String username) {
        try {
            this.clientSocket = clientSocket;
            this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.username = username;
        } catch (Exception e) {
            // Close the connection if an exception occurs during initialization
            closeConnection(clientSocket, reader, writer);
        }
    }

    // Method to handle sending messages from the client to the server
    public void sendMessage() {
        try {
            // Send the username to the server when the client connects
            writer.write(this.username);
            writer.newLine();
            writer.flush();

            Scanner sc = new Scanner(System.in);

            // Continuously listen for user input to send messages to the server
            while (clientSocket.isConnected()) {
                String message = sc.nextLine();

                // If the user types "exit", disconnect the client
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Disconnecting you.....");
                    System.exit(0);
                }

                // Send the message to the server
                writer.write(username + ": " + message);
                writer.newLine();
                writer.flush();
            }

            // Close the scanner once the connection is closed
            sc.close();
        } catch (Exception e) {
            // Close the connection if an exception occurs while sending a message
            closeConnection(clientSocket, reader, writer);
        }
    }

    // Method to handle listening for messages from the server
    public void listenForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgfromgroup;

                // Continuously listen for messages from the server
                while (clientSocket.isConnected()) {
                    try {
                        msgfromgroup = reader.readLine();
                        System.out.println(msgfromgroup); // Print the received message to the console
                    } catch (Exception e) {
                        // Handle the case where the server is lost or disconnected
                        System.out.println("Server lost");
                        closeConnection(clientSocket, reader, writer);
                        System.exit(0);
                    }
                }
            }
        }).start(); // Start a new thread to listen for messages
    }

    // Method to close the connection and clean up resources
    public void closeConnection(Socket clientSocket, BufferedReader reader, BufferedWriter writer) {
        try {
            if (reader != null) {
                reader.close(); // Close the BufferedReader if it's not null
            }
            if (writer != null) {
                writer.close(); // Close the BufferedWriter if it's not null
            }
            if (clientSocket != null) {
                clientSocket.close(); // Close the Socket if it's not null
            }
        } catch (Exception e) {
            System.out.println("Error closing client connection.");
        }
    }

    // Main method to start the client
    public static void main(String[] args) throws Exception {
        try {
            // Create a new client socket and connect to the server on localhost and port 5000
            Socket clientSocket = new Socket("localhost", 5000);

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter your username: ");
            String username = sc.nextLine();

            // Instantiate the multiclient class with the socket and username
            multiclient Client = new multiclient(clientSocket, username);

            // Start listening for messages from the server
            Client.listenForMessage();

            // Start sending messages to the server
            Client.sendMessage();

            // Close the scanner after the client disconnects
            sc.close();
        } catch (Exception e) {
            // Handle the case where no server is found or the connection fails
            System.out.println("No server found");
        }
    }
}
