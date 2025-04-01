// Mann Patel - 22BCP107

public class LamportClock extends Thread {

    // Local time of the process
    private int time;

    // Order of the process (as seen from the master)
    private int order;

    // Constructor to initialize the process order and set local time to 0
    public LamportClock(int order) {
        this.time = 0;
        this.order = order;
    }

    // Get the order of the process
    public int getOrder() {
        return this.order;
    }

    // Get the current local time
    public int getTime() {
        return this.time;
    }

    // Handle a local event by incrementing the local time
    public int localEvent() {
        ++this.time;
        return this.time;
    }

    // Handle a send event by incrementing the local time
    public int sendEvent() {
        ++this.time;
        return this.time;
    }

    // Handle a receive event by updating the local time based on received time
    public int receiveEvent(int receivedTime) {
        this.time = Math.max(receivedTime, this.time) + 1;
        return this.time;
    }

    // Update the local time based on the event type
    public void updateTime(Event e) {
        switch (e.type) {
            case 0: // Local event
                this.localEvent();
                break;
            case 1: // Send event
                this.sendEvent();
                break;
            case 2: // Receive event
                this.receiveEvent(e.localTime);
                break;
            default:
                break;
        }

        printTime(e);
    }

    // Print the current time and event details
    public void printTime(Event e) {
        String logging = "-------------------------\n";
        logging += "Process " + this.getOrder() + " local time " + this.getTime() + "\n";
        logging += "\tEvent type: ";

        switch (e.type) {
            case 0:
                logging += "LOCAL EVENT\n";
                break;
            case 1:
                logging += "SEND EVENT\n";
                break;
            case 2:
                logging += "RECEIVE EVENT\n";
                break;
            default:
                break;
        }

        // Uncomment if sender and receiver IDs are needed
        // logging += "\tEvent sender's ID: " + e.senderId + "\n";
        // logging += "\tEvent receiver's ID: " + e.receiverId + "\n";
        
        logging += "\tEvent local time: " + e.localTime + "\n";
        logging += "\tEvent content: " + e.content + "\n";
        logging += "-------------------------\n";

        System.out.print(logging);
    }

    // Run method to initialize the process
    public void run() {
        String greeting = "Process " + this.order + " with Unique ID " + this.threadId() +
            " is initialized with local clock " + this.time;
        System.out.println(greeting);
    }
}