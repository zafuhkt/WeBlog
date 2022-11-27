package Class;
import java.sql.Connection;
import java.sql.DriverManager;
public class DbConn {
    public static Connection _conn;
    public DbConn(){}
    // 获得数据库连接
    public static Connection GetConnection(){
        Connection con = null;
        try{
            Class.forName( "com.mysql.jdbc.Driver" );// 加载MySql数据驱动
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/course_design","root", "root" );// 创建数据库连接
        }
        catch( Exception e ){
            e.printStackTrace();
            System.out.printf( "数据库连接失败\n" );
        }
        return con;
    }
}
