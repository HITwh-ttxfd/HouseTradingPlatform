package entity;

public class Message {
    private String name;
    private String content;
    private String senderID;
    private String receiverID;
    private String date;
    private String read;

    public Message(String name, String senderID, String receiverID, String content, String date) {
        this.name = name;
        this.content = content;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.date = date;
        this.read = "false";
    }

    public Message(String name, String senderID, String receiverID, String content, String date, String read) {
        this.name = name;
        this.content = content;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.date = date;
        this.read = read;
    }

    public String getName() {
        return name;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public String getContent() {
        return content;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public String getDate() {
        return date;
    }
}