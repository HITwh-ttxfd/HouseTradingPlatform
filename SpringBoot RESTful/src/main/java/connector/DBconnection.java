package connector;

import entity.*;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;

@Component
public class DBconnection extends DBConnector {

    //上传请求
    public void addRequests(Request request){
        if(request==null){
            System.out.println("com.company.Request null, can't insert.");
            return;
        }
        String senderID = request.getSenderID();
        String receiverID = request.getReceiverID();
        String houseID = request.getHouseID();
        String phone = request.getPhone();
        String location = request.getLocation();
        String date = request.getDate();
        String time = request.getTime();
        String sendTime = request.getSendTime();
        String sql="insert into requests(senderID,receiverID,houseID,phone,location,date,time,sendTime) " +
                "values('"+senderID+"','"+receiverID+"','"+houseID+"','"+phone+"','"+location+"','"+date+"','"+time+"','"+sendTime+"');";
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("com.company.Request insert successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //下传请求 返回列表
    public ArrayList<Request> selectRequests(User user){
        String recID = user.getPhone();
        String type = user.getType();
        String sql = "";
        if(type=="seller"){
            sql = "select * from requests where receiverID='"+recID+"'";
        }else if(type=="buyer"){
            sql = "select * from requests where senderID='"+recID+"'";
        }else{
            System.out.println("error, no user type.");
            return null;
        }
        ArrayList<Request> requests = new ArrayList<Request>();
        try {
            Statement statement = (Statement)this.connection.createStatement();
            ResultSet resultSet = (ResultSet)statement.executeQuery(sql);
            while (resultSet.next()){
                String senderID = resultSet.getString("senderID");
                String receiverID = resultSet.getString("receiverID");
                String houseID = resultSet.getString("houseID");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                String phone = resultSet.getString("phone");
                String location = resultSet.getString("location");
                String sendTime = resultSet.getString("sendTime");
                String status = resultSet.getString("status");
                requests.add(new Request(houseID,senderID,receiverID,date,time,sendTime,phone,location,status));
            }
            System.out.println("Return requests successfully.");
        }catch (Exception e){
            e.printStackTrace();
        }
        return requests;
    }
    //更改请求--未定义
    //查询请求
    public String selectRequest(Request request){
        String send = request.getSenderID();
        String receive = request.getReceiverID();
        String houseid = request.getHouseID();
        String sql = "select * from requests where senderID='"+send+"' and receiverID='"+receive+"' and houseID='"+houseid+"'";
        try {
            Statement statement = (Statement)this.connection.createStatement();
            ResultSet resultSet = (ResultSet)statement.executeQuery(sql);
            while (resultSet.next()){
                String senderID = resultSet.getString("senderID");
                String receiverID = resultSet.getString("receiverID");
                String houseID = resultSet.getString("houseID");
                if (send==senderID&&receive==receiverID&&houseid==houseID){
                    return "exist";
                }
            }
            System.out.println("Search requests successfully.");
        }catch (Exception e){
            e.printStackTrace();
        }
        return "new";
    }
    //更改状态
    public String changeRequestStatus(String senderID,String receiverID,String houseID, String choice){
        String sql = "update requests set status='"+choice+"' where senderID='"+senderID+"' and receiverID='"+receiverID+"' and houseID='"+houseID+"'";
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("com.company.Request request status changed successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "sucess";
    }
    //删除请求
    public String delRequests(Request request){
        String houseID = request.getHouseID();
        String senderID = request.getSenderID();
        String receiverID = request.getReceiverID();
        String date = request.getDate();
        String time = request.getTime();
        String sql = "delete from requests where senderID='"+senderID+"'\n" +
                "and receiverID='"+receiverID+"' and houseID='"+houseID+"' and date='"+date+"' and time='"+time+"';";
        try {
            PreparedStatement preparedStatement = (PreparedStatement)this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Delete successfully.");
            preparedStatement.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }

    //上传用户--新增用户
    public void addUser(User user){
        if(user==null){
            System.out.println("User data null.");
            return;
        }
        String username = user.getUsername();
        String password = user.getPassword();
        String realname = user.getRealname();
        String id = user.getId();
        String phone = user.getPhone();
        String type = user.getType();
        String sql = "insert into users(username,password,realname,id,phone,type) values('"+username+"','"
                +password+"','"+realname+"','"+id+"','"+phone+"','"+type+"')";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("User data insert successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //查询用户--登录校验--根据phone和password返回user对象
    public User selectUser(String ph, String pass){
        if(ph==null){
            System.out.println("error, ID is null.");
            return null;
        }
        String sql = "select * from users where phone='"+ph+"' and password='"+pass+"'";
        User user = new User();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            String username="", id="", realname="",password="",type="",phone="";
            while (resultSet.next()){
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                realname = resultSet.getString("realname");
                id = resultSet.getString("id");
                phone = resultSet.getString("phone");
                type = resultSet.getString("type");
            }
            //System.out.println(username+" read.");
            user = new User(username,password,realname,id,phone,type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    //查询用户--类中生成临时对象使用
    public User selectUser(String ID){
        if(ID==null){
            System.out.println("error, ID is null.");
            return null;
        }
        String sql = "select * from users where phone='"+ID+"'";
        User user = new User();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            String username="", id="", realname="",password="",type="",phone="";
            while (resultSet.next()){
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                realname = resultSet.getString("realname");
                id = resultSet.getString("id");
                phone = resultSet.getString("phone");
                type = resultSet.getString("type");
            }
            System.out.println("MySQL read "+phone);
            user = new User(username,password,realname,id,phone,type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

    //上传评价
    public void addComments(Comment comment){
        if(comment==null){
            return;
        }
        String authorID = comment.getAuthorID();
        String houseID = comment.getHouseID();
        String content = comment.getContent();
        String date = comment.getDate();
        String sql = "insert into comments(authorID,houseID,content,date) values('"+authorID+"','"+houseID+"','"+content+"','"+date+"')";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Comments add successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //下传评价至房源
    public ArrayList<Comment> selectComments(House house){
        String id = house.getHouseID();
        String sql = "select * from comments where houseID='"+id+"'";
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String authorID = resultSet.getString("authorID");
                String houseID = resultSet.getString("houseID");
                String content = resultSet.getString("content");
                String date = resultSet.getString("date");
                comments.add(new Comment(authorID,date,houseID,content));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }
    //下传评价至用户
    public ArrayList<Comment> selectComments(Buyer buyer){
        String id = buyer.getPhone();
        String sql = "select * from comments where authorID='"+id+"'";
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String authorID = resultSet.getString("authorID");
                String houseID = resultSet.getString("houseID");
                String content = resultSet.getString("content");
                String date = resultSet.getString("date");
                comments.add(new Comment(authorID,date,houseID,content));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }
    //删除评价
    public void delComments(Comment comment){
        String authorID = comment.getAuthorID();
        String houseID = comment.getHouseID();
        String content = comment.getContent();
        String date = comment.getDate();
        String sql = "delete from comments where authorID='"+authorID+"' and houseID='"+houseID+"' and content='"+content+"' and date='"+date+"'";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Delete comment successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //上传消息
    public void addMessages(Message message){
        if(message==null){
            return;
        }
        String content = message.getContent();
        String senderID = message.getSenderID();
        String receiverID = message.getReceiverID();
        String date = message.getDate();
        String sql = "insert into messages(content,senderID,receiverID,date) values('"+content+"','"+senderID+"','"+receiverID+"','"+date+"')";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("com.company.Message upload successfully.");
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //下传消息
    public ArrayList<Message> selectMessages(User user){
        if(user==null){
            System.out.println("error, no user.");
            return null;
        }
        String id = user.getPhone();
        String sql = "select * from messages where senderID='"+id+"' or receiverID='"+id+"'";
        ArrayList<Message> messages = new ArrayList();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String content = resultSet.getString("content");
                String senderID = resultSet.getString("senderID");
                String receiverID = resultSet.getString("receiverID");
                String date = resultSet.getString("date");
                messages.add(new Message(content,senderID,receiverID,date));
                System.out.println(messages.get(0).getReceiverID());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (messages.isEmpty()){
            System.out.println("Empty.");
        }
        return messages;
    }
    
    //加载驱动程序
    public DBconnection(){
        super();
    }

}
