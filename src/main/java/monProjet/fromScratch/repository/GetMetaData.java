package monProjet.fromScratch.repository;

import monProjet.fromScratch.config.DataConfig;
import monProjet.fromScratch.service.form.LoginForm;
import monProjet.fromScratch.service.sql.StringBuilderSQL;
import monProjet.fromScratch.service.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class GetMetaData {
    private static ApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class);
    private static DataSource ds = context.getBean(DataSource.class);
    private static Connection conn = null;

    private static String mailMessage;
    private static String mdpMessage;

    public static ArrayList<String> SQLFieldNames = new ArrayList<>();
    public static ArrayList<String> SQLFieldTypes = new ArrayList<>();

    public static String getMailMessage(){
        if(mailMessage == null){ mailMessage = "" ;}
        return mailMessage;
    }

    public static void setMailMessage(String mailMessage) { GetMetaData.mailMessage = mailMessage; }

    public static String getMdpMessage() {
        if(mdpMessage == null){ mdpMessage = "" ;}
        return mdpMessage;
    }

    public static void setMdpMessage(String mdpMessage){ GetMetaData.mdpMessage = mdpMessage; }

    public static void goSecureKeyRoute(LoginForm form){
        try{
            setMailMessage(""); setMdpMessage("");
            conn = ds.getConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("SELECT user_email FROM users " +
                    "WHERE user_email = '" + form.getEmail() + "'");
            if(rs1.next() == false){
                mailMessage = "Votre email n'est pas correct";
                mdpMessage = "";
            }
            else{
                ResultSet rs2 = conn.createStatement().executeQuery("SELECT user_mdp FROM users " +
                        "WHERE user_email = '" + form.getEmail() + "'");
                while(rs2.next()){
                    if(rs2.getString("user_mdp").equals(form.getMdp())){
                        ResultSet rs3 = conn.createStatement().executeQuery("SELECT * FROM users " +
                                "WHERE user_email = '" + form.getEmail() + "'");
                        while(rs3.next()){
                            User.setPrenom(rs3.getString("user_prenom"));
                            User.setRole(rs3.getString("user_role"));
                            User.setId(rs3.getInt("user_id"));
                        }
                    }else {
                        mailMessage = "";
                        mdpMessage = "Votre mdp n'est pas correct" ;
                    }
                }
            }
        }catch(Exception e){ e.printStackTrace(); }
        finally {
            try{ conn.close(); }catch(SQLException e){  }
        }
    }

    public static void initName(){
        try{
            conn = ds.getConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("SELECT * FROM releve");
            ResultSetMetaData rsmd = rs1.getMetaData();
            int columnsCount = rsmd.getColumnCount();
            for(int a = 1 ; a < columnsCount + 1 ; a++){
                String name = rsmd.getColumnName(a);
                SQLFieldNames.add(name);
            }
        }
        catch (Exception e){ e.printStackTrace();}
        finally {
            try{ conn.close(); }catch(SQLException e){  }
        }
    }

    public static void initTypes(){
        try{
            conn = ds.getConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("SELECT * FROM releve");
            ResultSetMetaData rsmd = rs1.getMetaData();
            int columnsCount = rsmd.getColumnCount();
            for(int a = 1 ; a < columnsCount + 1 ; a++){
                if(rsmd.getColumnTypeName(a).equals("INT")){ SQLFieldTypes.add("int");} else{ SQLFieldTypes.add("String");}
            }
        }
        catch (Exception e){ e.printStackTrace();}
        finally {
            try{ conn.close(); }catch(SQLException e){  }
        }
    }

    public static void clearName(){ SQLFieldNames.clear(); }
    public static void clearType(){ SQLFieldTypes.clear(); }

    public static ArrayList<String> resetName(){
        clearName(); initName();
        return SQLFieldNames;
    }
    public static ArrayList<String> resetType(){
        clearType();
        initTypes();
        return SQLFieldTypes;
    }

    public static void resetNameAndTypesList(){ resetName(); resetType(); }

    public static ArrayList<ArrayList> getValues(){
        resetNameAndTypesList();
        ArrayList<ArrayList> SQLMetaDataValues = new ArrayList<>();
        try{
            conn = ds.getConnection();
            ResultSet rs1 = conn.createStatement().executeQuery("SELECT * FROM releve");
            ResultSetMetaData rsmd = rs1.getMetaData();
            int columnsCount = rsmd.getColumnCount();
            for(int a = 1 ; a < columnsCount + 1 ; a++){
                if(SQLFieldTypes.get(a - 1).equals("int")){
                    ArrayList<Integer> listInt = new ArrayList<>();
                    SQLMetaDataValues.add(listInt);
                }else{
                    ArrayList<String> listString = new ArrayList<>();
                    SQLMetaDataValues.add(listString);
                }
            }
            while(rs1.next()){
                for(int a = 1 ; a < columnsCount + 1 ; a++){
                    if(SQLFieldTypes.get(a - 1).equals("int")){
                        SQLMetaDataValues.get(a - 1).add(rs1.getInt(SQLFieldNames.get(a - 1)));
                    }else{ SQLMetaDataValues.get(a - 1).add(rs1.getString(SQLFieldNames.get(a - 1))); }
                }
            }
        }
        catch (Exception e){ e.printStackTrace();}
        finally {
            try{ conn.close(); }catch(SQLException e){  }
            return SQLMetaDataValues;
        }
    }

    public static void deleteReleve(int id){
        try{
            conn = ds.getConnection();
            conn.createStatement().execute("DELETE FROM releve WHERE releve_id = " + id);
            conn.createStatement().execute("ALTER TABLE releve AUTO_INCREMENT = 1");
        }catch(Exception e){ e.printStackTrace(); }
        finally {
            try{ conn.close(); }catch(SQLException e){ e.printStackTrace(); }
        }
    }

    public static String updateField(int id, String nom, String value){
        String message = "";
        try{
            conn = ds.getConnection();
            for(int a = 0; a < SQLFieldNames.size() ; a++){
                if(SQLFieldNames.get(a).equals(nom)){
                    if(SQLFieldTypes.get(a).equals("int")){ // Alors il faut vérifier que c'est un Integer
                        try{
                            Integer intValue = Integer.valueOf(value);
                            conn.createStatement().execute("UPDATE releve SET " + nom + " = " + intValue + " WHERE releve_id =" + id);
                            message = "La valeur a bien été actualisée";
                        }catch (Exception e){ message = "La valeur saisie est erronée"; }
                    }else{ // Alors il faut vérifier que c'est un String
                        try{
                            Integer intValue = Integer.valueOf(value);
                            message = "La valeur saisie est erronée";
                        }catch (Exception e){
                            conn.createStatement().execute("UPDATE releve SET " + nom + " = '" + value + "' WHERE releve_id =" + id);
                            message = "La valeur a bien été actualisée";
                        }
                    }
                }
            }
        }catch(Exception e){ e.printStackTrace(); }
        finally {
            try{ conn.close(); }catch(SQLException e){ e.printStackTrace(); }
            return message;
        }
    }

    public static void deleteChamp(String name){
        try{
            conn = ds.getConnection();
            conn.createStatement().execute("ALTER TABLE releve DROP " + name);
        }catch(Exception e){ e.printStackTrace(); }
        finally {
            try{ conn.close(); }catch(SQLException e){ e.printStackTrace(); }
        }
    }

    public static void insertChamp(String name, String type){
        try{
            conn = ds.getConnection();
            conn.createStatement().execute("ALTER TABLE releve ADD " + name + " " + type);
            conn.createStatement().execute("ALTER TABLE releve CHANGE user_id user_id int(10) NOT NULL AFTER " + name);
        }catch(Exception e){ e.printStackTrace(); }
        finally {
            try{ conn.close(); }catch(SQLException e){ e.printStackTrace(); }
        }
    }

    public static void insertReleve(String[] strings){
        try{
            conn = ds.getConnection();
            conn.createStatement().execute("INSERT INTO releve(" + strings[0] + ")VALUES(" + strings[1] + ")");
        }catch(Exception e){ StringBuilderSQL.setMessage("Il y a une erreur de saisie!");}
        finally {
            try{ conn.close(); }catch(SQLException e){ e.printStackTrace(); }
        }
    }

}