package monProjet.fromScratch.service.form;

import monProjet.fromScratch.service.form.Form;

public class LoginForm implements Form {
    private String name;
    private String email;
    private String mdp;

    public LoginForm(String email, String mdp){ this.email = email; this.mdp = mdp; }

    public String getEmail() { return email; }
    public String getMdp() { return mdp; }

    public void setEmail(String email) { this.email = email; }
    public void setMdp(String mdp) { this.mdp = mdp; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
