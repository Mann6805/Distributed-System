# RMI

Aim:
To implement a basic banking system using Java RMI (Remote Method Invocation) that supports account creation, balance inquiry, deposits, and withdrawals in a client-server architecture.

Theory:
Remote Method Invocation (RMI) in Java allows methods to be invoked from an object running on another JVM (Java Virtual Machine). This approach is useful for distributed systems where processes can communicate remotely.

System Architecture:
1. Client-Server Architecture:

	-The system follows a classic client-server model.
	-BankClient acts as the client, interacting with the server to request services.
	-BankServer acts as the central server that handles client requests and processes the required banking operations.

2. Interface (BankInterface):

	-BankInterface defines the methods available for client-server interaction, which helps decouple the client from server implementation details.
	-This design decision promotes flexibility and maintainability as the interface ensures that both the client and server adhere to a standard contract.

3. Communication:

	-The communication likely involves Remote Method Invocation (RMI) or similar technology to allow the BankClient to invoke methods on the BankServer as if they were local, though 	details on communication technology are not present in the files themselves.

Design Decisions:
1. Abstraction via Interface:

	-Using BankInterface abstracts the functionalities and ensures that any changes to server-side implementation will not affect the client, promoting loose coupling.

2. Modular Approach:

	-The system is modular, separating client-side logic, server-side logic, and communication via the interface. This separation of concerns improves maintainability.

3. Scalability:

	-With the client-server model, the system can scale by adding more clients without affecting the server design. Additionally, servers can be optimized independently.

Analysis:
Advantages: This implementation demonstrates a simple client-server architecture using Java RMI for distributed banking services. RMI abstracts the complexity of socket programming by allowing method invocation across JVMs as if it were local.

Limitations: This basic implementation does not include advanced features like user authentication, data persistence (account data is not saved after the server stops), or transaction history.

Conclusion:
The project successfully demonstrates the use of Java RMI to create a distributed banking application where clients can create accounts, check balances, deposit, and withdraw money through remote method invocations. This illustrates the fundamental concepts of distributed computing and remote communication using Java RMI.

How to run code:

Compile all files - javac BankInterface.java BankServer.java BankClient.java
Run BankServer - java BankServer
Run Client - java Client






