package connector;

import entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class JdbcUserDao implements UserDao {

    @Override
    public void add(User form) {
        String driverClassName = "com.mysql.jdbc.Driver";	//启动驱动
        String url = "jdbc:mysql://39.98.48.34:3306";	//设置连接路径
        String username = "root";	//数据库用户名
        String password = "mysql5117A";	//数据库连接密码
        Connection con = null;		//连接
        PreparedStatement pstmt = null;	//使用预编译语句
        ResultSet rs = null;	//获取的结果集
        try {
            Class.forName(driverClassName); //执行驱动
            con = DriverManager.getConnection(url, username, password); //获取连接
            String sql = "INSERT INTO users VALUES(?,?,?,?,?,?)"; //设置的预编译语句格式
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, form.getUsername());
            pstmt.setString(2, form.getPassword());
            pstmt.setString(3, form.getRealname());
            pstmt.setString(4, form.getId());
            pstmt.setString(5, form.getPhone());
            pstmt.setString(6, form.getType());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            //关闭资源,倒关
            try {
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();  //必须要关
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public User find(String type,String key) {
        String driverClassName = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://39.98.48.34:3306";
        String mysqlusername = "root";
        String password = "mysql5117A";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            Class.forName(driverClassName);
            con = DriverManager.getConnection(url, mysqlusername, password);


            String sql = "SELECT * FROM users WHERE "+type+"=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setNString(1, key);

            rs = pstmt.executeQuery();
            if(rs == null) {
                return null;
            }
            if(rs.next()) {
                return new User(rs.getString("username"),rs.getString("password"), rs.getString("name"), rs.getString("phoneNumber"), rs.getString("id"), rs.getBoolean("isBuyer")?"buyer":"seller");
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            //关闭资源,倒关
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(con != null) con.close();  //必须要关
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}