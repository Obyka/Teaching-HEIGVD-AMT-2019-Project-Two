# Choix de design et d'implémentation de nos API

## Spec et design de login API

La "login API" est notre API qui permettra de gérer des utilisateurs. Voici les différents DTO qui nous permettent d'interagir entre l'API et l'utilisateur

![image](https://user-images.githubusercontent.com/28777250/72664179-0748af80-39fb-11ea-93eb-79351cee39a0.png)

Voici l'explication des routes principales :

`/login` : POST

Afin de se connecter, il faut poster un objet Credentials. Si ces derniers sont corrects, un objet ValidCreds est retourné. Nous avons choisi de retourner un token JWT, mais aussi l'ID
de l'utilisateur. Comme ça, il pourra faire des opérations dessus sans devoir le redemander


`/users` : POST

Afin de créer un nouvel utilisateur, il faut poster un objet `UserToPost`. Nous avons choisi d'autoriser cette opération uniquement aux administrateurs.

`/users` : GET

Nous avons choisi de permettre à un utilisateur connecté (avec un token dans l'en-tête http correspondante) de pouvoir visualiser ces propres informations sur cette route.
Pour des raisons évidentes de data privacy, il n'est pas possible de spécifier un autre utilisateur que soi-même.

`/password` : PUT

Afin de modifier son propre mot de passe, il faut être connecté et poster un objet `QueryPasswordChange`. L'effet est immédiat et aucune confirmation n'est demandée.

## Implémentation de la login API

Dans cette rubrique, nous mentionnons quelques détails d'implémentation spécifique à cette API.

### Parsing du token JWT et filtres
Afin de parser le token, nous avons deux filtres configurés à l'aide de la classe `AppConfig`. Ces filtres sont : 

- `isAdminFilter` : Parse le token et lève une exception sur le champs `isadmin` de ce dernier ne vaut pas `true`
- `isLoggedFilter` : Contrôle simplement la validité du token et attache le nom d'utilisateur à la requête HTTP

| Filtre         | Route     | Méthodes HTTP |
|----------------|-----------|---------------|
| isAdminFilter  | /users    | POST          |
| isLoggedFilter | /users    | GET           |
|                | /password | Toutes        |

### Gestion des mots de passe
Pour gérer le hashing de mots de passe, nous utilisons une implémentation de BCRYPT reprise du projet précédente. Pour le nombre de rondes nous avons choisi 10000, ce qui correspond aux bonnes pratiques

### Gestion des JWTToken
Pour gérer les tokens (construction, validation, expiration) nous avons utilisé JJWT, la librairie java de gestion de JWTToken.
À l'intérieur du token, nous avons rajouté des Claims custom afin de faciliter certaines opérations.

![image](https://user-images.githubusercontent.com/28777250/72664478-aae78f00-39fe-11ea-8ca3-6f78ec91d5ca.png)

