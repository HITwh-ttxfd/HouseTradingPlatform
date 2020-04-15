package connector;

import entity.House;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class HouseDBconnection {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/secondcompany?serverTimezone=UTC";
    String user = "root";
    String password = "Sunbz241290";
    Connection connection = null;
    public HouseDBconnection(){
        try {
            Class.forName(driver);
            connection=(Connection) DriverManager.getConnection(url,user,password);
            if(!connection.isClosed()){
                System.out.println("Successfully connected.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //增加房源
    public void addHouse(House house){
        if(house==null){
            System.out.println("com.company.Request null, can't insert.");
            return;
        }
        String sql="insert into house(houseID,sellerID,size,price,locationX,locationY) values('"+house.getHouseID()+"','"+house.getSellerID()
                +"','"+house.getSize()+"','"+house.getPrice()+"','"
                +house.getLocationX()+"','"+house.getLocationY()+"')";
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            //preparedStatement.setString(1,senderID);
            preparedStatement.executeUpdate();
            System.out.println("com.company.Request insert successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void deleteHouse(String houseID) {
        //查找house的seller拥有的house中ID为所给house的id的房子
        //sql删除操作
    }

    public List<House> getHouse() {
        //
    }
}
