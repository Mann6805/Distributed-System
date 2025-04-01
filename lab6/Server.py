# Name - Rhythm Shah
# Roll No - 22BCP071

from xmlrpc.server import SimpleXMLRPCServer  # Import the SimpleXMLRPCServer to create an XML-RPC server

def add(a, b):
    return a + b  

def subtract(a, b):
    return a - b  

def multiply(a, b):
    return a * b  

def divide(a, b):
    if b == 0:
        raise ValueError("Cannot divide by zero")
    return a / b  

# Create an instance of the XML-RPC server, listening on localhost at port 8000
server = SimpleXMLRPCServer(("localhost", 8000))
print("Listening on port 8000...") 

# Register the functions to be called remotely via XML-RPC
server.register_function(add, "add")          # Register the add function
server.register_function(subtract, "subtract")  # Register the subtract function
server.register_function(multiply, "multiply")  # Register the multiply function
server.register_function(divide, "divide")      # Register the divide function

# Keep the server running, waiting for client requests
server.serve_forever()  # Start the server and handle incoming requests indefinitely
