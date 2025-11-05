<%@ page import="java.util.*, database.TestOracle, constituant.Requete" %>
<%@ page import="constituant.Employees" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="assests/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="assests/bootstrap/js/bootstrap.bundle.min.js"></script>
    <title>Formulaire JSP</title>
</head>
<body>
<% 
    List<Employees> liste = Requete.getEmployees();
    
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
        <div class="container">
            <span class="navbar-brand">Gestion des Employes</span>
            <div class="navbar-nav ms-auto">
                <a class="nav-link text-white" href="Filter.jsp">
                    <i class="bi bi-clock-history"></i> Old Historique 
                </a>
                 <a class="nav-link text-white" href="Historique.jsp">
                    <i class="bi bi-clock-history"></i> Historique
                </a>
            </div>
        </div>
    </nav>

 <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-white">
                        <h2 class="h4 text-center mb-0">Choisir la personne qui va etre modifier</h2>
                    </div>
                    <div class="card-body">

    <form action="page2.jsp" method="post">
       <select class="form-select" name="employees" id="validationCustom04" required>
             <option selected>Tous les employers</option>
             <% if (liste != null && !liste.isEmpty()) {
                   for(Employees emp : liste) { %>
             <option value="<%= emp.getEmpno() %>"><%= emp.getEname() %></option>
             <%       }
               } else { %>
             </select>
             <p style="color:orange">Aucun employé trouvé — vérifiez la connexion à la base de données et les logs serveur.</p>
             <% } %>

        <input type="submit" value="Changer" class="btn btn-primary">
    </form>

    </div>
                </div>
            </div>
        </div>
    </div>

 <footer class="fixed-bottom bg-white py-3">
        <div class="container text-center">
            <span class="text-muted">2025 Noah Town Corporation</span>
        </div>
    </footer>

</body>
</html>
