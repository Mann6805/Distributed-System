RPC

Aim:
To implement a simple Remote Procedure Call (RPC) client-server application using Winsock in C++ where the client can perform arithmetic operations (addition, subtraction, multiplication, division) and request the server to process them. The client also has an option to exit the program by sending an "exit" command.

Theory:
Remote Procedure Call (RPC) is a communication protocol used to allow a program to execute a procedure (function) on a different address space, typically on another physical server. The client sends a request, including the procedure name and its parameters, to the server, which then processes the request and sends back the result. In this example, we implement basic arithmetic operations via RPC over TCP using Winsock. The server listens on a specific port, waits for client connections, and processes the requested operations.

Compile files using:
	-g++ server.cpp -o server.exe -lws2_32
	-g++ client.cpp -o client.exe -lws2_32

Run files using:
	-server.exe
	-client.exe

Analysis:
The client successfully sends requests for arithmetic operations, which the server processes.
When the user sends the "exit" operation, the server gracefully shuts down.
The server handles multiple client connections and can perform different operations based on the requests it receives.

Conclusion:
The implemented RPC system using Winsock demonstrates a simple client-server architecture that can handle arithmetic operations and respond to the client appropriately. The system supports basic operations and also includes an "exit" command that allows the user to gracefully terminate the server. This project highlights the use of socket programming and inter-process communication using RPC over TCP.