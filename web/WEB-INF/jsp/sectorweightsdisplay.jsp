<%-- 
    Document   : sectorweightsdisplay
    Created on : Feb 15, 2016, 2:36:34 AM
    Author     : Jason Park
--%>

<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html>
    
    <body>
        
        
        <c:choose>
            <c:when test="${empty sectors}"></c:when>
            <c:otherwise>
        <table style="background-color: transparent; border:transparent">
            <tr>
                <td>
                    <table>
                        <tr>
                            <th>Sector</th>
                            <th>Weight</th>
                        </tr>
                        <c:forEach items="${sectors}" var="sector">
                            <tr>
                                <td><c:out value="${sector.name}"></c:out></td>
                                <td><c:out value="${sector.weight}"></c:out>%</td>
                                </tr>
                        </c:forEach>
                    </table>
                </td>
                <td><div id="chart_sector_div"></div></td>
            </tr>
            <tr style="background-color: transparent"><td>
                    <form action="Download" method="POST">
                                <input type="hidden" name="dlreq" value="sectors">
                                <input type="submit" value="Download .csv" class="button columns centered"/>
                    </form></td></tr>
        </table>
            </c:otherwise>
        </c:choose>
    </body>
</html>
