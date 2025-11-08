package constituant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.TestOracle;

public class HistoSal {
    private int empNo;
    private double sal;
    private Date dateChange;
    private int idRubrique;

    public HistoSal(int empNo, double sal, Date dateChange, int idRubrique) {
        this.empNo = empNo;
        this.sal = sal;
        this.dateChange = dateChange;
        this.idRubrique = idRubrique;
    }

    public int getEmpNo() {
        return empNo;
    }

    public double getSal() {
        return sal;
    }

    public Date getDateChange() {
        return dateChange;
    }

    public int getIdRubrique() {
        return idRubrique;
    }

    // Récupère tous les historiques
    public static List<HistoSal> getHistosal() {
        List<HistoSal> employees = new ArrayList<>();
        String sql = "SELECT * FROM HISTOSAL";

        try (Connection conn = TestOracle.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                employees.add(new HistoSal(
                        rs.getInt("EMPNO"),
                        rs.getDouble("SAL"),
                        rs.getDate("DATE_CHANGE"),
                        rs.getInt("IDRUBRIQUE")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    // Récupère les historiques pour un mois et une année donnée
    public static List<HistoSal> getHistoSalByDate(int mois, int annee) {
        List<HistoSal> employees = new ArrayList<>();
 

        String sql = "SELECT EMPNO, MAX(SAL) AS SAL, MAX(DATE_CHANGE) AS DATE_CHANGE, MAX(IDRUBRIQUE) AS IDRUBRIQUE " +
                "FROM HISTOSAL " +
                "WHERE DATE_CHANGE >= TO_DATE(?, 'DD-MM-YYYY') " +
                "GROUP BY EMPNO";

        try (Connection conn = TestOracle.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "01-" + mois + "-" + annee);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    employees.add(new HistoSal(
                            rs.getInt("EMPNO"),
                            rs.getDouble("SAL"),
                            rs.getDate("DATE_CHANGE"),
                            rs.getInt("IDRUBRIQUE")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employees;
    }

    public static String getNameDate(int mois, int annee) {
        String name = null;    
        
        String sql = "SELECT TRIM(TO_CHAR(TO_DATE(?,'DD-MM-YYYY'), 'DAY')) AS nom_jour FROM dual;";

        try (Connection conn = TestOracle.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "01-" + mois + "-" + annee);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                        name = rs.getString("nom_jour");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    return name;
    }

}
