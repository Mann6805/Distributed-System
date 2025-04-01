# Distributed System Simulation with Lamport Clock

Description:
This application simulates a distributed system using Lamport's logical clock algorithm. It models event handling, including local events, message sending, and message receiving across multiple processes to maintain causal ordering.

How Lamport's clock works:
- Logical Clock Assignment: Each process in a distributed system maintains a logical clock, starting from zero, which increments with each event.
- Local Events: When a process executes a local event, it increments its logical clock by one.
- Send Events: When a process sends a message, it increments its logical clock and timestamps the message with the updated clock value.
- Receive Events: Upon receiving a message, the process compares its current clock with the message’s timestamp. The receiving process sets its clock to the maximum of its current clock or the message’s timestamp, and then increments it by one.
- Causal Ordering: The logical clock helps maintain the causal order of events across processes, ensuring that if one event causally precedes another, the logical clock will reflect this order.

Features:
- Simulates local, send, and receive events in a distributed system.
- Uses Lamport's logical clock to ensure proper event ordering.
- Supports multiple processes with individual logical clocks.
- Command-line interaction to control event simulation.
- Displays the local time of each process before exiting.

Prerequisites:
- Java Development Kit (JDK) installed.
- Basic understanding of distributed systems and Lamport's clock algorithm.
- A terminal or command-line interface.

Compilation:
To compile the application, use the following command in the terminal:
- javac Main.java LamportClock.java Event.java

Running the Application:
To run the compiled application, use:
- java Main <number_of_processes>

Usage:
After starting the application, you can interact with it via the command line using the following commands:
- `LOCAL <process_id>`: Simulate a local event for the specified process.
- `SEND <sender_id> <receiver_id> [message]`: Simulate sending a message from the sender to the receiver. Optionally include a message.
- `EXIT`: Exit the simulation and print the final local times of all processes.

Testing the Application:
- Manually test by running the application and issuing different combinations of `LOCAL`, `SEND`, and `EXIT` commands.
- Observe the logical time progression for each process to verify that Lamport's clock algorithm is correctly implemented.
- Ensure that the causal ordering is maintained across processes.

Error Handling:
- The application checks for invalid commands and prints an error message.
- The application handles exceptions, such as `NumberFormatException`, and prints the error details to the console.
- The application will continue to run until the `EXIT` command is issued, regardless of command errors.	
