#!/bin/bash

echo "Deploy script : Executing tests with Cucumber"
cd #chemin du dossier de test
mvn test
echo "Deploy script : Clean and creating project"
cd #chemin du dossier de prod
mvn clean install
mv #chemin de destination des jar et chemin de destination pour le déploiement sur Docker
echo "Deploy script : execute Docker Compose"
cd #chemin vers fichier yml
docker-compose up --build

#Question que je me pose : le mvn spring-boot:run un jar suffit ou copier carrément toute la source dans le troisième container?