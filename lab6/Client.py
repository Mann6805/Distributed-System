# Name - Rhythm Shah
# Roll No - 22BCP071

import xmlrpc.client  # Import the module to use XML-RPC for communication between the client and server

# Function to get two numbers as input from the user
def get_numbers():
    a = int(input("Enter first number: "))  
    b = int(input("Enter second number: ")) 
    return a, b  # Return both numbers

def main():
    # Create a proxy object for communication with the XML-RPC server at the specified URL
    proxy = xmlrpc.client.ServerProxy("http://localhost:8000/")
    
    # Continuously show the menu until the user chooses to exit
    while True:
        # Display the available operations
        print("\nAvailable operations:")
        print("1. Add")
        print("2. Subtract")
        print("3. Multiply")
        print("4. Divide")
        print("5. Exit")
        
        # Take the user's choice
        choice = input("Enter your choice (1-5): ")
        
        # Exit the loop if the user chooses to quit
        if choice == '5':
            break

        a, b = get_numbers()
        
        # Try to perform the chosen operation, handling possible errors
        try:
            # Based on the choice, call the corresponding method on the server
            if choice == '1':
                result = proxy.add(a, b)  
                print(f"Result: {a} + {b} = {result}")
            elif choice == '2':
                result = proxy.subtract(a, b)  
                print(f"Result: {a} - {b} = {result}")
            elif choice == '3':
                result = proxy.multiply(a, b)  
                print(f"Result: {a} * {b} = {result}")
            elif choice == '4':
                result = proxy.divide(a, b) 
                print(f"Result: {a} / {b} = {result}")
            else:
                # If the user enters an invalid choice, show an error message
                print("Invalid choice. Please try again.")
        except ValueError as e:
            # Handle ValueError, for example, if invalid input is provided
            print(f"Error: {e}")
        except xmlrpc.client.Fault as e:
            # Handle XML-RPC specific server-side errors
            print(f"Server error: {e}")
        except ConnectionRefusedError:
            # Handle the case when the server is not running or cannot be reached
            print("Error: Could not connect to the server. Make sure the server is running.")

# Ensure the main function runs when the script is executed
if __name__ == "__main__":
    main()
