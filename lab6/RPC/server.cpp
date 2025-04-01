// Mann Patel
// 22BCP107

#include <iostream>
#include <string>
#include <winsock2.h>
#include <ws2tcpip.h>
#include <sstream>

#pragma comment(lib, "ws2_32.lib")  // Link with Winsock library

// Arithmetic operations
int add(int a, int b) {
    return a + b;
}

int subtract(int a, int b) {
    return a - b;
}

int multiply(int a, int b) {
    return a * b;
}

float divide(int a, int b) {
    if (b == 0) {
        std::cerr << "Error: Division by zero." << std::endl;
        return 0;
    }
    return static_cast<float>(a) / b;
}

void handle_rpc_request(SOCKET client_sock) {
    char buffer[1024] = {0};
    int valread = recv(client_sock, buffer, 1024, 0);

    if (valread <= 0) {
        return;
    }

    std::string request(buffer);
    std::string operation;
    int a, b;

    // Parse the request, e.g., "add 5 3"
    std::stringstream ss(request);
    ss >> operation;

    // Handle the "exit" command
    if (operation == "exit") {
        std::cout << "Client requested to exit." << std::endl;
        closesocket(client_sock);
        return;
    }

    ss >> a >> b;

    std::string response;

    if (operation == "add") {
        int result = add(a, b);
        response = "Result: " + std::to_string(result);
    } else if (operation == "subtract") {
        int result = subtract(a, b);
        response = "Result: " + std::to_string(result);
    } else if (operation == "multiply") {
        int result = multiply(a, b);
        response = "Result: " + std::to_string(result);
    } else if (operation == "divide") {
        if (b != 0) {
            float result = divide(a, b);
            response = "Result: " + std::to_string(result);
        } else {
            response = "Error: Division by zero";
        }
    } else {
        response = "Unknown operation";
    }

    send(client_sock, response.c_str(), response.length(), 0);
    closesocket(client_sock);
}

int main() {
    WSADATA wsaData;
    if (WSAStartup(MAKEWORD(2, 2), &wsaData) != 0) {
        std::cerr << "WSAStartup failed." << std::endl;
        return 1;
    }

    SOCKET server_fd, client_sock;
    struct sockaddr_in address;
    int addrlen = sizeof(address);

    // Creating socket
    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == INVALID_SOCKET) {
        std::cerr << "Socket creation failed." << std::endl;
        WSACleanup();
        return 1;
    }

    // Set the address and port
    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(8080);

    // Bind the socket to the network address and port
    if (bind(server_fd, (struct sockaddr*)&address, sizeof(address)) == SOCKET_ERROR) {
        std::cerr << "Bind failed." << std::endl;
        closesocket(server_fd);
        WSACleanup();
        return 1;
    }

    // Listen for incoming connections
    if (listen(server_fd, 3) == SOCKET_ERROR) {
        std::cerr << "Listen failed." << std::endl;
        closesocket(server_fd);
        WSACleanup();
        return 1;
    }

    std::cout << "Server is listening on port 8080..." << std::endl;

    while (true) {
        // Accept the incoming connection
        if ((client_sock = accept(server_fd, (struct sockaddr*)&address, &addrlen)) == INVALID_SOCKET) {
            std::cerr << "Accept failed." << std::endl;
            closesocket(server_fd);
            WSACleanup();
            return 1;
        }

        // Handle the client's RPC request
        handle_rpc_request(client_sock);
    }

    closesocket(server_fd);
    WSACleanup();
    return 0;
}
