<%@ page import="java.util.*, java.sql.Date, database.TestOracle, constituant.Requete" %>
<%@ page import="constituant.Employees" %>

<% 
    String nom = request.getParameter("nom");
    String daty = request.getParameter("date");
    String salaire = request.getParameter("salaire");
    String dept = request.getParameter("dept");
    String job = request.getParameter("job");
    int comm = request.getParameter("comm");
    int mgr = request.getParameter("mgr");



    //vaovao    
    String empno = request.getParameter("old_empno");
    String salInit = request.getParameter("salary");
    String old_name = request.getParameter("old_name");
    String old_date = request.getParameter("old_date");
    String old_dept = request.getParameter("old_dept");

    Date dat = Date.valueOf(daty);
    Date dateChange = Date.valueOf(old_date);

        if(salaire != salInit) {
            Requete.insertHisto(empno, old_name, salInit, dateChange, old_dept);
            Requete.saveUpdate(nom, dat, salaire, empno,mgr, job,comm);
        } else {
            Requete.saveUpdate(nom, dat, salaire, empno, mgr, job, comm);
        }

     

    response.sendRedirect("index.jsp");
%>