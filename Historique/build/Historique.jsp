<%@ page import="java.util.*, database.TestOracle, constituant.Requete" %>
<%@ page import="constituant.Employees, constituant.HistoSal" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="assests/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="assests/bootstrap/js/bootstrap.bundle.min.js"></script>
    <title>Formulaire JSP</title>
</head>
<body>
    <% List<HistoSal> liste = HistoSal.getHistosal();
    if(liste == null || liste.isEmpty()) { %>
        <p style="color:orange">Aucun historique trouvé — vérifiez la connexion à la base de données et les logs serveur.</p>
    <% } %>
<h4><a href="Generer.jsp" class="link-secondary link-offset-2 link-underline-opacity-25 link-underline-opacity-100-hover">Generer des employees</a></h4>
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-white">
                        <h2 class="h4 text-center mb-0">Liste des personnes</h2>
                    </div>
                    <div class="card-body">

<table class="table table-success table-striped">
<tr>
    <td>Date</td>
    <td>Id_EMP</td>
    <td>Salaire</td>
    <td>Id_Rubrique</td>
</tr>
<% for(HistoSal emp : liste) { %>
<tr class="table-active">
    <td><%= emp.getDateChange() %></td>
    <td><%= emp.getEmpNo() %></td>
    <td><%= emp.getSal() %></td>
    <td><%=emp.getIdRubrique() %></td>
</tr>
<% } %>
</table>

    </div>
                </div>
            </div>
        </div>
    </div>


 <footer class="absolute-bottom bg-white py-3">
        <div class="container text-center">
            <span class="text-muted">2025 Noah Town Corporation</span>
        </div>
    </footer>

</body>
</html>
