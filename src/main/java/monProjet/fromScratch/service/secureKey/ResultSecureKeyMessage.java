
package monProjet.fromScratch.service.secureKey;

import java.util.ArrayList;
import java.util.List;

public class ResultSecureKeyMessage {
    private static String restartApp;
    private static String message;
    public static String s;
    private static int count;
    public static ArrayList<String> listString = new ArrayList<>();

    public static int getCount() { return count; }
    public static void setCount(int thisCount){
        count = thisCount;
    }

    public static String getMessage() { return message; }

    public static void setMessage(String message) {
        if (message.equals("Le code n'est pas bon...") || message.equals("Vous avez mis trop de temps...")) {
            restartApp = "Essayez à nouveau";}
        else{ restartApp = "" ;}
        ResultSecureKeyMessage.message = message;
    }

    public static String getRestartApp(){ return restartApp; }

    public static List splitMessageAndFillList(String message){
        String[] sTab = message.split("");
        for(String tab : sTab){
            String newTab = "";
            if(!tab.equals(" ")){ newTab = "<div>" + tab + "</div>";}
            else{ newTab = "<div style=\"color: whitesmoke;\">-</div>";}
            listString.add(newTab);
        }

        return listString;
    }

    public static String constructSb(){
        count++;
        s = listString.get(listString.size() - 1);
        if(listString.size() != 1){
            String avantDernier = listString.get(listString.size() - 2) + listString.get(listString.size() - 1);
            listString.set(listString.size() - 2, avantDernier);
            listString.remove(listString.size() - 1);
        }
        return s;
    }
}

