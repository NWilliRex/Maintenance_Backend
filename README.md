# 🧰 Maintenance_Backend

## 📖 Description

**Maintenance_Backend** est un projet Java Spring Boot servant de backend pour la gestion de la maintenance d’un système.  
Il inclut une intégration complète avec **Maven**, **Docker**, et **Jenkins** pour l’automatisation du build, des tests et de la documentation.

---

## ⚙️ Stack Technique

| Outil / Technologie | Version utilisée | Description |
|---------------------|------------------|--------------|
| ☕ **JDK** | 21 | Environnement Java utilisé pour la compilation |
| 🧩 **Maven** | 3.9.x | Gestionnaire de dépendances et build |
| 🐳 **Docker** | 27.x | Conteneurisation du backend et du CI |
| 🧱 **Jenkins** | LTS | Serveur d’intégration continue |
| 📚 **JaCoCo** | 0.8+ | Génération de rapports de couverture de code |
| 📝 **JavaDoc** | via Maven plugin | Génération automatique de la documentation Java |

---

## 🏗️ Installation et exécution

### 🔹 1. Cloner le projet
```bash
git clone https://github.com/NWilliRex/Maintenance_Backend.git
cd Maintenance_Backend
```

---

## 🐳 Exécution avec Docker

1. Se placer dans le répertoire du projet :
   ```bash
   cd Maintenance_Backend/
   ```

2. Lancer le conteneur Docker avec Compose :
   ```bash
   docker compose up -d
   ```

Cela va automatiquement construire et exécuter le backend dans un conteneur accessible via le port configuré dans le `docker-compose.yml`.

---

## ☕ Exécution avec Maven (via Makefile)

1. Compiler le projet :
   ```bash
   make build
   ```

2. Lancer le projet :
   ```bash
   make run
   ```

Le backend sera ensuite disponible à `http://localhost:9090` (ou selon la configuration).

---

## ⚙️ Configuration de Jenkins

1. Se placer dans le dossier Jenkins :
   ```bash
   cd jenkins/
   ```

2. Démarrer Jenkins avec Docker Compose :
   ```bash
   docker compose up -d
   ```

3. Afficher les logs pour récupérer le mot de passe initial :
   ```bash
   docker logs docker-jenkins
   ```

4. Ouvrir Jenkins dans le navigateur à l’adresse configurée (par défaut `http://localhost:8080`), puis :
   - Copier le mot de passe affiché dans les logs et le coller dans la page Jenkins.
   - Installer les **plugins recommandés**.
   - Installer manuellement les plugins **Discord Notifier** et **Maven Integration**.

5. Créer un **Pipeline** avec ces paramètres :
   - Type : `Pipeline script from SCM`
   - SCM : `Git`
   - URL : *Lien GitHub du projet*
   - Path du script : `jenkins/Jenkinsfile`

6. Lancer le pipeline : il exécutera automatiquement les étapes de build, test et déploiement.

---

## 🧠 Bonnes pratiques

- Utiliser des noms cohérents pour les images Docker (ex : `maintenance_backend:latest`).
- Nettoyer les conteneurs inutilisés régulièrement :
  ```bash
  docker system prune -f
  ```
- Toujours lancer `mvn clean` avant un build complet pour éviter les conflits de dépendances.
- Centraliser les rapports (tests, couverture, JavaDoc) dans Jenkins pour une meilleure traçabilité.

---

## 🔗 Liens utiles

- 📦 [Documentation Maven Javadoc Plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/)
- 🐋 [Docker Docs](https://docs.docker.com/)
- ⚙️ [Jenkins Docs](https://www.jenkins.io/doc/)
- ☕ [Java 21 Documentation](https://docs.oracle.com/en/java/javase/21/)
