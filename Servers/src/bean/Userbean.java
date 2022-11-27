package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Class.*;

public class Userbean {
    private Connection conn;

    public int add(User user){
        int count = -1; // 受影响的记录条数
        conn = DbConn.GetConnection();// 获得连接
        System.out.printf( "user: " +user.toString() );
        try{
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append( "insert into user " )
                    .append( " ( id, name, password, posts, followers, followings, pic ) " )
                    .append( " values ( ?, ?, ?, ? ,?, ? ,?) " );
            PreparedStatement st = conn.prepareStatement(  sBuffer.toString() );
            st.setInt( 1, user.getId());
            st.setString( 2, user.getName());
            st.setString(3,user.getPassword());
            st.setInt(4,user.getPosts());
            st.setInt(5,user.getFollowers());
            st.setInt(6,user.getFollowings());
            st.setString(7,user.getPic());

            count = st.executeUpdate( );// 执行语句
            System.out.printf( "成功插入%d条记录\n", count );
            return count;

        }
        catch( SQLException e ){
            System.out.printf( "插入失败:" + e.getMessage() );
            e.printStackTrace();
            return -1;
        }
        finally{
            if( conn != null ){
                try{
                    conn.close();
                }
                catch( SQLException e ){
                    System.out.printf( "关闭连接失败\n" + e.getMessage()  );
                }// try
            }// if
        }
    }
//登陆时查找用户是否存在
    public User findByid(int id)
    {
        int flag=0;
        String rightPwd="";
        User user=new User();
        conn = DbConn.GetConnection();
        try {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(" select *").append(" from user where id=?");
            PreparedStatement st = this.conn.prepareStatement(sBuffer.toString());
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            //判断是否存在用户
            if(rs.next())
            {
                //用户已存在 获取用户信息
                flag=1;
                String name=rs.getString("name");
                String password=rs.getString("password");
                int posts=rs.getInt("posts");
                int followers=rs.getInt("followers");
                int followings=rs.getInt("followings");
                String pic=rs.getString("pic");
                user=new User(id,name,password,posts,followers,followings,pic);

            }
        } catch (SQLException var14) {
            System.out.printf("数据库查询失败\n" + var14.getMessage());
            var14.printStackTrace();
        } finally {
            if (this.conn != null) {
                try {
                    this.conn.close();
                } catch (SQLException var13) {
                    System.out.printf("关闭连接失败\n" + var13.getMessage());
                }
            }
        }
        if(flag==0)
            return null; //用户不存在返回空
        else
            return user;
    }

}
