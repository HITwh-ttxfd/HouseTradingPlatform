package entity;

public class Request {
    private String houseID;
    private String date;
    private String senderID;
    private String receiverID;

    public Request(){}

    public Request(String houseID, String senderID, String receiverID, String date) {
        this.houseID = houseID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.date = date;
    }

    public void setHouse(String houseID) {
        this.houseID = houseID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String  getHouseID() {
        return houseID;
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

    public void setDate(String date) {
        this.date = date;
    }
}
