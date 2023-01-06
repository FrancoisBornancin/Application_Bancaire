package monProjet.fromScratch.service.sql;

import monProjet.fromScratch.repository.GetMetaData;

import java.util.ArrayList;

public class PrepareInsertQuery {
    public static void insertIfNoError(String[] names, String[] values){
        StringBuilderSQL.setMessage("Le relevé a bien été saisi!");
        ArrayList<String> newValues = StringBuilderSQL.setListDepandOnTypeFiled(names, values);

        if(names.length == newValues.size()){
            String[] sTab = StringBuilderSQL.createStringForSQLInjection(names, newValues);
            GetMetaData.insertReleve(sTab);
        }
        else{ StringBuilderSQL.setMessage("Il y a une erreur de saisie!");}
    }
}
