<%@ page import="bean.Userbean" %>
<%@ page import="Class.User" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Userbean bean = new Userbean();
    int id=Integer.parseInt(request.getParameter("id"));
    String password=request.getParameter("password");
    if(bean.findByid(id)==null)
    {
        //注册新用户
        //初始昵称 用户+id
        String name="用户"+request.getParameter("id");
        User user=new User(id,name,password,0,0,0,"0");
        int count = bean.add(user);
        out.print("Newuser");
    }
    else
    {
        if(Objects.equals(password, bean.findByid(id).getPassword()))
        {
            //密码正确
            out.print("Login successfully");
        }
        else
        {
            //密码错误
            out.print("Password incorrect");
        }

    }
    //User user=new User(12345,"aa","123456",0,0,0,0);
%>
