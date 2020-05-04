package entity;


import java.util.Date;

public class House {
    //小区介绍、户型介绍、装修描述、税费解析、核心卖点
    //房屋户型、建筑面积、套内面积、房屋朝向、装修情况、供暖方式、所在楼层、户型结构、建筑类型、梯户比例、配置电梯
    //挂牌时间、上次交易、房屋年限、抵押信息、交易权属、房屋用途、产权所属、房本配件
    //房子ID，卖家ID、评分、地理位置、价格
    private String houseID;     //主键1
    private String sellerID;

    //可筛选信息                                             筛选时
    private double size;                     //建筑面积      默认为0
    private String location;                 //地理位置      默认为all
    private String village;                  //小区
    private double price;                    //价格         默认max与min为0
    private float score;                     //评分         默认0               满分5分
    //    private Date  ListingTime;               //建成时间      默认all 筛time1和time2之间的ListingTime;                    //挂牌时间
    private Date  time;                      //建成时间      默认all 筛time1和time2之间的
    private int  lift;                       //配置电梯
    private double locationX;
    private double locationY;


    //一系列详细信息
//    private String CommunityIntroduction;   //小区介绍
//    private String houseTypeIntroduction;  //户型介绍
//    private String decorationDescription;  //装修描述
//    private String  taxAnalysis;          //税费解析
//    private String coreSellingPoints;     //核心卖点
    private int count;              //数量1
    private String HouseType;              //房屋户型1
    private double buildingArea;           //证上面积     1                这里可能存在疑问
    private double interiorArea;           //套内面积1
    private String houseOrientation;         //房屋朝向1
    private String decoration;               //装修情况1
    private String heatingMode;              //供暖方式1
    private String floor;                   //所在楼层1
    private String houseTypeStructure;      //户型结构1
    private String buildingType;            //建筑类型1
    private String elevatorProportion;       //梯户比例1
    private Date  lastTransaction;           //上次交易1
    private Date  listingTime;               //挂牌时间1
    private String  housingAge;                    //房屋年限1
    private String  mortgageInformation;            //抵押信息1
    private String  transactionOwnership;            //交易权属1
    private String  housingPurpose;                  //房屋用途1
    private String  propertyOwnership;               //产权所属1
    private String  housingParts;                    //房本配件1


    public House(){

    };
    //最为健全的house，用于向前端发送
    //29个参数

    public House(int count,
            String village,Date  time,
                 String houseID, String sellerID,
                 double size, double locationX,
                 double locationY, String location,
                 double price, float score,
                 int lift, Date lastTransaction,
                 String houseType, double buildingArea,
                 double interiorArea, String houseOrientation,
                 String decoration, String heatingMode,
                 String floor, String houseTypeStructure,
                 String buildingType, String elevatorProportion,
                 Date listingTime, String housingAge,
                 String mortgageInformation, String transactionOwnership,
                 String housingPurpose, String propertyOwnership,
                 String housingParts) {
        this.count=count;
        this.village=village;
        this.time=time;
        this.houseID = houseID;
        this.sellerID = sellerID;
        this.size = size;
        this.locationX = locationX;
        this.locationY = locationY;
        this.location = location;
        this.price = price;
        this.score = score;
        this.lift = lift;
        this.lastTransaction = lastTransaction;
        this.HouseType = houseType;
        this.buildingArea = buildingArea;
        this.interiorArea = interiorArea;
        this.houseOrientation = houseOrientation;
        this.decoration = decoration;
        this.heatingMode = heatingMode;
        this.floor = floor;
        this.houseTypeStructure = houseTypeStructure;
        this.buildingType = buildingType;
        this.elevatorProportion = elevatorProportion;
        this.listingTime = listingTime;
        this.housingAge = housingAge;
        this.mortgageInformation = mortgageInformation;
        this.transactionOwnership = transactionOwnership;
        this.housingPurpose = housingPurpose;
        this.propertyOwnership = propertyOwnership;
        this.housingParts = housingParts;
    }


