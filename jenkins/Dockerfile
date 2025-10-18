FROM jenkins/jenkins:lts-jdk17

USER root

# Installer Maven et Docker CLI
RUN apt-get update && \
    apt-get install -y maven docker.io && \
    rm -rf /var/lib/apt/lists/*

# Désactiver certains warnings Java
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false -Dorg.jenkinsci.plugins.keyring=false"
ENV _JAVA_OPTIONS="-Djava.security.krb5.disableKeyTab=true"

USER jenkins
