<%@ page import="bean.*" %>
<%@ page import="Class.*" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int id = 0;
    String idStr = request.getParameter("id");
    if(idStr != null && idStr.length() > 0){
        id = Integer.parseInt(idStr);
    }
    int user_id = 0;
    String user_idStr = request.getParameter("user_id");
    if(user_idStr != null && user_idStr.length() > 0){
        user_id = Integer.parseInt(user_idStr);
    }
    int thumbup = 0;
    String thumbupStr = request.getParameter("thumbup");
    if(thumbupStr != null && thumbupStr.length() > 0){
        thumbup = Integer.parseInt(thumbupStr);
    }
    String text = request.getParameter("text");
    if(text != null && text.length() > 0){
        text = URLDecoder.decode(text, "UTF-8");
    }
    Blogbean blogbean = new Blogbean();
    Agreebean agreebean = new Agreebean();
    blogbean.Update_agree(thumbup,id);
    agreebean.Agree_delete(id,user_id,text);
%>

