<%@ page import="uk.ac.ucl.model.Patient" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: amodi
  Date: 15/03/2020
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
    <h2>Patients:</h2>
    <ul>
        <%
            Patient patient = (Patient) request.getAttribute("patient");
            ArrayList<String> values = patient.getColumnValues();
            ArrayList<String> colNames = patient.getColumnNames();
            for (int i = 0; i < patient.getColCount(); i++)
            {
                String text = colNames.get(i) + ": " + values.get(i);
        %>
        <li><%=text%></li>
        <% } %>
    </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>