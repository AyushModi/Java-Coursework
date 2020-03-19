<%@ page import="java.util.List" %>
<%@ page import="uk.ac.ucl.model.Patient" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <jsp:include page="/meta.jsp"/>
  <title>Patient Data App</title>
</head>
<body>
<jsp:include page="/header.jsp"/>
<div class="main">
  <h2>Patients:</h2>
  <form method="POST" action="/runsearch.html">
    <input type="text" name="FIRST" placeholder="First name contains"/>
    <input type="text" name="LAST" placeholder="Last name contains"/>
    <input type="submit" value="Search"/>
  </form>
  <ul>
    <%
      List<Patient> patients = (List<Patient>) request.getAttribute("patientNames");
      for (Patient patient : patients)
      {
        String href = "patient/"+patient.getID();
    %>
    <li><a href="<%=href%>"><%=patient.getName()%></a>
    </li>
    <% } %>
  </ul>
</div>
<jsp:include page="/footer.jsp"/>
</body>
</html>
