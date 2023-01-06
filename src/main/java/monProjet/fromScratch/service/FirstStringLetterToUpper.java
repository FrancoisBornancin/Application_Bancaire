package monProjet.fromScratch.service;

public class FirstStringLetterToUpper {
    public static String firstLetterToUpper(String name){
        String[] sTab = name.split("");
        String sMaj = sTab[0].toUpperCase();
        for(int a = 1 ; a < sTab.length ; a++){ sMaj += sTab[a]; }
        return sMaj;
    }
}
