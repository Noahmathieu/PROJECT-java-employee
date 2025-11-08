package constituant;

public class Rubrique {
     int id;
     String valeur;
     char typeRub;
     String mode;

    public Rubrique(int id, String valeur,char typeRub ,String mode) {
        this.id = id;
        this.valeur = valeur;
        this.typeRub = typeRub;
        this.mode = mode;

    }

    // --- Getters ---
    public int getId() {
        return id;
    }

    public String getValeur() {
        return valeur;
    }

    public char getTypeRub() {
        return typeRub;
    }

    public String getMode() {
        return mode;
    }
}