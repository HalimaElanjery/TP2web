package ht.fds.mbds.elanjery.tp2web.jsf;

import ht.fds.mbds.elanjery.tp2web.llm.LlmClient;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
public class Bb implements Serializable {

    private String roleSysteme;
    private boolean roleSystemeChangeable = true;
    private String question;
    private String reponse;
    private StringBuilder conversation = new StringBuilder();

    @Inject
    private LlmClient llmClient;

    @Inject
    private FacesContext facesContext;

    /**
     * Libellé du rôle -> texte du rôle système envoyé au LLM.
     * La liste déroulante utilise le libellé comme valeur : un texte
     * multiligne provoquait "erreur de validation".
     */
    private static final Map<String, String> ROLES = new LinkedHashMap<>();

    static {
        ROLES.put("Assistant", """
                You are a helpful assistant. You help the user to find the information they need.
                If the user type a question, you answer it.
                """);
        ROLES.put("Traducteur Anglais-Français", """
                You are an interpreter. You translate from English to French and from French to English.
                If the user type a French text, you translate it into English.
                If the user type an English text, you translate it into French.
                If the text contains only one to three words, give some examples of usage of these words in English.
                """);
        ROLES.put("Guide touristique", """
                Your are a travel guide. If the user type the name of a country or of a town,
                you tell them what are the main places to visit in the country or the town
                are you tell them the average price of a meal.
                """);
    }

    public Bb() {
    }

    public String getRoleSysteme() {
        return this.roleSysteme;
    }

    public void setRoleSysteme(String roleSysteme) {
        this.roleSysteme = roleSysteme;
    }

    public boolean isRoleSystemeChangeable() {
        return this.roleSystemeChangeable;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return this.reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getConversation() {
        return conversation.toString();
    }

    public void setConversation(String conversation) {
        this.conversation = new StringBuilder(conversation == null ? "" : conversation);
    }

    public String envoyer() {
        if (this.question == null || this.question.isBlank()) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Texte question vide",
                    "Il manque le texte de la question"
            );
            facesContext.addMessage(null, message);
            return null;
        }

        // Début de la conversation : on passe le texte du rôle système à LlmClient
        if (this.conversation.isEmpty()) {
            this.roleSystemeChangeable = false;
            this.llmClient.setSystemRole(ROLES.get(this.roleSysteme));
        }

        try {
            this.reponse = this.llmClient.envoyerRequete(this.question);
        } catch (Exception e) {
            this.reponse = "Erreur : " + e.getMessage();
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR,
                    "Problème de connexion avec l'API du LLM",
                    "Problème de connexion avec l'API du LLM : " + e.getMessage()
            );
            facesContext.addMessage(null, message);
        }

        afficherConversation();
        return null;
    }

    public String nouveauChat() {
        return "index";
    }

    private void afficherConversation() {
        this.conversation.append("== User:\n")
                .append(question)
                .append("\n== Serveur:\n")
                .append(reponse)
                .append("\n");
    }

    public List<SelectItem> getRolesSysteme() {
        List<SelectItem> liste = new ArrayList<>();
        for (String libelle : ROLES.keySet()) {
            liste.add(new SelectItem(libelle, libelle));
        }
        return liste;
    }
}