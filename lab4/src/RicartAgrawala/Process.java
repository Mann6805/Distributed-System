//Mann Patel 22BCP107

public class Process extends Thread implements Comparable<Process> {
    private final int id;  // Unique ID for each process
    private final DistributedSystem distributedSystem;  // Reference to the distributed system
    private int timestamp;  // Timestamp of the process's request for the critical section

    // Constructor to initialize the process with an ID and the distributed system reference
    public Process(int id, DistributedSystem distributedSystem) {
        this.id = id;
        this.distributedSystem = distributedSystem;
    }

    // Setter method for updating the timestamp
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    // Getter method to retrieve the current timestamp
    public int getTimestamp() {
        return timestamp;
    }

    // Getter method to return the process ID
    public long getId() {
        return id;
    }

    // Compare processes based on their timestamp first, and by their ID if timestamps are equal
    @Override
    public int compareTo(Process other) {
        if (this.timestamp != other.timestamp) {
            return Integer.compare(this.timestamp, other.timestamp);
        } else {
            return Integer.compare(this.id, other.id);
        }
    }

    // The main logic of the process when it starts running (requesting and releasing the critical section)
    public void run() {
        enterCriticalSection();  // Enter the critical section
        try {
            // Simulate some work inside the critical section by sleeping for a random duration
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        releaseCriticalSection();  // Release the critical section once done
    }

    // Synchronized method to safely enter the critical section
    public synchronized void enterCriticalSection() {
        System.out.println("Process " + id + " is entering the critical section with timestamp " + timestamp);
    }

    // Synchronized method to safely exit the critical section and notify the system
    public synchronized void releaseCriticalSection() {
        System.out.println("Process " + id + " has exited the critical section.");
        distributedSystem.processCompleted(this);  // Notify the distributed system that the process is done
    }
}