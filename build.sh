#!/bin/bash

# Supprime le dossier out (compilation propre)
rm -rf out

# Crée le dossier out
mkdir -p out

# Compile tous les fichiers .java du dossier src en mettant les classes dans out, avec le driver PostgreSQL dans le classpath
javac -d out -cp "lib/postgresql-42.7.6.jar" $(find src -name "*.java")

# Exécute la classe principale avec le bon classpath
java -cp "out;lib/postgresql-42.7.6.jar" com.erp.App
