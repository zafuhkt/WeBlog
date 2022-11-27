<%@ page import="bean.*" %>
<%@ page import="Class.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String id=request.getParameter("blog_id");
    Agreebean bean=new Agreebean();
    ArrayList Agreelist=new ArrayList();
    Agreelist=bean.Agree_load(Integer.parseInt(id));

    StringBuilder builder = new StringBuilder();
    Iterator<Agree> iter = Agreelist.iterator();
    out.println("[");
    while(iter.hasNext()) {
        Agree info = iter.next();
        builder.append(info.toString()).append("\n");
        out.println(" {");
        out.println("\"id\":\"" + info.getId() + "\"");
        out.println(", \"user_id\":\"" + info.getUser_id() + "\"");
        out.println(", \"name\":\"" + info.getName() + "\"");
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

