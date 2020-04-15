package entity;

public class Message {
    private String content;
    private String senderID;
    private String receiverID;
    private String date;

    public Message(String content, String senderID, String receiverID,String date) {
        this.content = content;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.date = date;
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