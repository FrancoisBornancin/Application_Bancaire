package monProjet.fromScratch.service;

import org.springframework.stereotype.Service;

@Service
public class CountDown {
    private final static int timing = 10;
    private static int secondes = timing;

    public static void restart(){
        secondes = timing;
    }

    public static int getSecondes(){
        if(secondes == 0){ restart(); }
        return secondes;
    }

    public static void setSecondes(int secondes) { CountDown.secondes = secondes; }

    public static int getTiming(){ return timing; }

    public static String displaySecondes(){
        if(CountDown.secondes < 10){
            return "0" + CountDown.secondes;
        }
        else{ return "" + CountDown.secondes; }
    }

}
