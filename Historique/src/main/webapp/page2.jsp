<%@ page import="java.util.*, java.sql.Date, database.TestOracle, constituant.Requete" %>
<%@ page import="constituant.Employees" %>

<% String val = request.getParameter("employees");
    List<Employees> liste = Requete.getEmployeesbyId(val);
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
    <table class="table table-primary ">
        <tr>
            <td>Id</td>
            <td>Nom</td>
            <td>Salaire</td>
            <td>Department</td>
            <td>Mgr</td>
            <td>Job</td>
            <td>Comm</td>
        </tr>
        <% for(Employees emp : liste) { %>
        <tr class="table-active">
            <td><%= emp.getEmpno() %></td>
            <td><%= emp.getEname() %></td>
            <td><%= emp.getSal() %></td>
            <td><%= emp.getDeptno() %></td>
            <td><%= emp.getMgr() %></td>
            <td><%= emp.getJob() %></td>
            <td><%= emp.getComm() %></td>

        </tr>
        <% } %>
    </table>
    <form action="treat.jsp" method="post" >
        <div class="mb-3">
          <label for="exampleFormControlInput1" class="form-label">Nom</label>
            <input type="text" name="nom" class="form-control" placeholder="Entrer nom">
        </div>
         <input type="hidden" name="old_empno" value="<%= liste.get(0).getEmpno() %>">
         <input type="hidden" name="salary" value="<%= liste.get(0).getSal() %>">
         <input type="hidden" name="old_name" value="<%= liste.get(0).getEname() %>">
         <input type="hidden" name="old_date" value="<%= liste.get(0).getHiredate() %>">
         <input type="hidden" name="old_dept" value="<%= liste.get(0).getDeptno() %>">
        <div class="mb-3">
          <label for="exampleFormControlInput1" class="form-label">Date</label>
            <input type="date" name="date" class="form-control" placeholder="Entrer nom">
        </div>
        <div class="mb-3">
          <label for="exampleFormControlInput1" class="form-label">Salaire</label>
            <input type="number" name="salaire" class="form-control" placeholder="Entrer Salaire">
        </div>

         <label for="exampleFormControlInput1" class="form-label">Department</label>
            <input type="number" name="dept" class="form-control" placeholder="Entrer departement">
        </div>

         <label for="exampleFormControlInput1" class="form-label">Job</label>
            <input type="text" name="job" class="form-control" placeholder="Entrer departement">
        </div>

         <label for="exampleFormControlInput1" class="form-label">MGR</label>
            <input type="number" name="mgr" class="form-control" placeholder="Entrer departement">
        </div>

         <label for="exampleFormControlInput1" class="form-label">Comm</label>
            <input type="number" name="comm" class="form-control" placeholder="Entrer departement">
        </div>

        

        <input class="btn btn-primary" type="submit" value="Modifier">
    </form>
</body>
</html>