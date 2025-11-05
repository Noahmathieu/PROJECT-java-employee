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
        int moisNext = (mois == 12) ? 1 : mois + 1;
        int anneeNext = (mois == 12) ? annee + 1 : annee;

        String sql = "SELECT EMPNO, MAX(SAL) AS SAL, MAX(DATE_CHANGE) AS DATE_CHANGE, MAX(IDRUBRIQUE) AS IDRUBRIQUE " +
                "FROM HISTOSAL " +
                "WHERE DATE_CHANGE >= TO_DATE(?, 'DD-MM-YYYY') " +
                "AND DATE_CHANGE < TO_DATE(?, 'DD-MM-YYYY') " +
                "GROUP BY EMPNO";

        try (Connection conn = TestOracle.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "01-" + mois + "-" + annee);
            pstmt.setString(2, "01-" + moisNext + "-" + anneeNext);

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

    // Récupère les historiques combinés avec Rubrique et Config pour un employé
    // donné
    public static List<HistoCombine> getHistosalById(int empId) {
        List<HistoCombine> list = new ArrayList<>();

        String sql = "SELECT H.EMPNO, H.SAL, H.DATE_CHANGE, H.IDRUBRIQUE, " +
                "R.ID AS R_ID, R.VALEUR AS R_VALEUR, R.TYPERUB, R.MODE, " +
                "C.ID_RUB AS C_ID_RUB, C.MIN, C.MAX, C.VALEUR AS C_VALEUR " +
                "FROM HISTOSAL H " +
                "LEFT JOIN RUBRIQUE R ON H.IDRUBRIQUE = R.ID " +
                "LEFT JOIN CONFIG C ON R.ID = C.ID_RUB " +
                "WHERE H.EMPNO = ?";

        try (Connection conn = TestOracle.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    HistoSal h = new HistoSal(
                            rs.getInt("EMPNO"),
                            rs.getDouble("SAL"),
                            rs.getDate("DATE_CHANGE"),
                            rs.getInt("IDRUBRIQUE"));

                    Rubrique r = new Rubrique(
                            rs.getInt("R_ID"),
                            rs.getString("R_VALEUR"),
                            rs.getString("TYPERUB") != null ? rs.getString("TYPERUB").charAt(0) : ' ',
                            rs.getString("MODE"));

                    Config c = new Config(
                            rs.getInt("C_ID_RUB"),
                            rs.getDouble("MIN"),
                            rs.getDouble("MAX"),
                            rs.getDouble("C_VALEUR"));

                    list.add(new HistoCombine(h, r, c));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
