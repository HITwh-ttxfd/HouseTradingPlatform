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
    public House(String houseID,String sellerID,double size, double price, double locationX,double locationY) {
        this.houseID=houseID;
        this.sellerID=sellerID;
        this.size = size;
        this.price = price;
        this.locationX = locationX;
        this.locationY = locationY;
    }
    public House(){

    };

    public void setHouseID(String houseID) {
        this.houseID = houseID;
    }

    public String getHouseID() {
        return houseID;
    }


    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

}
