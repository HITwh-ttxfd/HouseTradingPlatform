package connector;

import entity.House;
import org.springframework.web.bind.annotation.PathVariable;

import java.sql.*;
import java.util.ArrayList;


public class HouseDBconnection extends DBConnector{
    //增加房源
    public void addHouse(House house){
        if(house==null){
            System.out.println("House is null, can't insert.");
            return;
        }
        //加上一个查询先有houseID最大值的，给houseID赋值
        house.setHouseID(selectMaxHouseID());
        String sql="insert into house(houseID,sellerID,size,price,locationX,locationY) values('"+house.getHouseID()+"','"+house.getSellerID()
                +"','"+house.getSize()+"','"+house.getPrice()+"','"
                +house.getLocationX()+"','"+house.getLocationY()+"')";
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("insert successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
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
    //   删除房源
    public void deleteHouse(String houseID) {
        String sql = "delete from house where houseID="+houseID;
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Delete successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    // 查找某一房源
    public House getHouse(String houseID) {
        String sql = "select * from house where houseID="+houseID;
        House house=new House();
        try {
            Statement statement = (Statement)this.connection.createStatement();
            ResultSet resultSet = (ResultSet)statement.executeQuery(sql);
            while (resultSet.next()){
                house.setHouseID(resultSet.getString("houseID"));
                house.setSellerID(resultSet.getString("sellerID"));
                house.setPrice(resultSet.getDouble("price"));
                house.setSize(resultSet.getDouble("size"));
                house.setLocationX(resultSet.getDouble("locationX"));
                house.setLocationX(resultSet.getDouble("locationY"));

                System.out.println(resultSet.getString("houseID")+
                        resultSet.getString("sellerID")+
                        resultSet.getDouble("price")+
                        resultSet.getDouble("size")+
                        resultSet.getDouble("locationX")+
                        resultSet.getDouble("locationY"));
            }
            System.out.println("get successfully.");
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
                String houseID = resultSet.getString("houseID");
                String sellerID = resultSet.getString("sellerID");
                double size = resultSet.getDouble("size");
                double price = resultSet.getDouble("price");
                double locationX = resultSet.getDouble("locationX");
                double locationY = resultSet.getDouble("locationY");
                houses.add(new House(houseID,sellerID,size,price,locationX,locationY));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return houses;
    }
    //修改房源价格
    public void changeHousePrice(String houseID,double price){
        String sql="UPDATE house SET price = "+price+"WHERE houseID = "+houseID;
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("change successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public HouseDBconnection(){
        super();
    }
}
