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
                        <option value="2">Février</option>
                        <option value="3">Mars</option>
                        <option value="4">Avril</option>
                        <option value="5">Mai</option>
                        <option value="6">Juin</option>
                        <option value="7">Juillet</option>
                        <option value="8">Août</option>
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

if(anneeStr != null && moisStr != null ) {
    int annee = Integer.parseInt(anneeStr);
    int mois = Integer.parseInt(moisStr);

    List<HistoSal> employees = HistoSal.getHistoSalByDate(mois, annee);

    if(employees.isEmpty()) { %>
        <p class="mt-3 alert alert-warning">Aucun enregistrement pour ce mois/annee.</p>
<%  } else { %>

    <table class="table table-success table-striped mt-3">
        <tr>
            <th>EmpNo</th>
            <th>Date</th>
            <th>Salaire Net</th>
            <th>Show</th>
        </tr>
<%
    for (HistoSal emp : employees) { %>

        <p><%= emp.getEmpNo() %></p>

       <% List<HistoCombine> details = HistoCombine.getHistosalById(emp.getEmpNo());
        if(details.isEmpty()) { %>
            <p class="mt-3 alert alert-warning">Il y a l'erreur dans leHistoId</p>
       <% }

        double salNet = 0;
        double cnaps = 0;
        double valMin = 0;
        double valMax = 0;
        double valeurCnaps = 0;

        for(HistoCombine hc : details) {
            int num = hc.getHistoSal().getEmpNo();
            String rub = hc.getRubrique().getValeur();
            if(rub.equalsIgnoreCase("BASE SALARY")) {
                double salBrut = hc.getHistoSal().getSal();
                
                // si tu veux utiliser CNAPS, tu peux le récupérer ici
                if(details.size() > 1) {
                    for(HistoCombine h : details) {
                        if(h.getRubrique().getValeur().equalsIgnoreCase("CNAPS")) {
                            cnaps = h.getHistoSal().getSal();
                            valMin = h.getConfig().getMin();
                            valMax = h.getConfig().getMax();
                            valeurCnaps = h.getConfig().getValeur();
                            break;
                        }
                    }
                }

                double salNetSansIrsa = Calcul.calculSalaireDepense(salBrut, cnaps, valMin, valMax, valeurCnaps);
                salNet = Calcul.salaireFinale(salNetSansIrsa);
                break;
            } %>
            <tr>
            <td><%= num %></td>
            <td><%= hc.getHistoSal().getEmpNo() %></td>
            <td><%= salNet %></td>
            <td>
                <form action="calculTable.jsp" method="post">
                    <input type="hidden" name="salBrut" value="<%= salNet %>">
                    <input type="hidden" name="num" value="<%= num %>">
                    <button type="submit" class="btn btn-info btn-sm">Voir Calcul</button>
                </form>
            </td>
        </tr>
        
<%  } 
    } %>
    </table>
<% } // else employees not empty
} // if annee/mois
%>
</div>
</body>
</html>
