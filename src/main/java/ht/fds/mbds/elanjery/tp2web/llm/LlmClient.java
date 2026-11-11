package ht.fds.mbds.elanjery.tp2web.llm;

import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.google.genai.GoogleGenAiChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.enterprise.context.Dependent;

import java.io.Serializable;

/**
 * Interface avec le LLM (Gemini) en utilisant LangChain4j.
 * Portée Dependent : l'instance disparaît avec l'instance du backing bean.
 */
@Dependent
public class LlmClient implements Serializable {

    // Rôle système choisi par l'utilisateur
    private String systemRole;
    // Service IA (proxy créé par LangChain4j)
    private Assistant assistant;
    // Mémoire de la conversation (l'API du LLM est sans état)
    private ChatMemory chatMemory;

    public LlmClient() {
        // Clé secrète lue dans la variable d'environnement
        String cle = System.getenv("GEMINI_KEY");

        ChatModel model = GoogleGenAiChatModel.builder()
                .apiKey(cle)
                .modelName("gemini-3.5-flash")
                .build();

        this.chatMemory = MessageWindowChatMemory.withMaxMessages(20);
        this.assistant = AiServices.builder(Assistant.class)
                .chatModel(model)
                .chatMemory(chatMemory)
                .build();
    }

    /**
     * Met le rôle système dans la mémoire pour que le LLM en tienne compte.
     * Le contexte change, donc on vide d'abord la mémoire.
     */
    public void setSystemRole(String role) {
        this.systemRole = role;
        this.chatMemory.clear();
        this.chatMemory.add(SystemMessage.from(role));
    }

    /** Envoie la question au LLM et retourne sa réponse. */
    public String envoyerRequete(String prompt) {
        return this.assistant.chat(prompt);
    }
}