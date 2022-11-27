<%@ page import="Class.*" %>
<%@ page import="bean.Commentbean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String id=request.getParameter("blog_id");
    Commentbean bean=new Commentbean();
    ArrayList Commentlist=new ArrayList();
    Commentlist=bean.Comment_load(Integer.parseInt(id));

    StringBuilder builder = new StringBuilder();
    Iterator<Comment> iter = Commentlist.iterator();
    out.println("[");
    while(iter.hasNext()) {
        Comment info = iter.next();
        builder.append(info.toString()).append("\n");
        out.println(" {");
        out.println("\"id\":\"" + info.getId() + "\"");
        out.println(", \"user_id\":\"" + info.getUser_id() + "\"");
        out.println(", \"name\":\"" + info.getName() + "\"");
        out.println(", \"time\":\"" + info.getTime() + "\"");
        out.println(", \"place\":\"" + info.getPlace() + "\"");
        out.println(", \"text\":\"" + info.getText() + "\"");
        out.println(", \"head\":\"" + info.getHead() + "\"");
        out.println(" }");
        if( iter.hasNext() ){
            out.println(",");
        }
    }
    out.println("]");
    System.out.println(builder.toString());
%>
