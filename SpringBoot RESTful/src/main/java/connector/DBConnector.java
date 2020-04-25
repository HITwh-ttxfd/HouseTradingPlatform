package connector;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;

@Component
public class DBConnector {
    //数据库连接基类
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://39.98.48.34:3306/htpbase?serverTimezone=UTC&autoReconnect=true&amp;failOverReadOnly=false";
    String user = "root";
    String password = "2296026";
    Connection connection = null;
    //加载驱动程序
    public DBConnector(){
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
    //关闭数据库
    public void close(){
        try {
            this.connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
