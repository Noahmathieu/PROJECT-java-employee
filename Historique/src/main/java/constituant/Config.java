package constituant;

public class Config {
     int idRub;
     double min;
     double max;
     double valeur;


    public Config(int id ,double min ,double max ,double valeur) {
        this.idRub = id;
        this.min = min;
        this.max = max;
        this.valeur = valeur;
    }

    // --- Getters ---
    public int getIdRub() {
        return idRub;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getValeur() {
        return valeur;
    }
}
