// Mann Patel
// 22BCP107

#include <iostream>
#include <string>
#include <winsock2.h>
#include <ws2tcpip.h>  // Required for InetPton (not used anymore)
#pragma comment(lib, "ws2_32.lib")  // Link with Winsock library

// Function to make the RPC call
void rpc_call(const std::string& operation, int a = 0, int b = 0) {
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        std::cerr << "WSAStartup failed." << std::endl;
        return;
    }

    SOCKET sock = 0;
    struct sockaddr_in serv_addr;
    char buffer[1024] = {0};

    // Create socket
    if ((sock = socket(AF_INET, SOCK_STREAM, 0)) == INVALID_SOCKET) {
        std::cout << "Socket creation error" << std::endl;
        WSACleanup();
        return;
    }

    // Set server address
    serv_addr.sin_family = AF_INET;
    serv_addr.sin_port = htons(8080);

    // Use inet_addr instead of InetPton for compatibility
    serv_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

    // Connect to server
    if (connect(sock, (struct sockaddr*)&serv_addr, sizeof(serv_addr)) < 0) {
        std::cout << "Connection failed" << std::endl;
        closesocket(sock);
        WSACleanup();
        return;
    }

    // Prepare the request string
    std::string request = operation;
    if (operation != "exit") {
        request += " " + std::to_string(a) + " " + std::to_string(b);
    }

    // Send the request to the server
    send(sock, request.c_str(), request.length(), 0);

    // If operation is exit, skip reading the response
    if (operation == "exit") {
        closesocket(sock);
        WSACleanup();
        return;
    }

    // Read the response from the server
    int valread = recv(sock, buffer, 1024, 0);
    std::cout << "Server response: " << buffer << std::endl;

    closesocket(sock);
    WSACleanup();
}

int main() {
    std::string operation;
    int a, b;

    while (true) {
        // Take user input for operation and operands
        std::cout << "Enter operation (add/subtract/multiply/divide/exit): ";
        std::cin >> operation;

        if (operation == "exit") {
            // Make RPC call to the server to exit
            rpc_call(operation);
            break;
        }

        std::cout << "Enter two numbers: ";
        std::cin >> a >> b;

        // Make RPC call to the server
        rpc_call(operation, a, b);
    }

    return 0;
}
