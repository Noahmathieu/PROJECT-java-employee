    <%@ page import="java.util.*, java.sql.Date, database.TestOracle, constituant.*" %>

 <%
    String salBrutParam = request.getParameter("salBrut");
    String cnapsParam = request.getParameter("cnaps");
    String empnoParam = request.getParameter("num");
    String irsaParam = request.getParameter("irsa");
    String salNetParam = request.getParameter("salNet");
    String gainParam = request.getParameter("gain");
    String perteParam = request.getParameter("retenu");


    double salBrut = (salBrutParam != null && !salBrutParam.isEmpty()) ? Double.parseDouble(salBrutParam) : 0.0;
    double cnaps = (cnapsParam != null && !cnapsParam.isEmpty()) ? Double.parseDouble(cnapsParam) : 0.0;
    int empno = (empnoParam != null && !empnoParam.isEmpty()) ? Integer.parseInt(empnoParam) : 0;
    double irsa = (irsaParam != null && !irsaParam.isEmpty()) ? Double.parseDouble(irsaParam) : 0.0;
    double salNet = (salNetParam != null && !salNetParam.isEmpty()) ? Double.parseDouble(salNetParam) : 0.0;
    double gain = (gainParam != null && !gainParam.isEmpty()) ? Double.parseDouble(gainParam) : 0.0;
    double perte = (perteParam != null && !perteParam.isEmpty()) ? Double.parseDouble(perteParam) : 0.0;
%>

    <!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="assests/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="assests/bootstrap/js/bootstrap.bundle.min.js"></script>
    <title>Formulaire JSP</title>
</head>
<body>

<h2>Tableau du Personne: <%= empno %></h2>

<table class="table table-bordered">
  <tr>
    <td>Salaire Brut</td>
    <td><%= salBrut %></td>
  </tr>
  <tr>
    <td>Cnaps(avec les taux)</td>
    <td><%= cnaps %></td>
  </tr>
  <tr>
    <td>Gain</td>
    <td><%= gain %></td>
  </tr>
  <tr>
    <td>RETENU</td>
    <td><%= perte %></td>
  </tr>
  <tr>
    <td>IRSA</td>
    <td><%= irsa %></td>
  </tr>
  <tr>
    <td>Salaire NET</td>
    <td><%= salNet %></td>
  </tr>
</table>


</body>
</html>
