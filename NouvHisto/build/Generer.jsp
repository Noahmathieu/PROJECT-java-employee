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

    if(employees.isEmpty()) { %>
        <p class="mt-3 alert alert-warning">Aucun enregistrement pour ce mois/annee</p>
   
   <% } else { %>
            <table class="table table-success table-striped mt-3">
                <tr>
                    <td>EmpNo</td>
                    <td>Date</td>
                    <td>Salaire Net</td>
                    <td>Voir Calcul</td>
                </tr>

        <% for(HistoSal emp : employees){ %>
                <tr>
                    <td><%= emp.getEmpNo() %></td>
                    <td><%= emp.getDateChange() %></td>

                    <% double cnaps =0,
                    salBrut1 = 0,
                    salBrut2 = 0,
                    valMin=0,
                    valMax=0,
                    valeurCnaps=0;
                    
                    List<HistoCombine> combines = HistoCombine.getHistoDep(emp.getEmpNo());
                    for(HistoCombine combine : combines) {

                        Rubrique r = combine.getRubrique();
                        Config c = combine.getConfig();
                        HistoSal hs = combine.getHistoSal();

                        if(r != null && r.getValeur() != null) {
                        String val = r.getValeur().trim();

                // BASE SALARY
                if(val.equalsIgnoreCase("BASE SALARY")) {
                    salBrut1 = hs.getSal();
                }
                //advantage
                if(val.equalsIgnoreCase("ADVANTAGE")) {
                    salBrut2 = hs.getSal();
                }

                // CNAPS
                if(val.equalsIgnoreCase("CNAPS") && c != null) {
                    cnaps = hs.getSal();
                    valMin = c.getMin();
                    valMax = c.getMax();
                    valeurCnaps = c.getValeur();
                        
                    }
                }
            }
            double salBrut = salBrut1 + salBrut2;
        double salNetSansIrsa = Calcul.calculSalaireDepense(salBrut, cnaps, valMin, valMax, valeurCnaps);
         double irsa = Calcul.calculIrsa(salNetSansIrsa);
        double salNet = Calcul.salaireFinale(salNetSansIrsa);
        double finale = Calcul.apoint(salNet,10);


                    %>
                    <td><%= finale %></td>
                    <td>
                    <form action="calculTable.jsp" method="post">
                        <input type="hidden" name="salBrut" value="<%= salBrut %>">
                        <input type="hidden" name="num" value="<%= emp.getEmpNo() %>">
                        <input type="hidden" name="cnaps" value="<%= cnaps %>">
                        <input type="hidden" name="irsa" value="<%= irsa %>">
                        <input type="hidden" name="salNet" value="<%= finale %>">
                        <input type="hidden" name="gain" value="<%= salBrut2 %>">
                        <input type="hidden" name="retenu" value="<%= salBrut1 %>">
                    
                        <button type="submit" class="btn btn-info btn-sm">Voir Calcul</button>
                
                </form>
                </td>
            </tr>
       <% } %>
                
            </table>
   <% }
   
   }%>
</div>
</body>
</html>