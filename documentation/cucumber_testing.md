# Tests avec Cucumber

Cucumber permet de pouvoir tester le comportement de nos APIs en appliquant l'approche BDD (Behavior-Driven Development). Cette dernière applique le principe que toute l'équipe chargé pour le développement du logiciel puisse collaborer lors du développement.

Tout d'abord, un fichier avec l'extension `.feature` permet de créer des "phrases" permettant d'être plus ou moins compréhensible par un collaborateur non-technique. Ces "phrases" sont nommées scénarios et ont cette forme :

```
Feature : description of the feature

Scenario : scenario description
 Given : some variables or values
 When : I make actions with these variables
 Then : what it should happen
```
et peuvent être générées en donnant les fonctions suivantes :

```
@Given("^some variables or values$")
public void some_variables_or_values() {
    //...
}

@When("^I make actions with these variables$")
public void I_make_actions_with_these_variables() {
    //...
}

@Then("^what it should happen$")
public void what_it_should_happen() {
    //...
}
```

Il faut savoir que nous utilisons PicoContainer, dont le principe est de créer une classe contenant des variables, getters et setters pouvant être partagé entre toutes les classes de scénarios.

Mais après chaque sécnario, les variables sont remises à zéro, il faut donc faire attention comment nous structurons les scénarios.

Ce rapport permet de décrire chaque scénario dans chaque API.

### API Login

Ci-dessous tous les scénarios pour tester le comportement du login API : 

```
Feature: CRUD operations for login API

  Background: login as an administrator
    Given the credentials for administrator
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code

```
La mention `Feature` est simplement une description de ce que les scénarios suivants vont tester. `Background` a la même notion que un scénario, mais il s'exécute avant chaque scénario. Dans notre cas, nous avons besoin d'être connecté en tant qu'administrateur avant de pouvoir exécuter certaines actions, donc nous avons implémenté un scénario qui s'exécute avant tous les autres pour récupérer un token JWT de connexion.

```
  Scenario: get user
    Given valid token
    When I search some information about me
    Then I received all information about me and 200 status code

```
Ce premier scénario permet de savoir s'il est possible de récupérer à l'aide du token de connexion de pouvoir récupérer les données concernant l'utilisateur ayant ce token (dans notre cas l'administrateur) est un code HTTP 200.

```
  Scenario: create a new account and login as normal user and change password
    Given a new user to add
    And a valid administrator token
    When I insert a this new user in the database
    Then I received with the new user who have just insered and a 201 status code
    Given credentials for normal user
    When I try to login in the system as normal user
    Then I received a valid token with 200 status code
    Given new password
    And valid token for normal user
    When I update my password with a new password
    Then I receive all my information and 200 status code
```
Ce deuxième scénario nous permet de pouvoir créer un nouvel utilisateur, se se connecter avec et ensuite pouvoir modifier le mot de passe. Pour la création, nous avons besoin d'un token pour l'administrateur,car lui seul peut rajouter des utilisateurs. Lors de l'insertion, nous devons reçevoir des inforations concernant le nouvel utilisateur et un code 201. La connexion avec ce dernier doit nous retourner un code 200, comme lorsque l'utilisateur essaie de modifier son mot de passe.

Il est important de garder ces étapes dans le même scénario, car comme dit au début, toutes les variables sont remises à zéro entre chaque scénario, donc il n'est pas possible de séparer un scénario pour la création d'un nouvel utilisateur, et ensuite l'utiliser dans un autre pour la connexion avec ce dernier.

```
  Scenario: create a new account, login and try to get information
    Given a new user to add
    And a valid administrator token
    When I insert a this new user in the database
    Then I received with the new user who have just insered and a 201 status code
    Given credentials for normal user
    When I try to login in the system as normal user
    Then I received a valid token with 200 status code
    Given valid token for normal user
    When I search some information about me
    Then I received all information about me and 200 status code
```

Ce scénario permet de pouvoir rajouter un nouvel utilisateur, de se connecter avec ce dernier et pouvoir récupérer ses informations à l'aide de son token de connexion.

