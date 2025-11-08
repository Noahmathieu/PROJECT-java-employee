package constituant;

public class Calcul {
    double salaireBrut;
    double cnaps;
    double irsa;


    public static double calculSalaireDepense(double salaireBrut, double cnaps,double valMin, double valMax, double val) {
        double salaireNet;
        salaireNet = salaireBrut - calculCnaps(salaireBrut,valMin,valMax,val);
        return salaireNet;

    }
    public static double calculCnaps(double salBrut,double valMin, double valMax, double val) {
        double cnaps;
        cnaps = salBrut * val;
        if (cnaps < valMin ) {
            cnaps = valMin;
        } else if(cnaps > valMax) {
            cnaps = valMax;
        }
        return cnaps;
    }


   public static double calculIrsa(double salaireBrut) {
    double irsa = 0;

    if (salaireBrut <= 400) {
        irsa = 15; 
    } 
    else if (salaireBrut <= 800) {
        irsa = 15 + (salaireBrut - 400) * 0.05;
    } 
    else if (salaireBrut <= 1200) {
        irsa = 15 + (400 * 0.05) + (salaireBrut - 800) * 0.10;
    } 
    else if (salaireBrut <= 1600) {
        irsa = 15 + (400 * 0.05) + (400 * 0.10) + (salaireBrut - 1200) * 0.15;
    } 
    else {
        irsa = 15 + (400 * 0.05) + (400 * 0.10) + (400 * 0.15) + (salaireBrut - 1600) * 0.20;
    }

    return irsa;
}



    public static double salaireFinale(double salaireNet) {
        double salFinal;
         salFinal =salaireNet - calculIrsa(salaireNet);
         return salFinal;
    }
    
    public static double apoint(double valeur, int plus) {
        int div = (int)(valeur / plus);

        if (valeur % plus != 0) {
            div += 1;
        }
    return div * plus;
    }
    

}
