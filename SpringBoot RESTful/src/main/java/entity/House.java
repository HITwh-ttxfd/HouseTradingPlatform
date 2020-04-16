package entity;


public class House {
    private String houseID;
    private String sellerID;
    private double size;
    private double price;
    private double locationX;
    private double locationY;

    public House(String sellerID,double size, double price, double locationX,double locationY) {
        this.sellerID=sellerID;
        this.size = size;
        this.price = price;
        this.locationX = locationX;
        this.locationY = locationY;
    }

    public House() {

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

    public String getHouseID() {
        return houseID;
    }

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public double getLocationX() {
        return locationX;
    }

    public double getLocationY() {
        return locationY;
    }
}
