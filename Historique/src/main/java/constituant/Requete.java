package constituant;

import database.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class Requete {

    public static List<Employees> getEmployees() {
        List<Employees> employees = new ArrayList<>();
        String sql = "SELECT * FROM EMP";
        try (Connection conn = TestOracle.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employees emp = new Employees(
                        rs.getInt("EMPNO"),
                        rs.getString("ENAME"),
                        rs.getDate("HIREDATE"),
                        rs.getDouble("SAL"),
                        rs.getInt("DEPTNO"),
                        rs.getInt("MGR"),
                        rs.getString("JOB"),
                        rs.getInt("COMM"));
                employees.add(emp);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static List<Employees> getEmployeesbyId(String id) {
        List<Employees> employees = new ArrayList<>();
        String sql = "SELECT * FROM EMP WHERE EMPNO = " + id;
        try (Connection conn = TestOracle.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Employees emp = new Employees(
                        rs.getInt("EMPNO"),
                        rs.getString("ENAME"),
                        rs.getDate("HIREDATE"),
                        rs.getDouble("SAL"),
                        rs.getInt("DEPTNO"),
                        rs.getInt("MGR"),
                        rs.getString("JOB"),
                        rs.getInt("COMM"));
                employees.add(emp);
            }
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static void saveUpdate(String ename, Date hiredate, String sal, String id, String mgr, String job, String comm) {

        String sql = "UPDATE EMP SET ENAME = ?, HIREDATE = ?, SAL = ?, JOB = ?, MGR = ?, COMM = ? WHERE EMPNO = ?";
        try (Connection conn = TestOracle.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ename);
            pstmt.setDate(2, hiredate);
            pstmt.setDouble(3, Double.parseDouble(sal));
            pstmt.setInt(4, Integer.parseInt(id));
            pstmt.setString(5, job);
            pstmt.setInt(6, Integer.parseInt(mgr));
            pstmt.setInt(7, Integer.parseInt(comm));

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertHisto(String empno, String ename, String sal, Date date_change, String deptno) {
        String sql = "INSERT INTO HISTO VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = TestOracle.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
           
            pstmt.setInt(1, Integer.parseInt(empno));
            pstmt.setString(2, ename);
            pstmt.setDouble(3, Double.parseDouble(sal));
            pstmt.setDate(4, date_change);
            pstmt.setInt(5, Integer.parseInt(deptno));
           
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}