package monProjet.fromScratch.service;

import monProjet.fromScratch.repository.GetMetaData;
import monProjet.fromScratch.service.form.Form;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashSet;

public class BuildModel {
    public static ArrayList<ArrayList> listValues = GetMetaData.getValues();
    public static int startAndEnd;

    public static void setStartAndEnd(String userRole){
        if(userRole.equals("admin")){ startAndEnd = 0 ;}
        else{ startAndEnd = 1; }
    }


    public static String displaySelect_options(String userRole, int id){
        setStartAndEnd(userRole);
        ArrayList<String> listNames = GetMetaData.resetName();
        ArrayList<ArrayList> uniques = GetMetaData.getValues();
        ArrayList<ArrayList> values = GetMetaData.getValues();

        if(!userRole.equals("admin")){
            String user_id = Integer.toString(id);
            values = createNewList("user_id", user_id);}

        ArrayList<ArrayList> listUniques = getUniques(uniques, values);

        String wrapperString = "";

        for(int a = startAndEnd ; a < listNames.size() - startAndEnd ; a++){
            String stringOptions = "";
            for(int b  = 0 ; b < listUniques.get(a).size() ; b++){
                stringOptions += "<option>" + listUniques.get(a).get(b) + "</option>";
            }

            String sMaj = FirstStringLetterToUpper.firstLetterToUpper(listNames.get(a));

            wrapperString += "\t\n<div class=\"button-select\" >" +
                    "\n\t\t<select class=\"form-select\" >" +
                    "\n\t\t" + stringOptions +
                    "\n\t</select>" +
                    "\n\t<input type = \"checkbox\" class=\"form-check-input\" >" +
                    "<span style=\"display: none;\" >" + listNames.get(a) + "</span> " + sMaj +
                    "</div>";
        }

        wrapperString +="\n\t<button onclick=\"checkElements()\" class=\"btn btn-outline-info\" >Filtrer</button>";

        return wrapperString;
    }

    public static int displayCount(String userRole){
        setStartAndEnd(userRole);
        ArrayList<String> listNames = GetMetaData.resetName();
        int count = 0;
        for(int a = startAndEnd ; a < listNames.size() - startAndEnd ; a++){
            count++;
        }
        return count;
    }

    public static int getFieldIndex(String name){
        int index = 0;
        ArrayList<String> listNames = GetMetaData.resetName();
        for(int a = 0 ; a < listNames.size() ; a++){
            if(listNames.get(a).equals(name)){
                index = a;
            }
        }
        return index;
    }

    public static ArrayList<Integer> getValueIndex(ArrayList<ArrayList> listValues, String name, String value){
        int index = getFieldIndex(name);
        ArrayList<Integer> listIndex = new ArrayList<>();

        for(int a = 0 ; a < listValues.get(index).size() ; a++){
            try{
                Integer intValue = Integer.valueOf(value);
                if(listValues.get(index).get(a) != intValue){
                    listIndex.add(a);
                }
            }catch (Exception e){
                if(!listValues.get(index).get(a).equals(value)){
                    listIndex.add(a);
                }
            }
        }
        return listIndex;
    }

    public static ArrayList<ArrayList> createNewList(String name, String value){
        ArrayList<Integer> listIndex = getValueIndex(listValues, name, value);
        int size = listIndex.size();
        for(int a = 0 ; a < size ; a++){
            int index = listIndex.get(listIndex.size() - 1);
            for(int b = 0 ; b < listValues.size() ; b++){
                listValues.get(b).remove(index);
            }
            listIndex.remove(listIndex.size() - 1);
        }
        return listValues;
    }

    public static String displayListReleves(ArrayList<ArrayList> values, String userRole, int id){
        ArrayList<String> listNames = GetMetaData.resetName();
        setStartAndEnd(userRole);
        String s = "";
        if(!userRole.equals("admin")){
            String user_id = Integer.toString(id);
            values = createNewList("user_id", user_id);}

        s += "<table>";

        s += "<tr class=\"upper-tr\" >";
        for(int a = startAndEnd ; a < listNames.size() - startAndEnd ; a++){
            String sMaj = FirstStringLetterToUpper.firstLetterToUpper(listNames.get(a));
            s += "<td class=\"inner-td\">" + sMaj + "</td>";
        }

        s += "</tr>";
        for(int a = 0 ; a < values.get(0).size() ; a++){
            s += "<tr class=\"inner-tr\" >";
            for(int b = startAndEnd ; b < listNames.size() - startAndEnd; b++){ s += "<td class=\"inner-td\" >" + values.get(b).get(a) + "</td>"; }
            if(userRole.equals("admin")){
                s += "<td><a href=\"/crudReleveRoute/" + values.get(0).get(a) + "\" class=\"btn btn-outline-secondary\" >Editer</a></td>";
                s += "<td><button onclick=\"deleteReleve(" + values.get(0).get(a) + ")\" class=\"btn btn-outline-danger\" >Supprimer</button></td>";
            }
            s += "</tr>";
        }

        s += "</table>";

        return s;
    }

