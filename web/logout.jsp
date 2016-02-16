<%-- 
    Document   : logout
    Created on : Feb 15, 2016, 1:09:18 PM
    Author     : Jason Park
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Logging out...</title>
    </head>
    <body>
        <
        <%
        session.invalidate();
        %>
        <c:redirect url="/index.jsp"/>
    </body>
</html>