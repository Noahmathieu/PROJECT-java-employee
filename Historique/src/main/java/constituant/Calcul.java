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

        //par trance en dollars
        double tranche1 = 50;
        double tranche2 = (salaireBrut - 1001)* 0.05;
        double tranche3 = (salaireBrut - 2001)* 0.1;
        double tranche4 = (salaireBrut - 3001)* 0.15;
        double tranche5 = (salaireBrut - 4001)* 0.2;
        double tranche6 = (salaireBrut - 5001)* 0.25;
        
        if (salaireBrut <= 1000) {
            irsa = tranche1;
        
        } else if (salaireBrut >= 1001 && salaireBrut <= 2000) {
            irsa = tranche2 + tranche1;
        
        } else if(salaireBrut >= 2001 && salaireBrut <= 3000) {
            irsa = tranche1 + tranche2 + tranche3;
        
        }else if (salaireBrut >= 3001 && salaireBrut <= 4000) {
            irsa = tranche1 + tranche2 + tranche3 + tranche4;
        
        } else if (salaireBrut >= 4001 && salaireBrut <= 5000) {
            irsa = tranche1 + tranche2 + tranche3 + tranche4 + tranche5;

        } else if (salaireBrut >= 5001) {
            irsa = tranche1 + tranche2 + tranche3 + tranche4 + tranche5 + tranche6;
        }
        return irsa;
    }
    public static double salaireFinale(double salaireNet) {
        double salFinal;
         salFinal =salaireNet - calculIrsa(salaireNet);
         return salFinal;
    }

}