    public static ArrayList<ArrayList> getNewLists(String[] names, String[] values){
        listValues = GetMetaData.getValues();
        for(int a = 0 ; a < names.length ; a++){
            listValues = BuildModel.createNewList(names[a], values[a]);
        }
        return listValues;
    }

    public static String displayInsertReleve(String userRole){
        setStartAndEnd(userRole);
        ArrayList<String> listNames = GetMetaData.resetName();

        String s = "";
        for(int a = 1 ; a < listNames.size() ; a++){
            String sMaj = FirstStringLetterToUpper.firstLetterToUpper(listNames.get(a));
            s += "<div></div>";
            s += "<span style=\"display: none;\" >" + listNames.get(a) + "</span> " + sMaj + ": " +
                    "<input type = text class=\"form-control\" />";
        }
        s += "<button onclick=\"sendReleve()\" class=\"btn btn-outline-info\" >Envoyer</button>";
        return s;
    }

    public static String displayDeleteChamp(){
        ArrayList<String> listNames = GetMetaData.resetName();

        String s = "";
        for(int a = 1 ; a < listNames.size() - 1 ; a++){
            s += "<option th:value=\"" + listNames.get(a) + "\" >" + listNames.get(a) + "</option>";
        }

        return s;
    }

    public static ArrayList<ArrayList> deleteReleve(int id){
        GetMetaData.deleteReleve(id);
        for(int a = 0 ; a < listValues.get(0).size() ; a++){
            Integer b = (Integer) listValues.get(0).get(a);
            if(b == id){
                int index = a;
                for(int c = 0 ; c < listValues.size() ; c++){
                    listValues.get(c).remove(index);
                }
            }
        }
        return listValues;
    }

    public static String displayUpdateReleve(int id){
        ArrayList<String> listNames = GetMetaData.resetName();
        ArrayList<ArrayList> listValues = GetMetaData.getValues();

        String s = "";

        s += "<h3>Relevé n°" + id + ": Modifier la valeur du champ voulu: </h3>";

        for(int a = 1 ; a < listValues.size() ; a++){
            String sMaj = FirstStringLetterToUpper.firstLetterToUpper(listNames.get(a));
            s += "<span style=\"display: none;\" >" + listNames.get(a) + "</span>" + sMaj + ": <input class=\"form-control\" type=\"text\" value = ";
            for(int b = 0 ; b < listValues.get(0).size() ; b++){
                Integer c = (Integer) listValues.get(0).get(b);
                if(c == id){
                    s += listValues.get(a).get(b);
                }
            }
            s += " /><button class=\"btn btn-outline-info\" onclick=\"updateReleve(" + a + ", " + id + ")\">Envoyer</button> </div>";
            s += "<div id=\"" + listNames.get(a) + "Message\" ></div>";
            s += "<div></div>";
        }

        return s;
    }

    public static ArrayList<ArrayList> getUniques(ArrayList<ArrayList> uniques, ArrayList<ArrayList> values){
        ArrayList<ArrayList> listUniques = new ArrayList<>(uniques);
        for(int a = 0 ; a < listUniques.size() ; a++){
            listUniques.get(a).clear();
        }

        ArrayList<HashSet> listHashs = new ArrayList<>();
        for(int a = 0 ; a < listUniques.size() ; a++){
            if(GetMetaData.SQLFieldTypes.get(a).equals("int")){
                HashSet<Integer> listInt = new HashSet<>();
                for(int b = 0 ; b < values.get(a).size() ; b++){
                    Integer c = (Integer) values.get(a).get(b);
                    listInt.add(c);
                }
                listHashs.add(listInt);
            }else{
                HashSet<String> listString = new HashSet<>();
                for(int b = 0 ; b < values.get(a).size() ; b++){
                    String c = (String) values.get(a).get(b);
                    listString.add(c);
                }
                listHashs.add(listString);
            }
        }

        for(int a = 0 ; a < listUniques.size() ; a++){
            for(Object element : listHashs.get(a)){
                listUniques.get(a).add(element);
            }
        }
        return listUniques;
    }

    public static void displayCrudReleveRoute(String header, String body, Model model){
        model.addAttribute("cheminHeader", "fragments/crudReleve/header/" + header);
        model.addAttribute("fragmentHeader", "elements");
        model.addAttribute("cheminBody", "fragments/crudReleve/body/" + body);
        model.addAttribute("fragmentBody", "releve");
    }

    public static void displayForm(Form form, Model model, String name){
        model.addAttribute(name, form);
    }

    public static void displayUser(Model model, String prenom, String role){
        model.addAttribute("prenom", prenom);
        model.addAttribute("role", role);
    }

    public static String displayButtonAdmin(String user_role){
        String s = "";

        if(user_role.equals("admin")){
            s += "<a href=\"/crudReleveRoute\" class=\"btn btn-outline-success\" >Créer Nouveau Champ/Relevé</a>";
        }

        return s;
    }

}
