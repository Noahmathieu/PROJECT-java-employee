    <%@ page import="java.util.*, java.sql.Date, database.TestOracle, constituant.Requete" %>
    <%@ page import="constituant.Employees, constituant.HistoSal, constituant.Calcul" %>

    <% 
    double salBrut = request.getParameter("salBrut");
    double cnaps = request.getParameter("cnaps");
    int empno = request.getParameter("num");
    double irsa = request.getParameter("irsa");
    double salNet = request.getParameter("salNet");

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
    <td>IRSA</td>
    <td><%= Irsa %></td>
  </tr>
  <tr>
    <td>Salaire NET</td>
    <td><%= salNet %></td>
  </tr>
</table>


</body>
</html>
