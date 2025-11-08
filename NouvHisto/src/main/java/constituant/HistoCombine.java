package constituant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import database.TestOracle;

public class HistoCombine {

    private HistoSal hist;
    private Rubrique rub;
    private Config con;

    public HistoCombine(HistoSal hist, Rubrique rub, Config con) {
        this.hist = hist;
        this.rub = rub;
        this.con = con;
    }
    

    // --- Getters ---
    public HistoSal getHistoSal() { return hist; }
    public Rubrique getRubrique() { return rub; }
    public Config getConfig() { return con; }

    // 🔥 Nouvelle fonction : jointure entre HISTOSAL, RUBRIQUE et CONFIG
   public static List<HistoCombine> getAllHistoCombine() {
    List<HistoCombine> list = new ArrayList<>();

    String sql =
        "SELECT " +
        "    H.EMPNO, H.SAL, H.DATE_CHANGE, H.IDRUBRIQUE, " +
        "    R.ID AS R_ID, R.VALEUR AS R_VALEUR, R.TYPERUB, R.MOD AS R_MODE, " +  // <-- sans guillemets si la colonne est MOD
        "    C.ID_RUB AS C_ID_RUB, C.MIN AS C_MIN, C.MAX AS C_MAX, C.VALEUR AS C_VALEUR " +
        "FROM HISTOSAL H " +
        "LEFT JOIN RUBRIQUE R ON H.IDRUBRIQUE = R.ID " +
        "LEFT JOIN CONFIG C ON R.ID = C.ID_RUB " +
        " WHERE ORDER BY H.EMPNO, H.DATE_CHANGE DESC";

    try (Connection conn = TestOracle.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            HistoSal h = new HistoSal(
                rs.getInt("EMPNO"),
                rs.getDouble("SAL"),
                rs.getDate("DATE_CHANGE"),
                rs.getInt("IDRUBRIQUE")
            );

            Rubrique r = new Rubrique(
                rs.getInt("R_ID"),
                rs.getString("R_VALEUR"),
                rs.getString("TYPERUB") != null && !rs.getString("TYPERUB").isEmpty()
                    ? rs.getString("TYPERUB").charAt(0)
                    : ' ', // <-- éviter null char
                rs.getString("R_MODE")
            );

            Config c = new Config(
                rs.getInt("C_ID_RUB"),
                rs.getDouble("C_MIN"),
                rs.getDouble("C_MAX"),
                rs.getDouble("C_VALEUR")
            );

            list.add(new HistoCombine(h, r, c));
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}

public static List<HistoCombine> getHistoDep(int id) {
    List<HistoCombine> list = new ArrayList<>();

    String sql = "SELECT " + 
                 " H.EMPNO, H.SAL, H.DATE_CHANGE, H.IDRUBRIQUE, " + 
                 " R.ID AS R_ID, R.VALEUR AS R_VALEUR, R.TYPERUB, R.MOD AS R_MOD, " + 
                 " C.ID_RUB AS C_ID_RUB, C.MIN AS C_MIN, C.MAX AS C_MAX, C.VALEUR AS C_VALEUR " + 
                 "FROM HISTOSAL H " + 
                 "LEFT JOIN RUBRIQUE R ON H.IDRUBRIQUE = R.ID " + 
                 "LEFT JOIN CONFIG C ON R.ID = C.ID_RUB " + 
                 "WHERE H.EMPNO = ? " +  // <-- filtre l'employé
                 "ORDER BY H.DATE_CHANGE DESC"; // tri par date, pas besoin d'EMPNO ici

    try (Connection conn = TestOracle.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, id); // Liaison correcte

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                HistoSal h = new HistoSal(
                    rs.getInt("EMPNO"),
                    rs.getDouble("SAL"),
                    rs.getDate("DATE_CHANGE"),
                    rs.getInt("IDRUBRIQUE")
                );

                Rubrique r = new Rubrique(
                    rs.getInt("R_ID"),
                    rs.getString("R_VALEUR"),
                    rs.getString("TYPERUB") != null && !rs.getString("TYPERUB").isEmpty()
                        ? rs.getString("TYPERUB").charAt(0)
                        : ' ',
                    rs.getString("R_MOD")
                );

                Config c = new Config(
                    rs.getInt("C_ID_RUB"),
                    rs.getDouble("C_MIN"),
                    rs.getDouble("C_MAX"),
                    rs.getDouble("C_VALEUR")
                );

                list.add(new HistoCombine(h, r, c));
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return list;
}

}
