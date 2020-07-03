package entity;

import java.util.Date;

public class Request {
    private String senderID;
    private String receiverID;
    private String houseID;
    private String phone;
    private String location;
    private String date;
    private String time;
    private String sendTime;
    private String status;
    // 0-等待审核 1-卖家拒绝 2-卖家同意 3-已完成 5
    public Request(){}

    public Request(String houseID, String senderID, String receiverID, String date, String time,String sendTime,String phone,String location,String status) {
        this.houseID = houseID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.date = date;
        this.time = time;
        this.sendTime = sendTime;
        this.phone = phone;
        this.location=location;
        this.status=status;
    }

    public Request(String houseID, String senderID, String receiverID, String date, String time,String sendTime,String phone,String location) {
        this.houseID = houseID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.date = date;
        this.time = time;
        this.sendTime = sendTime;
        this.phone = phone;
        this.location=location;
        this.status="0";
    }

    public Request(String houseID, String senderID, String receiverID, String date, String time,String phone,String location) {
        this.houseID = houseID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.date = date;
        this.time = time;
        this.sendTime=new Date().toString();
        this.phone=phone;
        this.location=location;
        this.status="0";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getLocation() {
        return location;
    }

    public String getPhone() {
        return phone;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
