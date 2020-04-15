package entity;

public class Buyer extends User {
    public Buyer(){}

    public Buyer(String username,String password,String realname,String id,String phone){
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.id = id;
        this.phone = phone;
        this.type = "buyer";
    }
}

