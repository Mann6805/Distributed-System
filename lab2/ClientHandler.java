import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Runnable {

    // A thread-safe list to store all connected clients
    public static CopyOnWriteArrayList<ClientHandler> clienthandlers = new CopyOnWriteArrayList<>();
    
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String clientname;

    // Constructor to initialize the client connection and set up streams
    public ClientHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            // Read the client's name from the input stream
            this.clientname = reader.readLine();
            
            // Add this client handler to the list of active clients
            clienthandlers.add(this);
            
            // Broadcast a message to all clients that a new client has joined
            broadcastMessage("Server: " + clientname + " has entered the chat.");
        } catch (Exception e) {
            // Close the connection if an exception occurs during initialization
            closeConnection(clientSocket, reader, writer);
            System.out.println("Error on client side.");
        }
    }

    // The run method is executed when the thread is started
    @Override
    public void run() {
        String message;

        // Keep listening for messages from the client while the socket is connected
        while (clientSocket.isConnected()) {
            try {
                // Read a message from the client
                message = reader.readLine();
                
                // Broadcast the received message to all other clients
                broadcastMessage(message);
            } catch (Exception e) {
                // Close the connection if an exception occurs while reading a message
                closeConnection(clientSocket, reader, writer);
                break;
            }
        }
    }

    // Method to broadcast a message to all connected clients except the sender
    public void broadcastMessage(String message) {
        for (ClientHandler client : clienthandlers) {
            try {
                // Send the message to all clients except the one who sent it
                if (!client.clientname.equals(clientname)) {
                    client.writer.write(message);
                    client.writer.newLine();
                    client.writer.flush();
                }
            } catch (Exception e) {
                // Close the connection if an exception occurs while broadcasting
                closeConnection(clientSocket, reader, writer);
            }
        }
    }

    // Method to remove the client handler from the list when the client disconnects
    public void removeClientHandler() {
        clienthandlers.remove(this);
        broadcastMessage("Server: " + clientname + " has left the chat.");
    }

    // Method to close the connection and clean up resources
    public void closeConnection(Socket clientSocket, BufferedReader reader, BufferedWriter writer) {
        removeClientHandler(); // Remove the client handler from the list
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

}