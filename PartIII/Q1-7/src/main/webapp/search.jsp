<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: amodi
  Date: 15/03/2020
  Time: 22:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h1>Search</h1>
    <p>Put "(empty)" to search for empty string. Blank to ignore column in search.</p>
    <form method="POST" action="/runsearch.html">
        <%
            List<String> columnNames = (List<String>) request.getAttribute("columnNames");
            for (String colName : columnNames)
            {
        %>
        <label><%=colName+": "%></label>
        <br>
        <input type="text" name="<%=colName%>" placeholder="Enter keyword for <%=colName%>"/>
        <input type="checkbox" name="matchWhole<%=colName%>" id="matchWhole<%=colName%>">
        <label for="matchWhole<%=colName%>">Match whole string</label>
         <br>
        <br>
        <% } %>
        <input type="submit" value="Search"/>
    </form>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
