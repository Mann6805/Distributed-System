//Mann Patel 22BCP107

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        DistributedSystem distributedSystem = new DistributedSystem();

        // Create and add processes to the distributed system
        for (int i = 0; i < numProcesses; i++) {
            Process process = new Process(i, distributedSystem);
            distributedSystem.addProcess(process);
        }

        // Accept process IDs and timestamps for critical section requests
        System.out.println("Enter processes and their timestamps (e.g., '1 0' for Process 1 with timestamp 0). Enter 'done' when finished:");
        scanner.nextLine(); // Consume newline

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            String[] parts = input.split(" ");
            int processId = Integer.parseInt(parts[0]);
            int timestamp = Integer.parseInt(parts[1]);

            // Handle each critical section request as a new process instance
            Process process = new Process(processId, distributedSystem);
            process.setTimestamp(timestamp);
            distributedSystem.requestCS(process);
        }

        // Process requests in the queue based on timestamps
        distributedSystem.processQueue();
        scanner.close();
    }
}
