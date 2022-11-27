package bean;
import Class.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Agreebean {
    private Connection conn;

    public ArrayList<Agree> Agree_load(int id){
        ArrayList list=new ArrayList();
        conn = DbConn.GetConnection();
        try {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(" select a.user_id, u.name, a.text, u.pic")
                    .append(" from agree a,user u,blog b where b.id = a.id and a.user_id = u.id and b.id = ?");
            PreparedStatement st = this.conn.prepareStatement(sBuffer.toString());
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                int user_id = rs.getInt("a.user_id");
                String name=rs.getString("u.name");
                String text=rs.getString("a.text");
                String head=rs.getString("u.pic");
                Agree info=new Agree(id,user_id,name,text,head);
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
    public int Agree_insert(int id,int user_id,String text){
        ArrayList list=new ArrayList();
        conn = DbConn.GetConnection();
        try {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append(" insert into agree ")
                    .append( " ( id, user_id, text ) " )
                    .append(" values ( ?, ?, ? ) ");
            PreparedStatement st = this.conn.prepareStatement(sBuffer.toString());
            st.setInt(1, id);
            st.setInt(2, user_id);
            st.setString(3, text);
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
    public int Agree_delete(int id,int user_id,String text){
        ArrayList list=new ArrayList();
        conn = DbConn.GetConnection();
        try {
            StringBuffer sBuffer = new StringBuffer();
            sBuffer.append( "delete from agree " )
                    .append( " where id = ? and user_id = ? and text = ?  ");
            PreparedStatement st = this.conn.prepareStatement(sBuffer.toString());
            st.setInt(1, id);
            st.setInt(2, user_id);
            st.setString(3, text);
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
