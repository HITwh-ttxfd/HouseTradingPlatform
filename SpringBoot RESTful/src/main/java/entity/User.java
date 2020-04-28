package entity;

import connector.UserDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class User {
    protected String username;
    protected String password;
    protected String realname;
    protected String id;
    protected String phone;
    protected String type;

    public Request sendRequest(House house, Seller sell,String date,String time,String phone,String location){
        String houseID = house.getHouseID();
        String sellerID = sell.getPhone();
        Calendar c = Calendar.getInstance();
        String d = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DATE)+" "
                +c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
        Request request = new Request(houseID,this.phone,sellerID,date,time,d,phone,location);
        return request;
    }
    public Message sendMessage(String content, User receiver){
        String receiverID = receiver.getPhone();
        String name = receiver.getUsername();
        Calendar c = Calendar.getInstance();
        String d = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DATE)+" "
                +c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
        Message message = new Message(name,this.phone,receiverID,content,d);
        return message;
    }
    public Comment sendComment(String content, House house,String score){
        String houseID = house.getHouseID();
        Calendar c = Calendar.getInstance();
        String d = c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DATE)+" "
                +c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
        Comment comment = new Comment(this.phone, d, houseID, content,score);
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