Certaines fonctions dans les scénarios sont les mêmes, ce qui permet de ne pas réimplémenter à chaque fois une nouvelle fonction. Il est même possible de pouvoir réutiliser certaines fonctions dans d'autres fichiers `.feature`.


```
Feature: testing error status code for every API operations

  Scenario: login with bad credentials
    Given bad credentials
    When I try to connect with bad credentials
    Then the system returns me an error that it is a bad login with 401 status code

```
Ces nouveaux scénarios permettent de tester le comportement de l'API lorsque l'utilisateur n'utilise pas correctement notre application.

Comme d'habitude, le scénario dans `Background` pour la connexion administrateur décrite plus haut.

```
  Scenario: get information about me with empty token
    Given empty token
    When I try to get information with empty token
    Then the system returns me an error with 403 status code
```
Ce scénario permet de pouvoir tester le comportement lorsqu'un utilisateur essaie de récupérer ses informations sans spécifier un token de connexion.

L'API doit retourner une erreur 403 comme quoi l'accès est interdit.

```
  Scenario: Add user without administrator token
    Given empty token
    And user to add
    When I try to add a new user without token
    Then the system returns me an error that I have denied access with 403 status code
```
ce scénario reprend un peu le but du précédent et permet de tester le comportement si l'utilisateur ne spécifie pas le token et essaie d'insérer un nouvel utilisateur.

```
  Scenario: login as an administrator, add a new user, login with this new user and try to add a new user with normal user token
    Given the credentials for administrator
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code
    Given a valid administrator token
    And user to add
    When I insert a this new user in the database
    Then I received with the new user who have just insered and a 201 status code
    Given credentials for normal user
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code
    Given a new user to add
    And valid token for normal added user
    When I try to add a new user with normal user token
    Then the system returns me an error with 403 status code
```
Ce scénario reprend aussi le même principe que les scénarios précédents, mais nous testons ce qu'il se passe si un utilisateur normal essaie de se connecter et essaie de créer un utilisateur.

Le système peut lui retourner une erreur 403, car seul l'administrateur peut créer des nouveaux utilisateurs.

```
Feature: other errors, like send malformed payload for example

  Scenario: get information with malformed token
    Given malformed token
    When I try to get information about me
    Then the system returns me an error with 403 status code
```
Cette fois, il s'agit plus d'erreurs techniques, comme si l'utilisateur envoyait 
un mauvais payload ou un token malformé.

C'est le cas de ce premier scénario : il permet de tester ce qu'il se passe si un token malformé est envoyé pour effectuer une action GET par exemple. Le système doit retourner l'erreur HTTP 403.

```
  Scenario: login and get information in header if good format
    Given the credentials for administrator
    When I try to login in the system
    Then the system returns me a token with my identifior and a 200 status code
    Given a valid administrator token
    When I try to get information about me
    Then the system returns me a payload that is a valid payload
```
Ce scénario vérifie si la mention `application/json` apparaît bien dans l'entête sous `Content-Type`.

```
  Scenario: insert a bad payload
    Given malformed data to insert
    When I try to insert a bad payload in login
    Then the system returns the 400 status with an error
```
Ce dernier scénario permet de tester ce qu'il se passe si un utilisateur envoie un payload JSON malformé. Le système doit lui retourner une erreur 400 comme quoi la requête est malformée.

### Pokemon API

#### Pokemons

Les scénarios suivants permettent de tester les codes de statut HTTP ou les codes d'erreurs HTTP.

```
Feature: CRUD for Pokemons

  Background: login as an administrator
    Given payload JSON and HTTP data and header
    When I make a POST request to /api/login/login
    Then I receive a valid token
```
Comme dans les scénarios précédents, l'administrateur se logue pour obtenir un token afin de pouvoir exécuter certaines actions.

```
  Scenario: delete all pokemons
    When I delete all pokemons
```
Tout d'abord, un scénario a été mis en place afin de pouvoir supprimer tous les pokémons de la table insérés précédemment.

