<%-- 
    Document   : register
    Created on : Feb 15, 2016, 1:32:50 PM
    Author     : Jason Park
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/foundation.css"/>
        <title>Register new user.</title>
    </head>
    <body>
        <div class="container row centerText"><h1>Register new user</h1></div>
        <form action="RegisterServlet" method="POST">
            <div class ="container">
            <div class="row">
                <input name="username" placeholder="Username" type="text" required="required"/>
            </div>
            <div class="row">
                <input name="password" placeholder="Password" type="password" required="required"/>
            </div>
            <div class="row">
                <input name="email" placeholder="e-mail address" type="text"/>
            </div>
            <div class="row">
                <input name="fname" placeholder="First name" type="text"/>
            </div>
            <div class="row">
                <input name="lname" placeholder="Last name" type="text"/>
            </div>
            <input type="submit" value="Create user" class="button columns centered"/>
            </div>
        </form>
    </body>
</html>
