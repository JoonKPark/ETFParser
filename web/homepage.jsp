<%-- 
    Document   : homepage
    Created on : Feb 14, 2016, 8:43:54 PM
    Author     : Jason Park
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/foundation.css"/>
        <title>Welcome to ETF Parser!</title>

        

    </head>
    <body>
        <%if(null == session.getAttribute("isValid")){ %>  <c:redirect url="/index.jsp"/>    <%}%>
        <div class="right-align"><a href="logout.jsp" class="button">Logout</a></div>
        
        <div class ="container" >
            <div class="row centered"><h1 class="centerText">Welcome!</h1> </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="large-12 columns centered">
                    <form action="SearchResult" method="POST">
                        <div class="row centerText"> 
                            Enter the ticker symbol of the ETF:
                            <input class="centerText" name="symbol" placeholder="Ticker symbol, e.g. SPY" type="search" required="required" maxlength="6" list="query"/>
                            <datalist id="query">
                            <c:forTokens items="${queries}" delims=";" var="q">
                                <option value="<c:out value="${q}"/>">
                            </c:forTokens>
                            </datalist>
                        </div>
                        <input type="submit" value="Search" class="button columns centered"/>
                    </form>
                </div>
            </div>
        </div>
        <div class="column centered">
            <jsp:include page="WEB-INF/jsp/fundinfohub.jsp"/>
        </div>


    </body>
</html>
