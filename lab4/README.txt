Distributed Mutual Exclusion Algorithms: Ricart-Agrawala and Maekawa
This project implements and compares two distributed mutual exclusion algorithms: Maekawa's Algorithm and Ricart-Agrawala's Algorithm. These algorithms are used to manage access to a critical section in a distributed system.

How to Run the Simulation:

1) Ricart-Agrawala Algorithm

i) Navigate to the directory where the Java files are located and compile them using the following command:
   javac Main.java Process.java DistributedSystem.java 

ii) Execute the Ricart-Agrawala simulation with the following command:
    java Main

Algorithm Implementations:

i)Communication Protocol:

Each process sends a REQUEST message to all other processes when it wants to enter the critical section.
Processes reply with a REPLY message to acknowledge the request.
A process can enter the critical section only when it has received a reply from all other processes.

ii)Steps to Enter the Critical Section:

Increment the local timestamp and update the request timestamp.
Send a request message to all other processes.
Wait for replies from all processes.
Enter the critical section once all replies are received.
Exit the critical section, reset the request timestamp, and clear reply statuses

Testing the System:

Low Contention: Start the simulation with a small number of processes (e.g., 3-5). Request the critical section from different processes and observe the order of entry and the number of messages exchanged.
High Contention: Increase the number of processes (e.g., 10 or more) and simulate multiple simultaneous requests. Analyze the message complexity and response time.


2) Maekawa’s Voting Algorithm

   How to Run the Simulation:
i) Navigate to the directory where the Java files are located and compile them using the following command:
   javac MaekawaAlgorithm.java
 
ii) Execute the Ricart-Agrawala simulation with the following command:
    java MaekawaAlgorithm

Algorithm Implementations

i)Communication Protocol:

Each process is part of a quorum. When requesting access to the critical section, it sends a request to all members of its quorum.
The process must receive permission from a majority of its quorum members before entering the critical section.
After completing the critical section, the process sends a release message to its quorum members.

ii)Steps to Enter the Critical Section:

Send a request message to all quorum members.
Wait for permissions from a majority of quorum members.
Enter the critical section once the majority has granted permission.
After completing the critical section, send release messages to all quorum members.

Testing the System:

Low Contention: Set up the quorum groups for a small number of processes and simulate a single process requesting the critical section. Observe the number of messages exchanged and the response time.
High Contention: Define larger quorums and simulate multiple processes requesting access at the same time. Analyze the impact on response time and fairness.