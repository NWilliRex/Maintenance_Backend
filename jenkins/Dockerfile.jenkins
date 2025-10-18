FROM jenkins/jenkins:lts

USER root

# Installer Java 21 et Maven
RUN apt-get update && \
    apt-get install -y openjdk-21-jdk maven && \
    apt-get clean

# Définir JAVA_HOME
ENV JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
ENV PATH="$JAVA_HOME/bin:${PATH}"

# Revenir à l'utilisateur Jenkins
USER jenkins