    //去掉了houseid与score   默认score=0
    //用于接收前端传过来的房源

//    public House(String sellerID, double size,
//                 double locationX, double locationY,
//                 String location, double price,
//                 int lift, Date lastTransaction,
//                 String houseType, double buildingArea,
//                 double interiorArea, String houseOrientation,
//                 String decoration, String heatingMode,
//                 String floor, String houseTypeStructure,
//                 String buildingType, String elevatorProportion,
//                 Date listingTime, String housingAge,
//                 String mortgageInformation, String transactionOwnership,
//                 String housingPurpose, String propertyOwnership,
//                 String housingParts) {
//        this.sellerID = sellerID;
//        this.size = size;
//        this.locationX = locationX;
//        this.locationY = locationY;
//        this.location = location;
//        this.price = price;
//        this.score = 0;
//        this.lift = lift;
//        this.lastTransaction = lastTransaction;
//        HouseType = houseType;
//        this.buildingArea = buildingArea;
//        this.interiorArea = interiorArea;
//        this.houseOrientation = houseOrientation;
//        this.decoration = decoration;
//        this.heatingMode = heatingMode;
//        this.floor = floor;
//        this.houseTypeStructure = houseTypeStructure;
//        this.buildingType = buildingType;
//        this.elevatorProportion = elevatorProportion;
//        ListingTime = listingTime;
//        this.housingAge = housingAge;
//        this.mortgageInformation = mortgageInformation;
//        this.transactionOwnership = transactionOwnership;
//        this.housingPurpose = housingPurpose;
//        this.propertyOwnership = propertyOwnership;
//        this.housingParts = housingParts;
//    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }


    public int getLift() {
        return lift;
    }

    public void setLift(int lift) {
        this.lift = lift;
    }

    public String getHouseType() {
        return HouseType;
    }

    public void setHouseType(String houseType) {
        HouseType = houseType;
    }

    public double getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(double buildingArea) {
        this.buildingArea = buildingArea;
    }

    public double getInteriorArea() {
        return interiorArea;
    }

    public void setInteriorArea(double interiorArea) {
        this.interiorArea = interiorArea;
    }

    public String getHouseOrientation() {
        return houseOrientation;
    }

    public void setHouseOrientation(String houseOrientation) {
        this.houseOrientation = houseOrientation;
    }

    public String getDecoration() {
        return decoration;
    }

    public void setDecoration(String decoration) {
        this.decoration = decoration;
    }

    public String getHeatingMode() {
        return heatingMode;
    }

    public void setHeatingMode(String heatingMode) {
        this.heatingMode = heatingMode;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getHouseTypeStructure() {
        return houseTypeStructure;
    }

    public void setHouseTypeStructure(String houseTypeStructure) {
        this.houseTypeStructure = houseTypeStructure;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public String getElevatorProportion() {
        return elevatorProportion;
    }

    public void setElevatorProportion(String elevatorProportion) {
        this.elevatorProportion = elevatorProportion;
    }

    public Date getListingTime() {
        return listingTime;
    }

    public void setListingTime(Date listingTime) {
        this.listingTime = listingTime;
    }

    public Date getLastTransaction() {
        return lastTransaction;
    }

    public void setLastTransaction(Date lastTransaction) {
        this.lastTransaction = lastTransaction;
    }

    public String getHousingAge() {
        return housingAge;
    }

    public void setHousingAge(String housingAge) {
        this.housingAge = housingAge;
    }

    public String getMortgageInformation() {
        return mortgageInformation;
    }

    public void setMortgageInformation(String mortgageInformation) {
        this.mortgageInformation = mortgageInformation;
    }

    public String getTransactionOwnership() {
        return transactionOwnership;
    }

    public void setTransactionOwnership(String transactionOwnership) {
        this.transactionOwnership = transactionOwnership;
    }

    public String getHousingPurpose() {
        return housingPurpose;
    }

    public void setHousingPurpose(String housingPurpose) {
        this.housingPurpose = housingPurpose;
    }

    public String getPropertyOwnership() {
        return propertyOwnership;
    }

    public void setPropertyOwnership(String propertyOwnership) {
        this.propertyOwnership = propertyOwnership;
    }

    public String getHousingParts() {
        return housingParts;
    }

    public void setHousingParts(String housingParts) {
        this.housingParts = housingParts;
    }
}
