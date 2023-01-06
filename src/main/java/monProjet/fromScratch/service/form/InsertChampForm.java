package monProjet.fromScratch.service.form;

import monProjet.fromScratch.service.form.Form;

public class InsertChampForm implements Form {
    private String SQLType;
    private String name;
    private String type;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getSQLType() {
        setSQLType(type);
        return SQLType; }

    public void setSQLType(String oldType) {
        if(oldType.equals("Integer")){ SQLType = "int(10)"; }
        else{ SQLType = "varchar(255)"; }
    }
}
