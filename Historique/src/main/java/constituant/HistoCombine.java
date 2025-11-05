package constituant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.TestOracle;

public class HistoCombine {
    HistoSal hist;
    Rubrique rub;
    Config con;

    public HistoCombine(HistoSal hist, Rubrique rub, Config con) {
        this.con = con;
        this.rub = rub;
        this.hist = hist;

    }

     public HistoSal getHistoSal() {
        return hist;
    }

    public Rubrique getRubrique() {
        return rub;
    }

    public Config getConfig() {
        return con;
    }
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
