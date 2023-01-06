package monProjet.fromScratch.controller;

import monProjet.fromScratch.repository.GetMetaData;
import monProjet.fromScratch.service.*;
import monProjet.fromScratch.service.form.*;
import monProjet.fromScratch.service.secureKey.*;
import monProjet.fromScratch.service.sql.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class WebController {
    public boolean goToLostRoute = false;
    public boolean goToReadReleveRoute = false;

    @GetMapping("/loginRoute")
    public String displayLoginRoute(Model model){
        BuildModel.displayForm(new LoginForm("testMail", "testMdp"), model, "form");
        model.addAttribute("mailMessage", GetMetaData.getMailMessage());
        model.addAttribute("mdpMessage", GetMetaData.getMdpMessage());
        return "loginPage";
    }

    @PostMapping("/testLoginRoute")
    public String testLogin(@ModelAttribute LoginForm form, Model model){
        GetMetaData.goSecureKeyRoute(form);
        if(GetMetaData.getMailMessage() != "" || GetMetaData.getMdpMessage() != ""){ return "redirect:/loginRoute" ;}
        return "redirect:/secureKeyRoute";
    }

    @GetMapping("/secureKeyRoute")
    public String displaySecureKeyRoute(Model model, ControleSecureKeyForm form) {
        if(this.goToLostRoute == true){ return "redirect:/lostRoute"; }
        int sec = CountDown.getSecondes();
        int ms = 1000;
        model.addAttribute("ms", ms);
        model.addAttribute("sec", sec);
        model.addAttribute("form", form);
        return "secureKeyPage";
    }

    @PostMapping("/testSecureKeyRoute")
    public String testSecureKeyRoute(@ModelAttribute ControleSecureKeyForm form){
        if(form.getSecureKey().equals(RandomSecureKey.getRandomSecureKey())){
            ResultSecureKeyMessage.setMessage(" Bonjour " + User.getPrenom());
            ResultSecureKeyMessage.splitMessageAndFillList(ResultSecureKeyMessage.getMessage());
            return "redirect:/lostRoute";}
        ResultSecureKeyMessage.setMessage("Le code n'est pas bon...");
        ResultSecureKeyMessage.splitMessageAndFillList(ResultSecureKeyMessage.getMessage());
        return "redirect:/lostRoute";
    }

    @GetMapping("/AjaxSecureKeyRoute_CountDown")
    public String displayAjaxSecureKeyRoute_CountDown(Model model){
        CountDown.setSecondes(CountDown.getSecondes() - 1);
        if(CountDown.getSecondes() == 1){
            ResultSecureKeyMessage.setMessage("Vous avez mis trop de temps...");
            ResultSecureKeyMessage.splitMessageAndFillList(ResultSecureKeyMessage.getMessage());
            this.goToLostRoute = true;
        }
        String sec = CountDown.displaySecondes();
        model.addAttribute("sec", sec);
        return "fragments/countDown::CountDown";
    }

    @GetMapping("/AjaxSecureKeyRoute_SecureKey")
    public String displayAjaxSecureKeyRoute_SecureKey(Model model){
        String secureKey = RandomSecureKey.getRandomSecureKey();
        model.addAttribute("secureKey", secureKey);
        return "fragments/secureKey::SecureKey";
    }

    @GetMapping("/restartApp")
    public String restartApp(){
        this.goToLostRoute = false;
        this.goToReadReleveRoute = false;
        BuildModel.listValues = GetMetaData.getValues();
        CountDown.restart();
        RandomSecureKey.setRandomSecureKey();
        ResultSecureKeyMessage.setCount(0);
        return "redirect:/loginRoute";
    }

    @GetMapping("/lostRoute")
    public String displayLostRoute(Model model) {
        if(this.goToReadReleveRoute == true){ return "redirect:/readReleveRoute" ;}
        String restartApp = ResultSecureKeyMessage.getRestartApp();
        String displayNone = "";
        if(restartApp == ""){
            boolean loginSucess = true;
            model.addAttribute("loginSucess", loginSucess);
            displayNone = "style=\"display: none;";
        }
        int wait = 200;
        int messageLength = ResultSecureKeyMessage.getMessage().length();
        model.addAttribute("wait", wait);
        model.addAttribute("messageLength", messageLength);
        model.addAttribute("displayNone", "<a class=\"btn btn-outline-danger\" " +
                displayNone + " href=\"/restartApp\">" + restartApp + "</a>");
        return "lostPage";
    }

    @GetMapping("/AjaxLostRoute_PrintMessage")
    public String displayAjaxLostRoute_PrintMessage(Model model){
        String printSb = ResultSecureKeyMessage.constructSb();
        int messageLength = ResultSecureKeyMessage.getMessage().length();
        String restart = ResultSecureKeyMessage.getRestartApp();
        int count = ResultSecureKeyMessage.getCount();
        if((count == messageLength) == true && restart == ""){ this.goToReadReleveRoute = true ;}
        model.addAttribute("printSb", printSb);
        return "fragments/printMessage::PrintMessage";
    }

    @GetMapping("/readReleveRoute")
    public String displayReadReleveRoute(Model model){
        BuildModel.displayUser(model, User.getPrenom(), User.getRole());
        model.addAttribute("button", BuildModel.displayButtonAdmin(User.getRole()));
        model.addAttribute("select", BuildModel.displaySelect_options(User.getRole(), User.getId()));
        model.addAttribute("count", BuildModel.displayCount(User.getRole()));
        return "readRelevePage";
    }

    @GetMapping("/AjaxReadReleveRoute_ListReleve")
    public String displayAjaxReadReleveRoute_ListReleve(Model model){
        BuildModel.listValues = GetMetaData.getValues();
        model.addAttribute("listReleve", BuildModel.displayListReleves(GetMetaData.getValues(), User.getRole(), User.getId()));
        return "readReleve/body/ListReleve";
    }

    @GetMapping("/AjaxReadReleveRoute_ListReleveBy/{name}/{value}")
    public String displayAjaxReadReleveRoute_ListReleveBy(@PathVariable("name") String[] name,
                                                          @PathVariable("value") String[] value, Model model){
        ArrayList<ArrayList> listReleve = BuildModel.getNewLists(name, value);
        model.addAttribute("listReleve", BuildModel.displayListReleves(listReleve, User.getRole(), User.getId()));
        return "readReleve/body/ListReleve";
    }

    @PostMapping("/DeleteChamp")
    public String displayDeleteChampName(@ModelAttribute DeleteChampForm deleteForm){
        GetMetaData.deleteChamp(deleteForm.getName());
        return "redirect:/crudReleveRoute";
    }

    @PostMapping("/InsertChamp")
    public String displayInsertChamp(@ModelAttribute InsertChampForm insertForm){
        GetMetaData.insertChamp(insertForm.getName(), insertForm.getSQLType());
        return "redirect:/crudReleveRoute";
    }

    @GetMapping("/crudReleveRoute")
    public String displayCrudRelevePageId(Model model){
        BuildModel.displayUser(model, User.getPrenom(), User.getRole());

        BuildModel.displayForm(new InsertChampForm(), model, "insertForm");
        BuildModel.displayForm(new DeleteChampForm(), model, "deleteForm");

        BuildModel.displayCrudReleveRoute("scriptJSReleve", "insertReleve", model);
        model.addAttribute("insertReleve", BuildModel.displayInsertReleve(User.getRole()));
        model.addAttribute("deleteChamp", BuildModel.displayDeleteChamp());
        model.addAttribute("count", GetMetaData.resetName().size() - 1);

        return "crudRelevePage";
    }

    @GetMapping("/crudReleveRoute/{id}")
    public String displayCrudRelevePageId(@PathVariable("id") int id, Model model){
        BuildModel.displayUser(model, User.getPrenom(), User.getRole());
        model.addAttribute("button", BuildModel.displayButtonAdmin(User.getRole()));
        BuildModel.displayCrudReleveRoute("scriptJSReleveId", "updateReleve", model);
        model.addAttribute("updateReleve", BuildModel.displayUpdateReleve(id));
        return "crudRelevePage";
    }

    @GetMapping("/crudReleveRouteSuppression/{id}")
    public String displayCrudReleveRouteSuppression(@PathVariable("id") int id, Model model){
        model.addAttribute("listReleve", BuildModel.displayListReleves(BuildModel.deleteReleve(id), User.getRole(), User.getId()));
        return "readReleve/body/ListReleve";
    }

    @GetMapping("/AjaxSendReleve/{name}/{value}")
    public String displayAjaxSendReleve(@PathVariable("name") String[] names,
                                  @PathVariable("value") String[] values, Model model){
        PrepareInsertQuery.insertIfNoError(names, values);
        String message = StringBuilderSQL.getMessage();
        String messageColor = "";
        if(!message.equals("Il y a une erreur de saisie!")){
            messageColor = "<div class=\"message\" style=\"color: #4caf50;\">" + message + "</div>";
        }
        else{ messageColor = "<div class=\"message\" style=\"color: #e91e63;\">" + message + "</div>"; }
        model.addAttribute("messageColor", messageColor);
        return "crudReleve/insertMessage";
    }

    @GetMapping("/AjaxUpdateField/{nom}/{value}/{id}")
    public String displayAjaxUpdateField(@PathVariable("nom") String nom,
                                      @PathVariable("value") String value,
                                      @PathVariable("id") int id, Model model){
        String message = GetMetaData.updateField(id, nom, value);
        String messageColor = "";
        if(message.equals("La valeur a bien été actualisée")){
            messageColor = "<div class=\"message\" style=\"color: #4caf50;\">" + message + "</div>";
        }
        else{ messageColor = "<div class=\"message\" style=\"color: #e91e63;\">" + message + "</div>"; }
        model.addAttribute("messageColor", messageColor);
        return "crudReleve/updateMessage";
    }

}
