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

### 🔹 2. Compiler et exécuter avec Maven
```bash
mvn clean package
java -jar target/*.jar
```

La documentation JavaDoc sera générée dans :
```
target/site/apidocs/index.html
```

---

### 🔹 3. Exécuter avec Docker

#### 🧱 Construire l’image
```bash
docker build -t maintenance_backend .
```

#### ▶️ Lancer le conteneur
```bash
docker run -d -p 8080:8080 --name maintenance_backend maintenance_backend
```

Le backend sera accessible à :
```
http://localhost:8080
```

---

### 🔹 4. Pipeline Jenkins

Le projet utilise **un pipeline Jenkinsfile** qui exécute les étapes suivantes :

| Étape | Description |
|--------|--------------|
| 🧩 **Build** | Compilation du code via Maven |
| 🧪 **Tests** | Exécution des tests unitaires et d’intégration |
| 📚 **JavaDoc** | Génération de la documentation dans `/target/site/apidocs` |
| 🚀 **Notification Discord** | Envoi d’un message en cas de succès ou d’échec du build |

Exemple de commande pour déclencher manuellement :
```bash
mvn clean verify
```

---

## 🧭 Documentation

### 🔸 JavaDoc
Accessible après le build à :  
`target/site/apidocs/index.html`

Si publié par Jenkins :  
👉 via le lien **JavaDoc** dans la page du job.

---

### 🔸 Stack Technique Documentée

| Élément | Détails |
|----------|----------|
| **Backend** | Spring Boot Java 21 |
| **Frontend** | (à préciser si applicable) |
| **Base de données** | (à préciser si applicable) |
| **CI/CD** | Jenkins exécuté dans un conteneur Docker |
| **Gestionnaire de build** | Maven |
| **Conteneurisation** | Docker (avec `Dockerfile` et `docker-compose.yml`) |
| **Tests** | JUnit + JaCoCo |
| **Documentation** | JavaDoc générée via `mvn javadoc:javadoc` |

---

## 🧩 Commandes utiles (Makefile)

| Commande | Description |
|-----------|--------------|
| `make build` | Compile le projet avec Maven |
| `make test` | Exécute les tests |
| `make javadoc` | Génère la documentation JavaDoc |
| `make docker-build` | Construit l’image Docker |
| `make docker-run` | Lance le conteneur |

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