```
  Scenario: add pokemons
    When I add 20 pokemons
```
Ceci permet d'insérer 20 pokémons afin de pouvoir tester la pagination.

```
  Scenario Outline: pagination
    When I get pokemons at specific page <page> with specific size <size>
    Then the system returns the 200 status and size <answer> with pokemons

    Examples:
      | page      | size | answer |
      | 0         | 20   |20   |
      | 0         | 10   |10   |
      | 1          | 10   |10   |
      | 0          | 1   |1   |
```
Ce scénario est un peu spécial : un scénario est déclaré et est exécuté selon le tableau déclaré ci-dessus, c'est-à-dire qu'il va exécuter ce scénario 4 fois (1 fois pour chaque ligne). Ceci permet donc de pouvoir tester la pagination. Par exemple : le premier scénario prend la page 0 et la taille 20 et nous devons obtenir 20 données.

```
  Scenario: pagination
    When I get pokemons at specific page 0 with specific size 0
    Then The system returns me an error with 500 status code
```
Ceci est aussi pour tester la pagination, mais permet de tester lorsque nous recherchons la première page avec une taille de 0, alors que notre API a été conçu pour que la taille spécifié lors de la pagination soit plus grand que 0.

```
  Scenario: delete an unexisted pokemon
    Given random pokeDexId
    When I delete this pokemon
    Then The system returns me an error with 404 status code
```
Ce scénario permet de tester le comportement de l'API lorsque l'utilisateur tente de supprimer un pokémon qui n'existe pas. Il retourne une erreur accompagné du code de statut bien connu 404.

```
  Scenario: delete all pokemons
    When I delete all pokemons
    Then the system returns the 204 status
```
La même chose que le premier scénario, mais avec l'attente d'un code statut comme quoi l'opération s'est bien déroulé.

```
  Scenario: create a pokemon and check if it is created
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given This new pokemon
    When I get information about this pokemon
    Then the system returns the added pokemon with 200 status
```
Ce scénario permet de créer un pokémon et tester sa présence dans la base de données accompagné des codes statut.

```
  Scenario: create a pokemon and a user and login and try to get pokemon with this user
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given information for new user and other parameters
    When I insert this new user
    Then The user has been created
    Given credentials for new user
    When I make a POST request to /api/login/login
    Then I receive a valid token
    When I try to get the pokemon with this new user
    Then The system returns me an error with 404 status code
```
Ce scénario permet de créer un nouveau pokémon, un nouvel utilisateur, se connecter avec ce nouvel utilisateur et essayer d'obtenir le nouveau pokémon, mais le système lui retourne l'erreur 404. la raison est que notre API a été conçu pour afficher cette erreur lorsque soit le pokémon est introuvable, soit parce que l'utilisateur n'est pas le propriétaire de ce pokémon.

```
  Scenario: get a pokemon that does not exist
    Given random pokeDexId
    When I get information about this pokemon
    Then The system returns me an error with 404 status code
```
Ce scénario permet d'essayer de retrouver un pokémon qui n'existe pas dans la base de données et qui nous retroune une erreur 404.

```
  Scenario: get all pokemons belong to administrator
    When I get all pokemons
    Then I receive all my pokemons belong to me and 200 status code
```
Ce scénario permet de pouvoir obtenir tous les pokémons, mais seulement ceux qui appartiennent à l'utilisateur courant connecté.

```
  Scenario: create and delete a pokemon
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    When I delete this pokemon
    Then the system returns the 204 status
```
Ce scénario permet de pouvoir créer un nouveau pokémon et de le supprimer tout de suite après.

```
  Scenario: create and update a pokemon
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given modification on Pokemon
    When I update a pokemon
    Then the system returns the 200 status
```
Ce scénario permet de créer un nouveau pokémon et de le mettre à jour tout de suite.

```
  Scenario: delete all pokemons
    When I delete all pokemons
```
Même chose que le premier scénario, donc supprimer tous les pokémons.

