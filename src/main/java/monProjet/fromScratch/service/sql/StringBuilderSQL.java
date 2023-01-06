package monProjet.fromScratch.service.sql;

import monProjet.fromScratch.repository.GetMetaData;
import org.springframework.web.filter.GenericFilterBean;

import java.util.ArrayList;

public class StringBuilderSQL { 

	private static String message;

	public static void setMessage(String createMessage){ message = createMessage; }
	public static String getMessage(){ return message; }

	public static ArrayList<String> setListDepandOnTypeFiled(String[] names, String[] values){
		ArrayList<String> newValues = new ArrayList();
		ArrayList<String> listTypes = GetMetaData.resetType();
		ArrayList<String> listNames = GetMetaData.resetName();
		for(int a = 0 ; a < names.length ; a++){
			for(int b = 0 ; b < listNames.size() ; b++){
				if(names[a].equals(listNames.get(b))){
					if(listTypes.get(b).equals("int")){ // Tester si on peut le convertir en Int
						try{
							Integer intValue = Integer.valueOf(values[a]);
							newValues.add(values[a]);
						}catch(Exception e){}
					}else{ // Tester si on peut le convertir en String
						try{ Integer intValue = Integer.valueOf(values[a]);}
						catch (Exception e){ if(values[a].length() != 0){ newValues.add("'" + values[a] + "'");} }
					}
				}
			}
		}
		return newValues;
	}
	public static String[] createStringForSQLInjection(String[] names, ArrayList<String> values){
		String sNames = "";
		String sValues = "";
		for(int a = 0 ; a < names.length - 1 ; a++){
			sNames += names[a] + ", ";
		}
		sNames += names[names.length - 1];

		for(int a = 0 ; a < values.size() - 1 ; a++){
			sValues += values.get(a) + ", ";
		}
		sValues += values.get(values.size() - 1);
		String[] sTab = {sNames , sValues};
		return sTab;
	}
}
