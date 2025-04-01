// Mann Patel 22BCP107

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MaekawaAlgorithm {
    
    // Inner class representing a process in the Maekawa algorithm
    private static class Process {
        int processId;  // Unique ID of the process
        Set<Integer> group;  // Group of processes that this process communicates with
        boolean requesting;  // True if the process is requesting critical section access
        boolean granted;  // True if the process has been granted access to the critical section

        // Constructor for the Process class
        Process(int processId) {
            this.processId = processId;
            this.group = new HashSet<>();  // Group of processes to communicate with
            this.requesting = false;  // Initially not requesting the critical section
            this.granted = false;  // Initially not granted the critical section
        }

        // Method to request access to the critical section
        void requestCS() {
            requesting = true;  // Mark the process as requesting access
            System.out.println("Process " + processId + " is requesting access to the critical section.");
            
            // Send request to all processes in the group
            for (Integer member : group) {
                System.out.println("Request sent to Process " + member);
                // Simulate request sending logic (e.g., actual communication between processes)
            }
        }

        // Method to release access to the critical section
        void releaseCS() {
            requesting = false;  // Mark the process as not requesting anymore
            System.out.println("Process " + processId + " has exited the critical section.");
            
            // Send release message to all members of the group
            for (Integer member : group) {
                System.out.println("Release message sent to Process " + member);
                // Simulate release sending logic
            }
        }

        // Method to check if the process can enter the critical section
        boolean canEnterCS() {
            return !requesting && granted;  // Can enter if not requesting and access has been granted
        }
    }

    private Map<Integer, Process> processMap;  // Map to store process objects by ID
    private Queue<Integer> processQueue;  // Queue to manage the order of processes requesting critical section
    private Integer currentProcessInCS;  // ID of the process currently in the critical section

    // Constructor for the MaekawaAlgorithm class
    public MaekawaAlgorithm(int numProcesses) {
        processMap = new ConcurrentHashMap<>();  // Thread-safe map for storing processes
        processQueue = new ConcurrentLinkedQueue<>();  // Thread-safe queue for handling critical section requests
        currentProcessInCS = null;  // Initially no process is in the critical section

        // Create and store each process in the map
        for (int i = 1; i <= numProcesses; i++) {
            processMap.put(i, new Process(i));
        }
    }

    // Method to set the group of a process
    public void setGroup(int processId, Set<Integer> group) {
        if (processMap.containsKey(processId)) {
            processMap.get(processId).group = group;  // Set the group for the specified process
        }
    }

    // Method to simulate a process requesting the critical section
    public void simulate(int processId) {
        Process process = processMap.get(processId);
        if (process == null)
            return;  // If the process does not exist, exit

        process.requestCS();  // Process requests the critical section
        processQueue.add(processId);  // Add the process to the request queue

        // Start the loop to handle critical section access
        while (true) {
            if (currentProcessInCS == null) {
                grantAccess();  // Grant access to the next process if critical section is free
            }

            // Exit the loop if no more processes are waiting and the critical section is free
            if (processQueue.isEmpty() && currentProcessInCS == null) {
                break;
            }
        }
    }

    // Method to grant access to the critical section
    private void grantAccess() {
        Integer nextProcessId = processQueue.poll();  // Get the next process in the queue
        if (nextProcessId == null)
            return;

        Process process = processMap.get(nextProcessId);
        if (process != null) {
            process.granted = true;  // Mark the process as granted access
            currentProcessInCS = nextProcessId;  // Set this process as the one in the critical section
            System.out.println("Process " + nextProcessId + " is entering the critical section.");

            // Simulate the critical section execution
            Scanner scanner = new Scanner(System.in);
            System.out.println("Process " + nextProcessId + " is in the critical section.");
            System.out.println("Do you wish to add another process? (yes/no): ");
            String response = scanner.nextLine();

            // Optionally add another process to the request queue
            if (response.equalsIgnoreCase("yes")) {
                System.out.println("Enter the process ID to add to the queue: ");
                int newProcessId = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character
                processQueue.add(newProcessId);
            }

            process.releaseCS();  // Release the critical section after use
            process.granted = false;  // Mark the process as no longer having access
            currentProcessInCS = null;  // Mark the critical section as free

            // If more processes are waiting, grant access to the next one
            if (!processQueue.isEmpty()) {
                grantAccess();
            }
        }
    }

    // Main method to run the Maekawa algorithm simulation
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();  // Read the number of processes

        MaekawaAlgorithm maekawaAlgorithm = new MaekawaAlgorithm(numProcesses);  // Initialize the algorithm

        // Input groups for each process
        for (int i = 1; i <= numProcesses; i++) {
            System.out.println("Enter group size for Process " + i + ": ");
            int groupSize = scanner.nextInt();
            Set<Integer> group = new HashSet<>();
            System.out.println("Enter group members for Process " + i + ": ");
            for (int j = 0; j < groupSize; j++) {
                group.add(scanner.nextInt());  // Add group members
            }
            maekawaAlgorithm.setGroup(i, group);  // Set the group for the process
        }

        // Simulate the request for the critical section
        System.out.println("Enter the process ID to simulate its request for the critical section: ");
        int processId = scanner.nextInt();
        maekawaAlgorithm.simulate(processId);  // Simulate the process

        scanner.close();  // Close the scanner
    }
}