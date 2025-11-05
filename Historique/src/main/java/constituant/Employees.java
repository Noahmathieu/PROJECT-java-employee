package constituant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.TestOracle;

public class Employees {
    int empno;
    String ename;
    Date hiredate;
    double sal;
    int deptno;
    int mgr;
    String job;
    int comm;

    public Employees(int empno, String ename, Date hiredate, double sal, int deptno, int mgr, String job, int comm) {
        this.empno = empno;
        this.ename = ename;
        this.hiredate = hiredate;
        this.sal = sal;
        this.deptno = deptno;
        this.mgr = mgr;
        this.job = job;
        this.comm = comm;
    }

    // --- Constructeur vide (nécessaire dans getEMP) ---
    public Employees() {}

    // --- Getters ---
    public int getEmpno() { return empno; }
    public String getEname() { return ename; }
    public Date getHiredate() { return hiredate; }
    public double getSal() { return sal; }
    public int getDeptno() { return deptno; }
    public int getMgr() { return mgr; }
    public String getJob() { return job; }
    public int getComm() { return comm; }

    // --- Setters ---
    public void setEmpno(int empno) { this.empno = empno; }
    public void setEname(String ename) { this.ename = ename; }
    public void setHiredate(Date hiredate) { this.hiredate = hiredate; }
    public void setSal(double sal) { this.sal = sal; }
    public void setDeptno(int deptno) { this.deptno = deptno; }
    public void setMgr(int mgr) { this.mgr = mgr; }
    public void setJob(String job) { this.job = job; }
    public void setComm(int comm) { this.comm = comm; }

    // ===================================================================
    // HISTO EMP (ok)
    // ===================================================================
    public static List<Employees> getHistoEmp(Date d) {
        List<Employees> list = new ArrayList<>();
        String sql = "SELECT * FROM HISTO h1 WHERE h1.DATE_CHANGE = (SELECT MIN(h2.DATE_CHANGE) FROM HISTO h2 WHERE h2.DATE_CHANGE > ?)";
        try (Connection conn = TestOracle.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, d);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employees emp = new Employees(
                            rs.getInt("EMPNO"),
                            rs.getString("ENAME"),
                            rs.getDate("DATE_CHANGE"),
                            rs.getDouble("SAL"),
                            rs.getInt("DEPTNO"),
                            rs.getInt("MGR"),
                            rs.getString("JOB"),
                            rs.getInt("COMM")
                    );
                    list.add(emp);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===================================================================
    // FIND EMPLOYEES (ok)
    // ===================================================================
    public static List<Employees> getFindEmployees(Date d) {
        List<Employees> list = new ArrayList<>();
        String sql = "SELECT * FROM EMP WHERE HIREDATE = (SELECT MIN(HIREDATE) FROM EMP WHERE HIREDATE > ?)";
        try (Connection conn = TestOracle.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, d);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Employees emp = new Employees(
                            rs.getInt("EMPNO"),
                            rs.getString("ENAME"),
                            rs.getDate("HIREDATE"),
                            rs.getDouble("SAL"),
                            rs.getInt("DEPTNO"),
                            rs.getInt("MGR"),
                            rs.getString("JOB"),
                            rs.getInt("COMM")
                    );
                    list.add(emp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ===================================================================
    // FONCTION PRINCIPALE getEMP — version corrigée
    // ===================================================================
    public static List<Employees> getEMP(Date d) {
        List<Employees> empList = new ArrayList<>();
        List<Employees> filteredList = new ArrayList<>();

        // Tous les employés actuels
        String allEmpSql = "SELECT * FROM EMP";

        // Données dans HISTO avec la date la plus proche > d
        String filterSql = "SELECT * FROM HISTO h1 WHERE h1.DATE_CHANGE = (SELECT MIN(h2.DATE_CHANGE) FROM HISTO h2 WHERE h2.DATE_CHANGE > ?)";

        try (Connection conn = TestOracle.getConnection()) {

            // --- Récupérer tous les employés ---
            try (PreparedStatement pstmt = conn.prepareStatement(allEmpSql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    empList.add(new Employees(
                            rs.getInt("EMPNO"),
                            rs.getString("ENAME"),
                            rs.getDate("HIREDATE"),
                            rs.getDouble("SAL"),
                            rs.getInt("DEPTNO"),
                            rs.getInt("MGR"),
                            rs.getString("JOB"),
                            rs.getInt("COMM")
                    ));
                }
            }

            // --- Récupérer les historiques supérieurs à la date donnée ---
            try (PreparedStatement pstmt = conn.prepareStatement(filterSql)) {
                pstmt.setDate(1, d);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        filteredList.add(new Employees(
                                rs.getInt("EMPNO"),
                                rs.getString("ENAME"),
                                rs.getDate("DATE_CHANGE"),
                                rs.getDouble("SAL"),
                                rs.getInt("DEPTNO"),
                                rs.getInt("MGR"),
                                rs.getString("JOB"),
                                rs.getInt("COMM")
                        ));
                    }
                }
            }

            // --- Construire la liste finale ---
            List<Employees> result = new ArrayList<>();
            boolean found = false;

            for (Employees e : empList) {
                Employees next = new Employees();
                for (Employees f : filteredList) {
                    if (e.getEmpno() == f.getEmpno()) {
                        next = f;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    next = new Employees(
                            e.getEmpno(),
                            e.getEname(),
                            d,
                            e.getSal(),
                            e.getDeptno(),
                            e.getMgr(),
                            e.getJob(),
                            e.getComm()
                    );
                }
                result.add(next);
                found = false;
            }

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
