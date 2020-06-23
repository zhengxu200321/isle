<%--
  Created by IntelliJ IDEA.
  User: zhengxu
  Date: 2020-05-17
  Time: 01:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
    <base href=" <%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>JSD巨兽岛玩家后台登陆</title>
    <link rel="stylesheet" type="text/css" href="css/Login_style.css" />
    <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
    <script type="text/javascript" src="js/Login_javascript.js"></script>
</head>

<body>

<h1 align="center">欢迎光临JSD巨兽岛无规则服务器</h1>

<div class="login_frame"></div>

<div class="LoginWindow">
    <div>
        <form method="post" action="login" onsubmit="return user_input()" class="login">
            <p>
                <label for="user_name"><font style="color: #F6F6F6">账号</font>:</label>
                <input type="text" name="user_name" id="user_name" value="">
            </p>
            <p>
                <label for="password"><font style="color: #F6F6F6">密码</font>:</label>
                <input type="password" name="password" id="password" value="">
            </p>
            <p class="login-submit">
                <button type="submit" class="login-button">Login</button>
            </p>
        </form>
        <!--    <p class="registration"><a href="http://www.jq22.com">Registration</a></p>-->
        <p style="margin-left: 15%"><font style="color: #FD482C">${err_msg}</font> </p>
    </div>
</div>
<div id="timeArea"><script> LoadBlogParts();</script></div>
</body>

</html>
