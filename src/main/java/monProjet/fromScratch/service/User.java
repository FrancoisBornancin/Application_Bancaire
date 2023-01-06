package monProjet.fromScratch.service;

public class User {
    private static String prenom;
    private static int id;
    private static String role;

    public static String getPrenom() { return prenom; }
    public static void setPrenom(String newPrenom) { prenom = newPrenom ;}

    public static int getId(){ return id ;}
    public static void setId(int SQL_id){ id = SQL_id ;}

    public static String getRole() { return role; }
    public static void setRole(String role) { User.role = role; }
}
