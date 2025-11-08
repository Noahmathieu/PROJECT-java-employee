<%@ page import="java.util.*, java.sql.Date, database.TestOracle, constituant.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="assests/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="assests/bootstrap/js/bootstrap.bundle.min.js"></script>
    <title>Formulaire JSP</title>
</head>
<body>
<div class="container mt-4">
    <div class="card">
        <div class="card-body">
            <h4 class="card-title">Generer les salaires</h4>
            <form action="Generer.jsp" class="row g-3" method="post">
                <div class="col-md-4">
                    <label class="form-label">Choisir le mois</label>
                    <select class="form-select" name="mois" required>
                        <option value="1">Janvier</option>
                        <option value="2">Fevrier</option>
                        <option value="3">Mars</option>
                        <option value="4">Avril</option>
                        <option value="5">Mai</option>
                        <option value="6">Juin</option>
                        <option value="7">Juillet</option>
                        <option value="8">Aout</option>
                        <option value="9">Septembre</option>
                        <option value="10">Octobre</option>
                        <option value="11">Novembre</option>
                        <option value="12">Décembre</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Annee</label>
                    <input type="number" class="form-control" name="annee" required>
                </div>
                <div class="col-12">
                    <button class="btn btn-primary" type="submit">Generer</button>
                </div>
            </form>
        </div>
    </div>

<%
String anneeStr = request.getParameter("annee");
String moisStr = request.getParameter("mois");

if(anneeStr != null && moisStr != null) {
    int annee = Integer.parseInt(anneeStr);
    int mois = Integer.parseInt(moisStr);

    List<HistoSal> employees = HistoSal.getHistoSalByDate(mois, annee);
    
    String nomJour = HistoSal.getNameDate(mois, annee);
    
    if(employees.isEmpty()) { %>
        <p class="mt-3 alert alert-warning">Aucun enregistrement pour ce mois/annee</p>
   
   <% } else { %>
            <table class="table table-success table-striped mt-3">
                <form action="treat.jsp" method="post">
                <tr>
                    <td>EmpNo</td>
                    <td>Date</td>
                    <% if(nomJour == "SATURDAY" || nomJour == "SUNDAY") {
                        for(int j = 0; j < 4; j++ ) { %>
                            <td>S<%= j %> </td>
                       <% } %>
                    <% } else { 
                        for(int i = 0; i < 5; i++ ) { %>
                            <td>S<%= i %> </td>
                       <% } %>
                        
                   <% } %>
                    <td>Salaire Net</td>
                    <td>Voir Calcul</td>
                </tr>

        <% for(HistoSal emp : employees){ %>
                <tr>
                    <td><%= emp.getEmpNo() %></td>
                    <td><%= emp.getDateChange() %></td>
                     <% if(nomJour == "SATURDAY" || nomJour == "SUNDAY") { %>
                        <td><input type="number" name="s1" value="0" ></td>
                        <td><input type="number" name="s2" value="0"></td>
                        <td><input type="number" name="s3" value="0"></td>
                        <td><input type="number" name="s4" value="0"></td>
                    <% } else { %>
                        <td><input type="number" name="s1" value="0" ></td>
                        <td><input type="number" name="s2" value="0" ></td>
                        <td><input type="number" name="s3" value="0"></td>
                        <td><input type="number" name="s4" value="0"></td>
                        <td><input type="number" name="s5" value="0"></td>
                   <% } %>
         
            </tr>
       <% } %>
        </form>
   </table>
   <% }
   
   }%>
</div>
</body>
</html>