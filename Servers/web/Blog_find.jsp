<%@ page import="bean.*" %>
<%@ page import="Class.*" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String user_id=request.getParameter("main_user_id");
    Blogbean bean=new Blogbean();
    ArrayList Bloglist=new ArrayList();
    Bloglist=bean.Blog_findByuser_id(Integer.parseInt(user_id));

    StringBuilder builder = new StringBuilder();
    Iterator<Blog> iter = Bloglist.iterator();
    out.println("[");
    while(iter.hasNext()) {
        Blog info = iter.next();
        builder.append(info.toString()).append("\n");

        out.println(" {");
        out.println("\"id\":\"" + info.getId() + "\"");
        out.println(", \"user_id\":\"" + info.getUser_id() + "\"");
        out.println(", \"name\":\"" + info.getName() + "\"");
        out.println(", \"time\":\"" + info.getTime() + "\"");
        out.println(", \"place\":\"" + info.getPlace() + "\"");
        out.println(", \"text\":\"" + info.getText() + "\"");
        out.println(", \"head\":\"" + info.getHead() + "\"");
        out.println(", \"relay\":\"" + info.getRelay() + "\"");
        out.println(", \"comment\":\"" + info.getComment() + "\"");
        out.println(", \"thumbup\":\"" + info.getThumup() + "\"");


//        out.println("<user_id>" + info.getId() + "</user_id>");  //每条微博对应的用户
//        out.println("  <name>" + info.getName() + "</name>");
//        out.println("  <time>" + info.getTime() + "</time>");
//        out.println("  <place>" + info.getPlace() + "</place>");
//        out.println("  <text>" + info.getText() + "</text>");
//        out.println("  <head>" + info.getHead() + "</head>");
//        out.println("  <relay>" + info.getRelay() + "</relay>");
//        out.println("  <comment>" + info.getComment() + "</comment>");
//        out.println("  <thumup>" + info.getThumup() + "</thumup>");
        out.println(" }");
        if( iter.hasNext() ){
            out.println(",");
        }
    }
    out.println("]");
    System.out.println(builder.toString());

%>
