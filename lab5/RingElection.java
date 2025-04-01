// Mann Patel - 22BCP107

import java.util.ArrayList;
import java.util.List;

// Class representing a process in the ring
class Process {
    int pid;           // Process ID
    boolean isLeader;  // Indicates if this process is the current leader
    boolean isAlive;   // Indicates if this process is alive

    // Constructor to initialize a process with a given pid
    public Process(int pid) {
        this.pid = pid;
        this.isLeader = false;
        this.isAlive = true;
    }

    // Simulate process failure
    public void fail() {
        this.isAlive = false;
        System.out.println("Process " + pid + " has failed.");
    }

    // Simulate process recovery
    public void recover() {
        this.isAlive = true;
        System.out.println("Process " + pid + " has recovered.");
    }
}

// Main class to implement the Ring Election Algorithm
public class RingElection {
    private List<Process> processes = new ArrayList<>();  // List of all processes
    private Process leader;  // Current leader process

    // Constructor to create the ring of processes and elect an initial leader
    public RingElection(int numProcesses) {
        // Add processes to the list based on the specified number of processes
        for (int i = 1; i <= numProcesses; i++) {
            processes.add(new Process(i));
        }
        // Elect the initial leader
        electLeader();
    }

    // Function to perform the leader election
    public void electLeader() {
        System.out.println("\nStarting election...");
        Process newLeader = null;  // Temporary variable to store the new leader
        // Iterate through all processes to find the highest alive process as the leader
        for (Process p : processes) {
            if (p.isAlive && (newLeader == null || p.pid > newLeader.pid)) {
                newLeader = p;  // The process with the highest pid is elected
            }
        }
        // If a new leader is found, update the leader status
        if (newLeader != null) {
            newLeader.isLeader = true;
            leader = newLeader;
            System.out.println("Process " + newLeader.pid + " is elected as the new leader.");
        }
    }

    // Function to check if the current leader is still alive
    public void checkLeader() {
        // If the leader is null or has failed, trigger a new election
        if (leader == null || !leader.isAlive) {
            System.out.println("Leader has failed! Starting a new election...");
            electLeader();
        } else {
            System.out.println("Process " + leader.pid + " is still the leader.");
        }
    }

    // Function to simulate the failure of a process
    public void failProcess(int pid) {
        // Find the process with the given pid and simulate its failure
        for (Process p : processes) {
            if (p.pid == pid) {
                p.fail();
                checkLeader();  // Check if the leader needs to be re-elected
                return;
            }
        }
    }

    // Function to simulate the recovery of a failed process
    public void recoverProcess(int pid) {
        // Find the process with the given pid and simulate its recovery
        for (Process p : processes) {
            if (p.pid == pid) {
                p.recover();
                // If the recovered process has a higher pid than the current leader, re-elect the leader
                if (p.pid > leader.pid) {
                    electLeader();
                }
                return;
            }
        }
    }

    // Main function to test the ring election algorithm
    public static void main(String[] args) {
        RingElection ringElection = new RingElection(5);  // Create a ring with 5 processes

        ringElection.checkLeader(); // Check and print the initial leader
        ringElection.failProcess(5); // Simulate the failure of the leader (Process 5)
        ringElection.recoverProcess(5); // Recover the failed process and re-elect it as leader if appropriate
    }
}