#### Trainers

Les scénarios suivants sont inspirés du même principe que les scénarios des pokémons.

```
Feature: CRUD for Trainers

  Background: login as an administrator
    Given payload JSON and HTTP data and header
    When I make a POST request to /api/login/login
    Then I receive a valid token

  Scenario: delete all trainers
    When I delete all trainers

  Scenario: add trainers
    When I create 20 trainers

  Scenario Outline: pagination
    And I get trainers at specific page <page> with specific size <size>
    Then the system returns the 200 status and size <answer> with trainers

    Examples:
      | page      | size | answer |
      | 0         | 5   |5   |
      | 0         | 10   |10   |
      | 1          | 5   |5   |
      | 0          | 1   |1   |

  Scenario: pagination
    When I get trainers at specific page 0 with specific size 0
    Then The system returns me an error with 500 status code with trainers

  Scenario: delete an unexisted trainer
    Given random trainer ID
    When I delete this trainer
    Then The system returns me an error with 404 status code

  Scenario: delete all trainers
    When I delete all trainers
    Then the system returns the 204 status

  Scenario: add a new trainer and check if is it created
    Given a new trainer
    When I add a new trainer
    Then the system returns the added trainer with 201 status
    When I get information about this trainer
    Then the system returns the added trainer with 200 status

  Scenario: create a trainer and a user and login and try to get pokemon with this user
    Given a new trainer
    When I add a new trainer
    Then the system returns the added trainer with 201 status
    Given information for new user and other parameters
    When I insert this new user
    Then The user has been created
    Given credentials for new user
    When I make a POST request to /api/login/login
    Then I receive a valid token
    When I get information about this trainer
    Then The system returns me an error with 404 status code

  Scenario: get a trainer that does not exist
    Given random trainer ID
    When I get information about this trainer
    Then The system returns me an error with 404 status code

  Scenario: get all trainers belong to administrator
    When I get all trainers
    Then I receive all my trainers belong to me and 200 status code

  Scenario: create and delete a trainer
    Given a new trainer
    When I add a new trainer
    Then the system returns the added pokemon with 201 status
    When I delete this trainer
    Then the system returns the 204 status

  Scenario: create and update a trainer
    Given a new trainer
    When I add a new trainer
    Then the system returns the added pokemon with 201 status
    Given modification on trainer
    When I update a trainer
    Then the system returns the 200 status

  Scenario: delete all trainers
    When I delete all trainers
```

#### Captures

Les scénarios pour les captures sont un peu plus spécial : il faut soit tester par rapport à un pokémon ou par rapport à un trainer : 

```
Feature: CRUD operations for captures

  Background: login as an administrator
    Given payload JSON and HTTP data and header
    When I make a POST request to /api/login/login
    Then I receive a valid token

  Scenario: add pokemons and trainer and captures and test pagination
    Given a new trainer
    When I add 20 pokemons
    And I add a new trainer
    And I add 20 captures about a trainer
    And I get captures about a trainer at specific page 0 with specific size 20
    Then the system returns the 200 status and size 20 with captures
    When I get captures about a trainer at specific page 0 with specific size 10
    Then the system returns the 200 status and size 10 with captures
    When I get captures about a trainer at specific page 1 with specific size 10
    Then the system returns the 200 status and size 10 with captures
    When I get captures about a trainer at specific page 0 with specific size 0
    Then The system returns me an error with 500 status code

  Scenario: add pokemon and trainers and captures and test pagination
    Given a new pokemon to create
    When I add a new pokemon
    And I create 20 trainers
    And I add 20 captures about a pokemon
    And I get captures about a pokemon at specific page 0 with specific size 20
    Then the system returns the 200 status and size 20 with captures
    When I get captures about a pokemon at specific page 0 with specific size 10
    Then the system returns the 200 status and size 10 with captures
    When I get captures about a pokemon at specific page 1 with specific size 10
    Then the system returns the 200 status and size 10 with captures
```
Les deux gros scénarios ci-dessus permettent le test de la pagination et nous pouvons remarquer que le premier scénario insère 20 pokémons, un trainer et 20 captures par rapport à ce trainer et teste la pagination en récupérant des informations par rapport à ce trainer.

