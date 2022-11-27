package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Class.*;

public class Blogbean {
    private Connection conn;

    public ArrayList<Blog> Blog_findByuser_id(int user_id){
        ArrayList list=new ArrayList();
        conn = DbConn.GetConnection();
        try {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(" select b.id, u.name, time, place, text, u.pic,relay,comment,thumbup")
                    .append(" from blog b,user u where b.user_id=u.id and u.id=?");
            PreparedStatement st = this.conn.prepareStatement(sBuffer.toString());
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            //判断是否存在用户
            while(rs.next())
            {
                int id=rs.getInt("b.id");
                String name=rs.getString("u.name");
                String time=rs.getString("time");
                String place=rs.getString("place");
                String text=rs.getString("text");
                String head=rs.getString("u.pic");
                int relay=rs.getInt("relay");
                int comment=rs.getInt("comment");
                int thumbup=rs.getInt("thumbup");

                Blog info=new Blog(id,user_id,name,time,place,text,head,relay,comment,thumbup);
                list.add(info);
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
        return  list;
    }

    public int Update_agree(int thumbup,int id){
        conn = DbConn.GetConnection();
        try {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append( " update blog " )
                    .append( " set thumbup = ? "
                            + " where id = ? " );
            PreparedStatement st = this.conn.prepareStatement(sBuffer.toString());
            st.setInt(1, thumbup);
            st.setInt(2, id);
            int rs = st.executeUpdate();
        } catch (SQLException var14) {
            System.out.printf("数据库查询失败\n" + var14.getMessage());
            var14.printStackTrace();
            return 0;
        } finally {
            if (this.conn != null) {
                try {
                    this.conn.close();
                } catch (SQLException var13) {
                    System.out.printf("关闭连接失败\n" + var13.getMessage());
                }
            }
        }
        return  1;
    }
}
