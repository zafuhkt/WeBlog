package bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Class.*;

public class Commentbean {
    private Connection conn;

    public ArrayList<Comment> Comment_load(int id){
        ArrayList list=new ArrayList();
        conn = DbConn.GetConnection();

        try {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(" select c.user_id, u.name, c.time, c.place, c.text, u.pic")
                    .append(" from comment c,user u,blog b where b.id = c.id and c.user_id = u.id and b.id = ?");
            PreparedStatement st = this.conn.prepareStatement(sBuffer.toString());
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                int user_id = rs.getInt("c.user_id");
                String name=rs.getString("u.name");
                String time=rs.getString("c.time");
                String place=rs.getString("c.place");
                String text=rs.getString("c.text");
                String head=rs.getString("u.pic");
                Comment info=new Comment(id,user_id,name,time,place,text,head);
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
}