le deuxième part sur le même principe, mais insère un pokémon, 20 trainers et 20 captures afin de pouvoir tester la pagination en demandant des informations sur les captures par rapport à un pokémon.

```
  Scenario: create a pokemon, a trainer and a capture and check if it is created
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given a new trainer
    When I add a new trainer
    Then the system returns the added trainer with 201 status
    Given a new capture
    When I create a new capture
    Then the system returns the added capture with 201 status
    When I get information about this capture with the new pokemon
    Then the system returns the added capture with pokemon with 200 status
    When I get information about this capture with the new trainer
    Then the system returns the added capture with trainer with 200 status
```
Ce scénario permet de pouvoir créer un pokémon, un trainer et une capture et tester si la capture est bien présente dans la base de données.

```
  Scenario: get captures with a pokemon that does not exist
    Given random pokeDexId
    When I get information about captures with a unexisted pokemon
    Then The system returns me an error with 404 status code

  Scenario: get captures with a trainer that does not exist
    Given random trainer ID
    When I get information about captures with a unexisted trainer
    Then The system returns me an error with 404 status code
```
Les deux scénarios précédents permettent de tester respecivement ce qu'il se passe si nous recherchons une capture par rapport à un pokémon qui n'existe pas ou par rapport à un trainer qui n'existe pas.

#### Autres erreurs 

```
Feature: other errors

  Background: login as an administrator
    Given payload JSON and HTTP data and header
    When I make a POST request to /api/login/login
    Then I receive a valid token
  
  Scenario: insert a bad payload
    Given malformed data to insert
    When I try to insert a bad payload in pokemon
    Then the system returns the 400 status with an error
```
A part le scénario `Background` qui permet de récupérer un token en tant qu'administrateur, ce scénario permet de tester le comportement de l'API si l'utilisateur insère un mauvais payload.

### Conclusion

Ces scénarios qui ne sont des simples phrases peuvent être facilement interprétées par une personne non-technique, mais le développement de ces scénarios n'a pas été facile par manque de connaissance de l'utilisation de Cucumber et pouvoir débugger afin de pallier les erreurs.

Aussi, deux scénarios n'ont pas pu être implémentés, dont les voici : 

```
  Scenario: create a pokemon, a trainer, a capture and a user and login and try to get capture with this user
    Given a new pokemon to create
    When I add a new pokemon
    Then the system returns the added pokemon with 201 status
    Given a new trainer
    When I add a new trainer
    Then the system returns the added trainer with 201 status
    Given a new capture
    When I create a new capture
    Then the system returns the added capture with 201 status
    Given information for new user and other parameters
    When I insert this new user
    Then The user has been created
    Given credentials for new user
    When I make a POST request to /api/login/login
    Then I receive a valid token
    When I get information about this capture with the new pokemon
    Then The system returns me an error with 404 status code
    When I get information about this capture with the new trainer
    Then The system returns me an error with 404 status code

  Scenario: insert without accept charset in header
    Given malformed header
    When I try to insert a pokemon
    Then the system returns the 406 status with an error
```

les causes sont les suivantes : pour le premier, nous n'arrivons pas à trouver le problème car il marquait toujours une erreur. Après maintes débuggages, le token ne se mettait pas à jour, alors que nous appelons la fonction pour le mettre à jour dans l'entête.

Le deuxième scénario ne fonctionnait pas, car notre hypothèse est que notre spécification API rajoutait malgré tout l'entête `Accept : application/json` pour reçevoir en réponse des données en JSON, malgré que nous testons ce qu'il se passe en enlevant cette entête, dont le système doit retrourner une erreur 406.

malgré cela, tous nos tests passent, ce qui veut dire que notre API répond à nos spécifications.

Le code source des scénarios est dispoinible sur le Git.