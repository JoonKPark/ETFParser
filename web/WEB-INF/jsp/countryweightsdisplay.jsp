<%-- 
    Document   : countryweightsdisplay
    Created on : Feb 15, 2016, 1:54:40 AM
    Author     : Jason Park
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    
    <body>
        <c:choose>
            <c:when test="${empty countries}"></c:when>
            <c:otherwise>
                <table style="background-color: transparent; border:transparent">
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <th>Country</th>
                                    <th>Weight</th>
                                </tr>
                                <c:forEach items="${countries}" var="country">
                                    <tr>
                                        <td><c:out value="${country.name}"></c:out></td>
                                        <td><c:out value="${country.weight}"></c:out>%</td>
                                        </tr>
                                </c:forEach>
                            </table>
                        </td>
                        <td><div id="chart_country_div"></div></td>
                    </tr>
                    <tr style="background-color: transparent"><td>
                            <form action="Download" method="POST">
                                <input type="hidden" name="dlreq" value="countries">
                                <input type="submit" value="Download .csv" class="button columns centered"/>
                            </form></td></tr>
                </table>
            </c:otherwise>
        </c:choose>
    </body>
</html>
