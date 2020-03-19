<%@ page import="uk.ac.ucl.model.AnalyticsData" %><%--
  Created by IntelliJ IDEA.
  User: amodi
  Date: 18/03/2020
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Patient Data App</title>
</head>
<jsp:include page="/header.jsp"/>
<div class="main">
    <%
        AnalyticsData analyticsData = (AnalyticsData) request.getAttribute("analyticsData");
    %>
    <h1>Analytics</h1>
    <ul>
        <li>Oldest patient: <a href="patient/<%=analyticsData.getOldest().getID()%>"><%=analyticsData.getOldest().getName()%></a></li>
        <li>Youngest patient: <a href="patient/<%=analyticsData.getYoungest().getID()%>"><%=analyticsData.getYoungest().getName()%></a></li>
        <li>Average age: <%=analyticsData.getAverageAge()%></li>
        <br>
        <%
            for (int i = 0; i < analyticsData.getNumInEachDecade().length; i++) {

        %>
        <li>Number of patients between ages <%=10*i%> and <%=10*i+10%>: <%=analyticsData.getNumInEachDecade()[i]%></li>
        <%
            }
        %>
    </ul>

</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>