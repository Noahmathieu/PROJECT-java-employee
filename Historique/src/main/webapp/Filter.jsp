    <%@ page import="java.util.*, java.sql.Date, database.TestOracle, constituant.Requete" %>
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
    
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-header bg-white">
                        <h2 class="h4 text-center mb-0">Filtre Employees</h2>
                    </div>
                    <div class="card-body">

    <form action="Filter.jsp" class="row g-3" method="post">


<select class="form-select"  name="fil" aria-label="Default select example">
  <option value="1">Employees</option>
  <option value="2">HistoEmployees</option>
</select>


  <div class="col-md-4">
    <label for="validationDefault01" class="form-label">Date Choosing</label>
    <input type="date" class="form-control" name="dat" required>
     </div>


      <div class="col-12">
    <button class="btn btn-primary" type="submit">Search</button>

  </div>
    </form>
     </div>
                </div>
            </div>
        </div>
    </div>

  <% 
    if(request.getParameter("dat") != null) {

        String daty = request.getParameter("dat");
         Date dateChoisie = Date.valueOf(daty);
        String filter = request.getParameter("fil");
if(filter.equals("1") ) {
    List<Employees> emp = Employees.getEMP(dateChoisie);
    if (emp.isEmpty()) {
%>
        <p>Aucun employé trouvé pour la date choisie.</p>
<% 
    } else { 
%>         
        <table class="table table-primary ">
        <tr>
            <td>Id</td>
            <td>Nom</td>
            <td>Salaire</td>
            <td>date changee</td>
        </tr>
        <% for(Employees liste : emp) { %>
        <tr class="table-active">
            <td><%= liste.getEmpno() %></td>
            <td><%= liste.getEname() %></td>
            <td><%= liste.getSal() %></td>
            <td><%= liste.getHiredate() %></td>
        </tr>
        <% } %>
        </table>   
<% 
    }
}
%>
        
        <% if (filter.equals("2")) {
            List<Employees> emp = Employees.getHistoEmp(dateChoisie); %>
             <p>Historique des modifications avant la date: <%= dateChoisie %></p>
         <table class="table table-primary ">
        
        <tr>
            <td>Id</td>
            <td>Nom</td>
            <td>Salaire</td>
            <td>date changee</td>
        </tr>
        <% for(Employees liste : emp) { %>
        <tr class="table-active">
            <td><%= liste.getEmpno() %></td>
            <td><%= liste.getEname() %></td>
            <td><%= liste.getSal() %></td>
            <td><%= liste.getHiredate() %></td>
        </tr>
        <% } %>

    </table>
       <%  } %>
        
   <% } %>

</body>
</html>