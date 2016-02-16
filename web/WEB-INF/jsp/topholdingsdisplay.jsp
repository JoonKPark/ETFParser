<%-- 
    Document   : topholdingsdisplay
    Created on : Feb 14, 2016, 11:50:16 PM
    Author     : Jason Park
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <body class="centerText">
        <c:choose>
            <c:when test="${empty holdings}"></c:when>
            <c:otherwise>
                <table style="background-color: transparent; border:transparent">
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <th>Name</th>
                                    <th>Percent</th>
                                    <th>Shares</th>
                                </tr>
                                <c:forEach items="${holdings}" var="holding">
                                    <tr>
                                        <td><c:out value="${holding.name}"></c:out></td>
                                        <td><c:out value="${holding.percent}"></c:out>%</td>
                                        <td><fmt:formatNumber type="number" value="${holding.shares}"></fmt:formatNumber></td>
                                        </tr>
                                </c:forEach>
                            </table>
                        </td>
                        <td><div id="chart_holdings_div"></div></td>
                    </tr>
                    <tr style="background-color: transparent"><td>
                            <form action="Download" method="POST">
                                <input type="hidden" name="dlreq" value="holdings">
                                <input type="submit" value="Download .csv" class="button columns centered"/>
                            </form></td></tr>
                </table>
            </c:otherwise>
        </c:choose>
        </body>
    </html>
