//Mann Patel 22BCP107

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class DistributedSystem {
    // List to hold all the processes in the distributed system
    final List<Process> processes = new ArrayList<>();

    // Priority queue to manage requests for the critical section, ordered by timestamp
    private final PriorityQueue<Process> requestQueue = new PriorityQueue<>();

    // Method to add a process to the system
    public void addProcess(Process process) {
        processes.add(process);  // Add the process to the list of processes
    }

    // Method to request access to the critical section
    public void requestCS(Process process) {
        requestQueue.add(process);  // Add the process to the priority queue (sorted by timestamp)
    }

    // Method to process the queue, allowing processes to access the critical section in order
    public void processQueue() {
        // Loop through all processes in the queue
        while (!requestQueue.isEmpty()) {
            Process process = requestQueue.poll();  // Retrieve and remove the process with the highest priority (lowest timestamp)
            process.start();  // Start the process to enter the critical section
            try {
                process.join();  // Wait for the process to complete before allowing the next one
            } catch (InterruptedException e) {
                e.printStackTrace();  // Handle any interruptions during execution
            }
        }
    }

    // Callback method when a process completes its critical section
    public void processCompleted(Process process) {
        System.out.println("Process Completed");  // Notify that the process has completed its work
    }
}
