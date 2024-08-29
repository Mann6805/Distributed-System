import java.net.ServerSocket;
import java.net.Socket;

public class multiserver {
    
    private ServerSocket serverSocket;

    // Constructor to initialize the server with a given ServerSocket
    public multiserver(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // Method to start the server and listen for client connections
    public void startServer() {
        System.out.println("Server is started.");
        try {
            // Keep the server running as long as it's not closed
            while (!serverSocket.isClosed()) {

                // Check if the server socket is valid, not closed, and bound to a port
                if (serverSocket != null && !serverSocket.isClosed() && serverSocket.isBound()) {
                    // Accept a new client connection
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("A new client is connected.");

                    // Create a new ClientHandler to manage the client connection
                    ClientHandler clientHandler = new ClientHandler(clientSocket);

                    // Start a new thread to handle the client's requests
                    Thread thread = new Thread(clientHandler);
                    thread.start();
                }
            }
        } catch (Exception e) {
            // Handle any exceptions that occur while connecting a client
            System.out.println("Error connecting client to server.");
        }
    }

    // Method to close the server socket
    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close(); // Close the server socket if it's not null
            }
        } catch (Exception e) {
            // Handle any exceptions that occur while closing the server
            System.out.println("Error closing server connection.");
        }
    }

    // Main method to start the server
    public static void main(String[] args) throws Exception {
        // Create a ServerSocket that listens on port 5000
        ServerSocket serverSocket = new ServerSocket(5000);
        
        // Instantiate the multiserver class and start the server
        multiserver server = new multiserver(serverSocket);
        server.startServer();
    }
}
