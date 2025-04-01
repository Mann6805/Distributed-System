// Mann Patel - 22BCP107

public class Event {

    // Event type (e.g., message, notification)
    public int type;

    // Sender's ID
    public long senderId;

    // Receiver's ID
    public long receiverId;

    // Local timestamp (default 0)
    public int localTime;

    // Event content
    public String content;

    // Constructor with default localTime
    public Event(int type, long senderId, long receiverId, String content) {
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.localTime = 0;
    }

    // Constructor with specified localTime
    public Event(int type, long senderId, long receiverId, int localTime, String content) {
        this(type, senderId, receiverId, content);
        this.localTime = localTime;
    }
}
