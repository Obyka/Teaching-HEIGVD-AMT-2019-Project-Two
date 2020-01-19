# Projet 2 d'AMT
Authors : Antoine Hunkeler, Florian Polier
## Contexte

Pour le projet 2 du cours d'AMT, nous avons conceptualisé et développé deux API REST à l'aide de Swagger et Spring boot permettant de manipuler des entités (Pokemons, Trainers, Capture) ainsi que des comptes utilisateurs afin d'appliquer les techniques et pattern enseignés durant les cours.

## Déploiement de l'architecture

- Cloner le repo
- Lancer `start.sh`

Après un certain temps, le projet est prêt à être utilisé et quatres containters ont été déployés, examinons-les ensemble.

### docker-mysql : Serveur MySQL
Ce serveur MySQL contient les deux bases de données pour les API login et pokemon.
Au démarrage, les scripts de créations des deux DV vont être placés dans le répértoire /docker-entrypoint-initdb.d de l'image MySQL et vont donc être exécutés par le container.

### login-api
Ce container déploie notre projet Spring de l'API pour gérer les utilisateurs. Grâce au script `wait-for-it.sh`, il attend une réponse de la base de donnée pour s'exécuter (Le mécanisme `depends_on` de docker-compose n'est pas suffisant.)

### pokemon-api
Ce container déploie notre projet Spring de l'API pour gérer les entités. Grâce au script `wait-for-it.sh`, il attend une réponse de la base de donnée pour s'exécuter (Le mécanisme `depends_on` de docker-compose n'est pas suffisant.)

### traefik
Ce container agit comme un reverse proxy pour nous 2 APIs. Grâce à lui, les deux APIs sont disponible sur `localhost` au port 80. Voici les 2 adresses de bases (sans route)

`localhost/api/login`
`localhost/api/pokemon`

## Utilisation de nos API
Afin de pouvoir effectuer toutes les opérations sur nos API, il faudra tout d'abord récupérer un token JWT avec l'un des deux utilisateurs suivant.

| Nom d'utilisateur | Mot-de-passe | Administrateur |
|-------------------|--------------|----------------|
| user              | password     | Non            |
| admin             | password     | Oui            |

Les utilisateurs ont été créés au préalable car selon notre cahier des charges, seul un administrateur peut utiliser la route de création de compte.

## Design et implémentation des deux API
Nous discutions de ces deux aspects dans un fichier markdown à part, appelé `design-impl-api`
