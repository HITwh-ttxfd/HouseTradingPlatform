package connector;

import entity.*;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DBconnection{

    // 上传图片
    public static String uploadImg(String path, String houseID,String fileName,String style){
        if(path==null || houseID==null){
            System.out.println("Image is null. Error.");
            return "null";
        }
        String sql = "insert into house_pic(houseID,fileName,style,path) values('"+houseID+"','"+fileName+"','"
                +style+"','"+path+"');";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Image insert successfully.");
            preparedStatement.close();
            connection.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    // 返回图片列表
    public static ArrayList<housePic> selectImg(String houseID){
        ArrayList<housePic> bases = new ArrayList<>();
        String sql = "select * from house_pic where houseID='"+houseID+"';";
        try {
            Connection connection = jdbcUtils.getConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String houseid= resultSet.getString("houseID");
                String fileName = resultSet.getString("fileName");
                String style = resultSet.getString("style");
                String path = resultSet.getString("path");
                bases.add(new housePic(houseid,fileName,style,path));
            }
            //System.out.println("Img return successfully");
            statement.close();
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bases;
    }
    // 删除图片
    public static String delImg(String houseID,String fileName){
        String sql = "delete from house_pic where houseID='"+houseID+"' and fileName='"+fileName+"';";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Image delete successfully.");
            preparedStatement.close();
            connection.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }

    //上传请求
    public static String addRequests(Request request){
        if(request==null){
            System.out.println("com.company.Request null, can't insert.");
            return "null";
        }
        String senderID = request.getSenderID();
        String receiverID = request.getReceiverID();
        String houseID = request.getHouseID();
        String phone = request.getPhone();
        String location = request.getLocation();
        String date = request.getDate();
        String time = request.getTime();
        String sendTime = request.getSendTime();
        String status = request.getStatus();
        //System.out.println(date+" "+time);
        String sql="insert into requests(senderID,receiverID,houseID,phone,location,date,time,sendTime,status) " +
                "values('"+senderID+"','"+receiverID+"','"+houseID+"','"+phone+"','"+location+"','"+date+"','"+time+"','"+sendTime+"','"+status+"');";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Request insert successfully.");
            preparedStatement.close();
            connection.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    //下传请求 返回列表
    public static ArrayList<Request> selectRequests(User user){
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
            Connection connection = jdbcUtils.getConnect();
            //System.out.println(connection);
            Statement statement = (Statement)connection.createStatement();
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
            statement.close();
            resultSet.close();
            connection.close();
            //System.out.println("Return requests successfully.");
        }catch (Exception e){
            e.printStackTrace();
        }
        Collections.sort(requests,new sortReq());
        return requests;
    }
    //更改请求--未定义
    //查询请求
    public static String selectRequest(Request request){
        String send = request.getSenderID();
        String receive = request.getReceiverID();
        String houseid = request.getHouseID();
        String status = request.getStatus();
        String sql = "select * from requests where senderID='"+send+"' and receiverID='"+receive+"' and houseID='"
                +houseid+"';";
        try {
            Connection connection = jdbcUtils.getConnect();
            Statement statement = (Statement)connection.createStatement();
            ResultSet resultSet = (ResultSet)statement.executeQuery(sql);
            while (resultSet.next()){
                String senderID = resultSet.getString("senderID");
                String receiverID = resultSet.getString("receiverID");
                String houseID = resultSet.getString("houseID");
                String sta = resultSet.getString("status");
                //System.out.println(senderID+" "+send);
                //System.out.println(receiverID+" "+receive);
                //System.out.println(houseID+" "+houseid);
                if (send.equals(senderID) && receive.equals(receiverID) && houseid.equals(houseID) && sta.equals(status)){
                    return "exist";
                }if (send.equals(senderID) && receive.equals(receiverID) && houseid.equals(houseID) && sta.equals("2")){
                    return "exist";
                }
            }
            statement.close();
            resultSet.close();
            connection.close();
            //System.out.println("Search requests successfully.");
        }catch (Exception e){
            e.printStackTrace();
            return "exist";
        }
        return "new";
    }
    //更改状态
    public static String changeRequestStatus(String senderID,String receiverID,String houseID, String choice){
        String sql = "update requests set status='"+choice+"' where senderID='"+senderID+"' and receiverID='"+receiverID+"' and houseID='"+houseID+"'";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Request status changed successfully.");
            preparedStatement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "success";
    }
    //删除请求
    public static String delRequests(Request request){
        String houseID = request.getHouseID();
        String senderID = request.getSenderID();
        String receiverID = request.getReceiverID();
        String date = request.getDate();
        String time = request.getTime();
        String sql = "delete from requests where senderID='"+senderID+"'\n" +
                "and receiverID='"+receiverID+"' and houseID='"+houseID+"' and date='"+date+"' and time='"+time+"';";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Delete request successfully.");
            preparedStatement.close();
            connection.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
    }


    //查询用户--登录校验--根据phone和password返回user对象
    public static User selectUser(String ph, String pass){
        if(ph==null){
            System.out.println("error, ID is null.");
            return null;
        }
        String sql = "select * from users where phone='"+ph+"' and password='"+pass+"'";
        User user = new User();
        try {
            Connection connection = jdbcUtils.getConnect();
            Statement statement = connection.createStatement();
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
            //System.out.println(phone+" read.");
            statement.close();
            resultSet.close();
            connection.close();
            user = new User(username,password,realname,id,phone,type);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
    //查询用户--类中生成临时对象使用
    public static User selectUser(String ID){
        if(ID==null){
            System.out.println("error, ID is null.");
            return null;
        }
        String sql = "select * from users where phone='"+ID+"'";
        User user = new User();
        try {
            Connection connection = jdbcUtils.getConnect();
            //System.out.println(connection);
            Statement statement = connection.createStatement();
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
            //System.out.println("MySQL read user "+phone);
            user = new User(username,password,realname,id,phone,type);
            statement.close();
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }


    //上传评价
    public static String addComments(Comment comment){
        if(comment==null){
            return "null";
        }
        String authorID = comment.getAuthorID();
        String houseID = comment.getHouseID();
        String content = comment.getContent();
        String date = comment.getDate();
        float score = comment.getScore();
        String sql = "insert into comments(authorID,houseID,content,date,score) values('"+authorID+"','"+houseID+"','"+content+"','"+date+"','"+score+"')";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Comments add successfully.");
            preparedStatement.close();
            connection.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        finally{
            HouseDBconnection p = new HouseDBconnection();
            p.changeScore(houseID);
        }
    }
    //下传评价至房源
    public static ArrayList<Comment> selectComments(House house){
        String id = house.getHouseID();
        String sql = "select * from comments where houseID='"+id+"'";
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {
            Connection connection = jdbcUtils.getConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String authorID = resultSet.getString("authorID");
                String houseID = resultSet.getString("houseID");
                String content = resultSet.getString("content");
                String date = resultSet.getString("date");
                String score = resultSet.getString("score");
                comments.add(new Comment(authorID,date,houseID,content,score));
            }
            //System.out.println("Return comments to house successfully.");
            statement.close();
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Collections.sort(comments,new sortCom());
        return comments;
    }
    //下传评价至用户
    public static ArrayList<Comment> selectComments(Buyer buyer){
        String id = buyer.getPhone();
        String sql = "select * from comments where authorID='"+id+"'";
        ArrayList<Comment> comments = new ArrayList<Comment>();
        try {
            Connection connection = jdbcUtils.getConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String authorID = resultSet.getString("authorID");
                String houseID = resultSet.getString("houseID");
                String content = resultSet.getString("content");
                String date = resultSet.getString("date");
                String score = resultSet.getString("score");
                comments.add(new Comment(authorID,date,houseID,content,score));
            }
            statement.close();
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Collections.sort(comments,new sortCom());
        return comments;
    }
    //删除评价
    public static String delComments(Comment comment){
        String authorID = comment.getAuthorID();
        String houseID = comment.getHouseID();
        String content = comment.getContent();
        String date = comment.getDate();
        String sql = "delete from comments where authorID='"+authorID+"' and houseID='"+houseID+"' and content='"+content+"' and date='"+date+"'";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Delete comment successfully.");
            preparedStatement.close();
            connection.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    //返回评价数
    public static int countComments(String houseID){
        String sql = "select count(*) as num from comments where houseID='"+houseID+"';";
        int result = 0;
        try {
            Connection connection = jdbcUtils.getConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String num = resultSet.getString("num");
                result = Integer.parseInt(num);
            }
            statement.close();
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    //返回评价总分
    public static float sumComments(String houseID){
        String sql = "select sum(score) as sum from comments where houseID='"+houseID+"';";
        float result = 0;
        try {
            Connection connection = jdbcUtils.getConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                float sum = resultSet.getFloat("sum");
                result = sum;
            }
            statement.close();
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //上传消息
    public static String addMessages(Message message){
        if(message==null){
            return "null";
        }
        String name = message.getName();
        String content = message.getContent();
        String senderID = message.getSenderID();
        String receiverID = message.getReceiverID();
        String date = message.getDate();
        String read = message.getRead();
        String sql = "insert into messages(name,senderID,receiverID,content,date,status) values('"+name+"','"+senderID+"','"+receiverID+"','"
                +content+"','"+date+"','"+read+"')";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Message upload successfully.");
            preparedStatement.close();
            connection.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    //下传消息
    public static ArrayList<Message> selectMessages(User u1, User u2){
        if(u1==null || u2==null){
            System.out.println("error, no user.");
            return null;
        }
        String id1 = u1.getPhone();
        String id2 = u2.getPhone();
        String sql = "select * from htpbase.messages\n" +
                "where (senderID='"+id1+"' and receiverID='"+id2+"') or (senderID='"+id2+"' and receiverID='"+id1+"');";
        ArrayList<Message> messages = new ArrayList();
        try {
            Connection connection = jdbcUtils.getConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String senderID = resultSet.getString("senderID");
                String receiverID = resultSet.getString("receiverID");
                String content = resultSet.getString("content");
                String date = resultSet.getString("date");
                String read = resultSet.getString("status");
                messages.add(new Message(name,senderID,receiverID,content,date,read));
            }
            //System.out.println("Return messages successfully.");
            statement.close();
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Collections.sort(messages,new sortMessage());
        return messages;
    }
    //修改状态
    public static String changeMessageStatus(Message message,String status){
        String senderID = message.getSenderID();
        String receiverID = message.getReceiverID();
        String content = message.getContent();
        String date = message.getDate();
        String sql = "update messages set status='"+status+"' where senderID='"+senderID+"' and receiverID='"+
                receiverID+"' and content='"+content+"' and date='"+date+"';";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = (PreparedStatement)connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Message status changed successfully.");
            preparedStatement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }
    //删除消息
    public static String delMessage(Message message){
        String send = message.getSenderID();
        String receive = message.getReceiverID();
        String content = message.getContent();
        String date = message.getDate();
        String sql = "delete from messages where senderID='"+send+"' and receiverID='"+receive+"' and content='"
                +content+"' and date='"+date+"';";
        try {
            Connection connection = jdbcUtils.getConnect();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //System.out.println("Delete message successfully.");
            preparedStatement.close();
            connection.close();
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
    }
    //返回会话
    public static ArrayList<Conversation> selectConversation(User u){
        String id = u.getPhone();
        String sql = "select * from messages where senderID='"+id+"' or receiverID='"+id+"';";
        ArrayList<Conversation> list = new ArrayList<>();
        try {
            Connection connection = jdbcUtils.getConnect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            HashSet<String> index = new HashSet<>();
            ArrayList<Message> messages = new ArrayList<>();
            index.add(id);
            while (resultSet.next()){
                String name = resultSet.getString("name");
                String senderID = resultSet.getString("senderID");
                String receiverID = resultSet.getString("receiverID");
                String content = resultSet.getString("content");
                String date = resultSet.getString("date");
                String read = resultSet.getString("status");
                messages.add(new Message(name,senderID,receiverID,content,date,read));
                /*if(index.add(senderID)){
                    list.add(new Conversation(senderID,name,date,content,read));
                }
                else if(index.add(receiverID)){
                    list.add(new Conversation(receiverID,name,date,content,read));
                }*/
            }
            Collections.sort(messages, new sortMessage());
            for(int i=0;i<messages.size();i++){
                String senderID = messages.get(i).getSenderID();
                String receiverID = messages.get(i).getReceiverID();
                if(index.add(senderID)){
                    String name = messages.get(i).getName();
                    String date = messages.get(i).getDate();
                    String read = messages.get(i).getRead();
                    String content = messages.get(i).getContent();
                    list.add(new Conversation(senderID,name,date,content,read));
                }
                else if(index.add(receiverID)){
                    String name = messages.get(i).getName();
                    String date = messages.get(i).getDate();
                    String read = messages.get(i).getRead();
                    String content = messages.get(i).getContent();
                    list.add(new Conversation(receiverID,name,date,content,read));
                }
            }
            System.out.println("Return conversations successfully.");
            statement.close();
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Collections.sort(list,new sortConser());
        return list;
    }

}
class sortMessage implements Comparator {

    public int compare(Object o1, Object o2) {
        //倒序，日期最靠前的在最前
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Message m1 = (Message)o1;
        Message m2 = (Message)o2;
        Date d1,d2;
        try {
            d1 = format.parse(m1.getDate());
            d2 = format.parse(m2.getDate());
        } catch (ParseException e) {
            // 解析出错，则不进行排序
            System.out.println("ComparatorDate--compare--SimpleDateFormat.parse--error");
            return 0;
        }
        if (d1.before(d2)) {
            return 1;
        } else {
            return -1;
        }
    }

}
class sortConser implements Comparator {

    public int compare(Object o1, Object o2) {
        //倒序，日期最靠前的在最前
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Conversation m1 = (Conversation)o1;
        Conversation m2 = (Conversation)o2;
        Date d1,d2;
        try {
            d1 = format.parse(m1.getDate());
            d2 = format.parse(m2.getDate());
        } catch (ParseException e) {
            // 解析出错，则不进行排序
            System.out.println("ComparatorDate--compare--SimpleDateFormat.parse--error");
            return 0;
        }
        if (d1.before(d2)) {
            return 1;
        } else {
            return -1;
        }
    }
}
class sortCom implements Comparator {

    public int compare(Object o1, Object o2) {
        //倒序，日期最靠前的在最前
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
        Comment m1 = (Comment)o1;
        Comment m2 = (Comment)o2;
        Date d1,d2;
        try {
            d1 = format.parse(m1.getDate());
            d2 = format.parse(m2.getDate());
        } catch (ParseException e) {
            // 解析出错，则不进行排序
            System.out.println("ComparatorDate--compare--SimpleDateFormat.parse--error");
            return 0;
        }
        if (d1.before(d2)) {
            return 1;
        } else {
            return -1;
        }
    }
}
class sortReq implements Comparator {

    public int compare(Object o1, Object o2) {
        //倒序，日期最靠前的在最前
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
        Request m1 = (Request)o1;
        Request m2 = (Request)o2;
        Date d1,d2;
        try {
            d1 = format.parse(m1.getDate());
            d2 = format.parse(m2.getDate());
        } catch (ParseException e) {
            // 解析出错，则不进行排序
            System.out.println("ComparatorDate--compare--SimpleDateFormat.parse--error");
            return 0;
        }
        if (d1.before(d2)) {
            return 1;
        } else {
            return -1;
        }
    }
}
