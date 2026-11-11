# Utilisation de l'IA - TP 2 (application Web)

Outil utilisé : Claude (assistant IA), dans une conversation de chat.
Environnement : IntelliJ IDEA, projet Maven (JSF, PrimeFaces, LangChain4j, Gemini), Git et GitHub.


## 1. Symboles LangChain4j introuvables

- **Problème rencontré** : IntelliJ affichait « Cannot resolve symbol 'dev' », puis ChatMemory, AiServices, etc.
- **Raison de l'utilisation** : je ne comprenais pas l'origine de ces nombreuses erreurs de symboles dans IntelliJ. <!-- Vérifier que c'est vrai, sinon corriger -->
- **Prompt utilisé** : la liste des messages d'erreur copiée depuis IntelliJ.
- **Réponse de l'IA (extrait)** : les dépendances n'étaient pas chargées dans ce projet ; vérifier le pom.xml, recharger Maven, vérifier le panneau Dependencies.
- **Ce que j'ai appris** : les erreurs en cascade viennent souvent d'une seule cause, ici des dépendances non chargées.

## 2. « form:role : erreur de validation »

- **Problème rencontré** : après avoir choisi un rôle dans la liste, la page affichait « form:role : erreur de validation. La valeur est incorrecte. »
- **Raison de l'utilisation** : je ne comprenais pas pourquoi la valeur choisie dans la liste était refusée. <!-- Vérifier que c'est vrai, sinon corriger -->
- **Prompt utilisé** : « meme si je click sue le role il s'afiche comme ca » (avec une capture d'écran).
- **Réponse de l'IA (extrait)** : la valeur de chaque rôle était un texte multiligne que le navigateur renvoie avec des retours à la ligne différents ; il faut utiliser le libellé court comme valeur de la liste et garder le texte du rôle dans une Map côté serveur.
- **Ce que j'ai appris** : JSF compare la valeur renvoyée à celles de la liste ; un texte long et multiligne n'est pas une bonne valeur.


## 5. Rédaction de l'email

- **Problème rencontré** : mettre en forme l'email « TP 2 - Web terminé ».
- **Raison de l'utilisation** : gagner du temps sur la mise en forme.
- **Prompt utilisé** (résumé) : demande d'un email qui continet tous crs detaille (avec un fichier qui contient une description).
- **Réponse de l'IA (extrait)** : un modèle avec l'URL du dépôt, la description et les résultats de mes tests.
- **Ce que j'ai appris** :l'email