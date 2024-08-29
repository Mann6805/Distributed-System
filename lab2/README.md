# Multi-Client Chat Application

## Description
The Multi-Client Chat Application is a Java-based project that facilitates real-time communication between multiple clients over a network. The server handles multiple client connections concurrently, allowing clients to send and receive messages in a group chat setting. This application is designed to be simple, yet robust, with features such as message broadcasting, client disconnection handling, and basic command processing.

## Features
- **Group Chat:** All connected clients can participate in a group chat where messages are broadcasted to everyone.
- **Multi-Threaded Server:** The server can handle multiple clients simultaneously using threading.
- **Asynchronous Communication:** Clients can continue typing while receiving messages.
- **Client Disconnection:** Clients can gracefully exit the chat using the `exit` command, notifying other clients of their departure.

## Prerequisites
- **Java Development Kit (JDK):** Ensure you have JDK installed on your machine (version 8 or higher).
- **Network:** Both the server and clients should be able to communicate over a common network (e.g., localhost for testing, or over a LAN/WAN).

## Compilation
To compile the server and client code, navigate to the directory containing the source files and run the following commands:

```bash
javac multiserver.java ClientHandler.java
javac multiclient.java
```
## Running the Application
### Starting the Server
To start the server, execute the following command in your terminal or command prompt:
```bash
java multiserver
```
The server will start listening for client connections on port 5000.
### Starting a Client
To start a client, open a new terminal or command prompt and execute the following command:
```bash
java multiclient
```
The client will prompt you to enter a username before connecting to the server. Once connected, you can start sending and receiving messages.

## Usage
- **Sending Messages:** Simply type your message and press Enter to send it to all connected clients.
- **Exiting the Chat:** Type /quit and press Enter to disconnect from the server.
- **Message Formatting:** Messages are prefixed with the sender's username to identify who sent the message.

## Request/Response Protocol
- Client to Server:
    - Message Format: `<username>: <message>`
    - Command Format: `exit` to exit the chat.
- Server to Client:
    - Broadcast Message: The server broadcasts each message it receives to all connected clients.
    - Client Connection/Disconnection Notification: The server notifies all clients when a new client joins or leaves the chat.

## Testing the Application
The application can be tested by running multiple client instances and interacting with the server. Refer to the following scenarios:
- **Scenario 1: Basic Messaging:** Connect two clients and exchange messages.
- **Scenario 2: Client Disconnection:** A client disconnects using the `exit` command.
- **Scenario 3: Abrupt Disconnection:** A client disconnects abruptly (e.g., by closing the terminal).

## Error Handling
The application includes basic error handling for common issues such as:
- **Connection Failures:** If the client fails to connect to the server, an error message is displayed.
- **Abrupt Disconnection:** The server gracefully handles a client disconnecting abruptly, ensuring other clients are notified.
- **Message Sending Errors:** If a message fails to send, the client attempts to close the connection gracefully.

## Limitations and Future Improvements
- **Private Messaging:** The current implementation does not support direct messaging between clients.
- **Security:** Messages are sent in plain text, making the communication vulnerable to interception. Future versions could implement encryption for secure communication.
- **GUI Interface:** Currently, the client is console-based. A graphical user interface (GUI) could be developed for a more user-friendly experience.
