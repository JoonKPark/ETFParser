<%-- 
    Document   : fundinfohub
    Created on : Feb 15, 2016, 3:29:30 AM
    Author     : Jason Park
--%>

<%@page import="backend.Holding"%>
<%@page import="backend.Country"%>
<%@page import="backend.Sector"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            ArrayList<Country> countries = (ArrayList) session.getAttribute("countries");
            ArrayList<Sector> sectors = (ArrayList) session.getAttribute("sectors");
            ArrayList<Holding> holdings = (ArrayList) session.getAttribute("holdings");
        %>
        <!--Load the AJAX API-->
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">

            // Load the Visualization API and the corechart package.
            google.charts.load('current', {'packages': ['corechart']});
            // Set a callback to run when the Google Visualization API is loaded.
            google.charts.setOnLoadCallback(drawChart);

            // Callback that creates and populates a data table,
            // instantiates the pie chart, passes in the data and
            // draws it.
            function drawChart() {



                // For Sector weights.
            <% if (sectors != null) {  %>
                var dataSector = new google.visualization.DataTable();
                dataSector.addColumn('string', 'Name');
                dataSector.addColumn('number', 'Weight');
            <% for (int i = 0; i < sectors.size(); i++) {%>
                dataSector.addRows([['<%=sectors.get(i).getName()%>',<%=sectors.get(i).getWeight()%>]]);
            <% }%>
                var optionsSector = {'title': 'Sector Weights, in percent',
                    'left': 0,
                    'backgroundColor': 'transparent',
                    'top': 0,
                    'height': <%=sectors.size() * 40%>};
                var chart2 = new google.visualization.PieChart(document.getElementById('chart_sector_div'));
                chart2.draw(dataSector, optionsSector);
            <% } %>

            <% if (holdings != null) { %>
                var dataHolding = new google.visualization.DataTable();
                dataHolding.addColumn('string', 'Name');
                dataHolding.addColumn('number', 'Weight');
            <% for (int i = 0; i < holdings.size(); i++) {%>
                dataHolding.addRows([['<%=holdings.get(i).getName()%>',<%=holdings.get(i).getPercent()%>]]);
            <% }%>
                var optionsHolding = {
                    'title': 'Top 10 Holdings, ordered by percent',
                    'hAxis': {
                        title: 'Holder name'
                    },
                    'vAxis': {
                        title: 'Percentage held',
                        gridlines: {count: 5}
                    },
                    'height':<%=holdings.size() * 40%>,
                    'backgroundColor': 'transparent',
                    'legend': 'none'
                };

                var chart3 = new google.visualization.ColumnChart(document.getElementById('chart_holdings_div'));
                chart3.draw(dataHolding, optionsHolding);
            <% }%>

                // Create the data table.
                // For Country weights.
            <% if (countries != null) { %>

                var dataCountry = new google.visualization.DataTable();
                dataCountry.addColumn('string', 'Name');
                dataCountry.addColumn('number', 'Weight');
            <% for (int i = 0; i < countries.size(); i++) {%>
                dataCountry.addRows([['<%=countries.get(i).getName()%>',<%=countries.get(i).getWeight()%>]]);
            <% }%>
                // Set chart options
                var optionsCountry = {'title': 'Country Weights, in percent',
                    'left': 0,
                    'backgroundColor': 'transparent',
                    'top': 0,
                    'height': <%=countries.size() * 40%>
                };
                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.PieChart(document.getElementById('chart_country_div'));
                chart.draw(dataCountry, optionsCountry);
            <% }%>
            }
        </script>
    </head>
    <body>

        <table style="background-color: transparent; border:transparent">
            <tbody class="centerText">
                <tr><h2><c:out value="${displaySymbol}"/></h2></tr>
        <tr><h3><c:out value="${displayName}"/></h3></tr>
    <tr><c:out value="${displayDesc}"/></tr>
    <tr><jsp:include page="topholdingsdisplay.jsp"/></tr>
    <tr><jsp:include page="sectorweightsdisplay.jsp"/></tr>
    <tr><jsp:include page="countryweightsdisplay.jsp"/></tr>
</tbody>
</table>
</body>
</html>
