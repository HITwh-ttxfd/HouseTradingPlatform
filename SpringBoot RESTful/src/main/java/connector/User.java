package connector;

import entity.*;

import java.util.Date;

public class User {
    protected String username;
    protected String password;
    protected String realname;
    protected String id;
    protected String phone;
    protected String type;

    public Request sendRequest(House house, Seller sell){
        String houseID = house.getHouseID();
        String sellerID = sell.getPhone();
        Request request = new Request(houseID,new Date().toString(),this.phone,sellerID);
        return request;
    }
    public Message sendMessage(String content, connector.User receiver){
        String receiverID = receiver.getPhone();
        Message message = new Message(content,this.phone,receiverID,new Date().toString());
        return message;
    }
    public Comment sendComment(String content, House house){
        String houseID = house.getHouseID();
        Comment comment = new Comment(this.phone, new Date().toString(), houseID, content);
        return comment;
    }

    public User(){}

    public User(String username, String password, String realname, String id, String phone){
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.id = id;
        this.phone = phone;
        this.type = null;
    }

    public User(String username, String password, String realname, String id, String phone, String type){
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.id = id;
        this.phone = phone;
        this.type = type;
    }


    //getter&&setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

