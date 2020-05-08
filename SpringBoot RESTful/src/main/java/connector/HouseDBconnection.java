package connector;


import entity.House;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class HouseDBconnection{
    //上传项目专用
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://39.98.48.34:3306/htpbase?serverTimezone=UTC";
    String user = "root";
    String password = "2296026";
    Connection connection = null;


    //构造函数
    public HouseDBconnection(){
        try {
            Class.forName(driver);
            connection=(Connection) DriverManager.getConnection(url,user,password);
            if(!connection.isClosed()){
                System.out.println("Successfully connected HouseDataBase.");
            }
        }catch (Exception e){
            System.out.println("failed to connect HouseDataBase.");
            e.printStackTrace();
        }
    }

    //增加房源
    public void addHouse(House house){
        if(house==null){
            System.out.println("House is null, can't insert.");
            return;
        }
        //设置时间日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //加上一个查询先有houseID最大值的，给houseID赋值
        house.setHouseID(selectMaxHouseID());
        System.out.println("ID is "+selectMaxHouseID());
        System.out.println(house.getHouseID());
        //设置house的username
        house.setUsername(selectUsername(house.getSellerID()));

        String sql="insert into house(username,count,village,time,houseID,sellerID, size, locationX, locationY, location, price,score, lift, lastTransaction, houseType, buildingArea, interiorArea, houseOrientation, decoration, heatingMode, floor, houseTypeStructure, buildingType, elevatorProportion, listingTime, housingAge, mortgageInformation, transactionOwnership, housingPurpose, propertyOwnership, housingParts)" +
                " values('"+house.getUsername()+"',"
                +house.getCount()+",'"
                +house.getVillage()+"','"+sdf.format(house.getTime())+"','"
                +house.getHouseID()+"','"+house.getSellerID()+"',"
                +house.getSize()+","+house.getLocationX()
                +","+house.getLocationY()+",'"+house.getLocation()+"',"
                +house.getPrice()+","+house.getScore()+","
                +house.getLift()+",'"+sdf.format(house.getLastTransaction())+"','"
                +house.getHouseType()+"',"+house.getBuildingArea()+","
                +house.getInteriorArea()+",'"+house.getHouseOrientation()+"','"
                +house.getDecoration()+"','"+house.getHeatingMode()+"','"
                +house.getFloor()+"','"+house.getHouseTypeStructure()+"','"
                +house.getBuildingType()+"','"+house.getElevatorProportion()+"','"
                +sdf.format(house.getListingTime())+"',"+house.getHousingAge()+",'"
                +house.getMortgageInformation()+"','"+house.getTransactionOwnership()+"','"
                +house.getHousingPurpose()+"','"+house.getPropertyOwnership()+"','"
                +house.getHousingParts()+"')";
        System.out.println(sql);
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //查询用户名--添加房源--根据sellerID（也就是phone）返回username
    public String selectUsername(String sellerID){
        String sql = "select username from users where phone='"+sellerID+"'";
        String username="";
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                username = resultSet.getString("username");
            }
            statement.close();
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return username;
    }

    //查询HouseId最大值
    public String selectMaxHouseID(){
        String sql = "select max(houseID) from house ";
        String temp="";
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                temp=resultSet.getString("max(houseID)");
            }
            temp=String.valueOf(Integer.parseInt(temp)+1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return temp;
    }

    // 删除房源
    public void deleteHouse(String houseID) {
        String sql = "delete from house where houseID='"+houseID+"'";
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 查找某一房源详细信息    第二次点击
    public House getHouse(String houseID) {
        String sql = "select * from house where houseID='"+houseID+"'";
        House house=new House();
        try {
            Statement statement = (Statement)this.connection.createStatement();
            ResultSet resultSet = (ResultSet)statement.executeQuery(sql);
            while (resultSet.next()){
                int count=resultSet.getInt("count");
                String village = resultSet.getString("village");
                Date time = resultSet.getDate("time");
                String sellerID = resultSet.getString("sellerID");
                double size = resultSet.getDouble("size");
                double locationX = resultSet.getDouble("locationX");
                double locationY = resultSet.getDouble("locationY");
                String location = resultSet.getString("location");
                double price = resultSet.getDouble("price");
                float score = resultSet.getFloat("score");
                int lift = resultSet.getInt("lift");
                String houseType = resultSet.getString("houseType");
                double buildingArea = resultSet.getDouble("buildingArea");
                double interiorArea = resultSet.getDouble("interiorArea");
                String houseOrientation = resultSet.getString("houseOrientation");
                String decoration = resultSet.getString("decoration");
                String heatingMode = resultSet.getString("heatingMode");
                String floor = resultSet.getString("floor");
                String houseTypeStructure = resultSet.getString("houseTypeStructure");
                String buildingType = resultSet.getString("buildingType");
                String elevatorProportion = resultSet.getString("elevatorProportion");
                Date listingTime = resultSet.getDate("listingTime");
                Date lastTransaction = resultSet.getDate("lastTransaction");
                String housingAge = resultSet.getString("housingAge");
                String mortgageInformation = resultSet.getString("mortgageInformation");
                String transactionOwnership = resultSet.getString("transactionOwnership");
                String housingPurpose = resultSet.getString("housingPurpose");
                String propertyOwnership = resultSet.getString("propertyOwnership");
                String housingParts = resultSet.getString("housingParts");
                String username = resultSet.getString("username");
                House housetemp=new House(username,count,village,time,
                        houseID, sellerID,
                        size,locationX,
                        locationY,location,
                        price,score,
                        lift,lastTransaction,
                        houseType,buildingArea,
                        interiorArea,houseOrientation,
                        decoration,heatingMode,
                        floor, houseTypeStructure,
                        buildingType, elevatorProportion,
                        listingTime, housingAge,
                        mortgageInformation,transactionOwnership,
                        housingPurpose,propertyOwnership,
                        housingParts);
                house=housetemp;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return house;
    }

    //返回所有house
    public  ArrayList<House> getHouses() {
        String sql = "select * from house ";
        ArrayList<House> houses = new ArrayList<House>();
        try {
            Statement statement = (Statement)this.connection.createStatement();
            ResultSet resultSet = (ResultSet)statement.executeQuery(sql);
            while (resultSet.next()){
                int count=resultSet.getInt("count");
                String village = resultSet.getString("village");
                Date time = resultSet.getDate("time");
                String houseID = resultSet.getString("houseID");
                String sellerID = resultSet.getString("sellerID");
                double size = resultSet.getDouble("size");
                double locationX = resultSet.getDouble("locationX");
                double locationY = resultSet.getDouble("locationY");
                String location = resultSet.getString("location");
                double price = resultSet.getDouble("price");
                float score = resultSet.getFloat("score");
                int lift = resultSet.getInt("lift");
                String houseType = resultSet.getString("houseType");
                double buildingArea = resultSet.getDouble("buildingArea");
                double interiorArea = resultSet.getDouble("interiorArea");
                String houseOrientation = resultSet.getString("houseOrientation");
                String decoration = resultSet.getString("decoration");
                String heatingMode = resultSet.getString("heatingMode");
                String floor = resultSet.getString("floor");
                String houseTypeStructure = resultSet.getString("houseTypeStructure");
                String buildingType = resultSet.getString("buildingType");
                String elevatorProportion = resultSet.getString("elevatorProportion");
                Date listingTime = resultSet.getDate("listingTime");
                Date lastTransaction = resultSet.getDate("lastTransaction");
                String housingAge = resultSet.getString("housingAge");
                String mortgageInformation = resultSet.getString("mortgageInformation");
                String transactionOwnership = resultSet.getString("transactionOwnership");
                String housingPurpose = resultSet.getString("housingPurpose");
                String propertyOwnership = resultSet.getString("propertyOwnership");
                String housingParts = resultSet.getString("housingParts");
                String username = resultSet.getString("username");
                House house=new House(username,count,village,time,
                        houseID, sellerID,
                        size,locationX,
                        locationY,location,
                        price,score,
                        lift,lastTransaction,
                        houseType,buildingArea,
                        interiorArea,houseOrientation,
                        decoration,heatingMode,
                        floor, houseTypeStructure,
                        buildingType, elevatorProportion,
                        listingTime, housingAge,
                        mortgageInformation,transactionOwnership,
                        housingPurpose,propertyOwnership,
                        housingParts);
                houses.add(house);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return houses;
    }

    //返回卖家房源
    public ArrayList<House> getSellerHouse(String sellerID) {
        String sql = "select * from house where sellerID='"+sellerID+"'";
        ArrayList<House> houses = new ArrayList<House>();
        try {
            Statement statement = (Statement)this.connection.createStatement();
            ResultSet resultSet = (ResultSet)statement.executeQuery(sql);
            while (resultSet.next()){
                House house=new House();
                house.setHouseID(resultSet.getString("houseID"));
                house.setSellerID(resultSet.getString("sellerID"));
                house.setScore(resultSet.getFloat("score"));
                house.setPrice(resultSet.getDouble("price"));
                house.setSize(resultSet.getDouble("size"));
                house.setLocation(resultSet.getString("location"));
                house.setVillage(resultSet.getString("village"));
                house.setCount(resultSet.getInt("count"));
                house.setUsername(resultSet.getString("username"));
                houses.add(house);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return houses;
    }

    //返回基本房源   第一次点击
    public ArrayList<House> getBasicHouses() {
        String sql = "select * from house ";
        ArrayList<House> houses = new ArrayList<House>();
        try {
            Statement statement = (Statement)this.connection.createStatement();
            ResultSet resultSet = (ResultSet)statement.executeQuery(sql);
            while (resultSet.next()){
                House house=new House();
                house.setHouseID(resultSet.getString("houseID"));
                house.setSellerID(resultSet.getString("sellerID"));
                house.setScore(resultSet.getFloat("score"));
                house.setPrice(resultSet.getDouble("price"));
                house.setSize(resultSet.getDouble("size"));
                house.setLocation(resultSet.getString("location"));
                house.setVillage(resultSet.getString("village"));
                house.setCount(resultSet.getInt("count"));
                house.setUsername(resultSet.getString("username"));
                //测试用，正式上线删
                houses.add(house);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return houses;
    }

    //修改房源价格（房子也就价格可以修改了）
    public void changeHousePrice(String houseID, double price){
        String sql = "UPDATE house SET price = "+price+" WHERE houseID ='"+houseID+"'";
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //修改房源评价分数和评价数量
    public void changeHouseScore(float score, String houseID,int count){
        String sql = "UPDATE house SET score = "+score+", count="+count+" WHERE houseID ='"+houseID+"'";
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //获得房源评价分数
    public float getHouseScore(String houseID){
        String sql = "select score from house where houseID='"+houseID+"'";
        float temp=0;
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                temp=resultSet.getFloat("score");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return temp;
    }

    //修改房源评价分数  用于发生评价并且更新评价后
    public void changeScore(String houseID)
    {
        int count=DBconnection.countComments(houseID);
        float score=DBconnection.sumComments(houseID);
        float finalScore=score/(float)count;
        changeHouseScore(finalScore,houseID,count);
    }

    //搜索房源
    public ArrayList<House> selectBasicHouse(String position,String village, double mimSize, double maxSize, double minPrice,double maxPrice, float score, int time) throws ParseException {
        ArrayList<House> houses = new ArrayList<House>();
        String sql="select * from house where";
        if(position!=""){
            sql+=" location LIKE '"+position+"%' and ";
        }
        if(village!="")
            sql+=" village = '"+village+"' and ";
        sql+=" size >="+mimSize;
        sql+=" and size <="+maxSize;
        sql+=" and price >="+minPrice;
        sql+=" and price <="+maxPrice;
        sql+=" and score >="+score;

        //将现在的时间减去给的时间
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String temp= formatter.format(date);
        String str1=temp.substring(0, temp.indexOf("-"));
        String str2=temp.substring(str1.length()+1, temp.length());
        int m=Integer.valueOf(str1.toString());
        m-=time;
        str1 = String.valueOf(m);
        temp=str1+'-'+str2;


        sql+=" and time >='"+temp+"'";
        try {
            Statement statement = (Statement)this.connection.createStatement();
            ResultSet resultSet = (ResultSet)statement.executeQuery(sql);
            while (resultSet.next()){
                House house=new House();
                house.setHouseID(resultSet.getString("houseID"));
                house.setSellerID(resultSet.getString("sellerID"));
                house.setScore(resultSet.getFloat("score"));
                house.setPrice(resultSet.getDouble("price"));
                house.setSize(resultSet.getDouble("size"));
                house.setLocation(resultSet.getString("location"));
                house.setVillage(resultSet.getString("village"));
                houses.add(house);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return houses;
    }

}
