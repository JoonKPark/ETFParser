<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/foundation.css"/>
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body>
        <div class="block" style="height:300px;"></div>
        <div class="container">
            <div class="row">
                <div class="large-12 columns centered">
                    <form action="LoginServlet" method="POST">
                        <div class="row">
                            <input name="username" placeholder="Username" type="text" required="required"/>
                        </div>
                        <div class="row">
                            <input name="password" placeholder="Password" type="password" required="required"/>
                        </div>
                        <input type="submit" value="Log in" class="button columns centered"/>
                    </form>
                    <a href="register.jsp" class="button columns centered">Register</a>
                </div>
            </div>
        </div>
    </body>
</html>