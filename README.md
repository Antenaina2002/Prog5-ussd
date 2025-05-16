# Prog5-ussd

## Description

`Prog5-ussd` une application Java d'un menu simple USSD d'opérateur.

---

## 🎯 Objectif

- Appliquer des conventions de codage cohérentes.
- Garantir la qualité du code via un linter.
- Automatiser les vérifications (CI) avec GitHub Actions.

---

## 📜 Conventions de codage

### **Nommage**
- **Classes/Interfaces** : `PascalCase` (ex: `UserService`).
- **Variables/Méthodes** : `camelCase` (ex: `getName`).
- **Constantes** : `SCREAMING_SNAKE_CASE` (ex: `MAX_RETRIES`).
- **Packages** : `lowercase` (ex: `services, models...`).

---

## 🔧 Linter (Checkstyle)

### **Prérequis**
- Java 17+
- Maven

### **Utilisation**
**Exécuter le linter localement** :
   ```bash
   mvn clean install checkstyle:check  # Vérifie les erreurs et recompile
   mvn checkstyle:checkstyle  # Génère un rapport HTML (dans target/site/)