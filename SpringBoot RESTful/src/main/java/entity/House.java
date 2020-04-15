package entity;


import java.util.Random;

public class House {
    private String houseID;
    private String sellerID;
    private double size;
    private double price;
    private String locationX;
    private String locationY;

    private String HashID(String ID){
        Random i = new Random();
        int x = i.nextInt(100000) % 999;
        String result = String.format("%04d",x);
        return result;
    }// 随机生成houseID

    public House(){}

    public House(String houseID, String sellerID,double size, double price, String locationX, String locationY) {
        this.houseID = houseID;
        this.sellerID=sellerID;
        this.size = size;
        this.price = price;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getLocationX() {
        return locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public static void addHouse(House house) {

    }

    public static void deleteHouse(House house) {

    }

}
