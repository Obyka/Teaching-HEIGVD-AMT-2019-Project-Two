# Tests de performance
Comme demandé dans le CdC, nous avons testé les effets d'une pagination existante - ou non - à l'aide des tests de charge de JMeter
Voici notre méthodologie

### Plan de test
Voici notre plan de tests

![image](https://user-images.githubusercontent.com/28777250/72508437-f30f8180-3845-11ea-8d3c-c971e568904f.png)

Regardons les différents objets :
Thread Group : Un simple thread groupe pour lequel nous allons choisir le nombre de thread qui feront les requêtes suivantes

Get JWT Token : Requête HTTP sur la route `login`de l'api user afin de récupéré un token JWT à jour.

JSON Extractor : Objet permettant d'extraire le token de la requête susmentionnée, puis met le résultat dans une variable que nous utilisons plus loin.

Loop Controller : Permet de définir le nombre de fois où la requête GetEntities sera effectuée par chaque thread

Get Entities : Requête HTTP GET sur la route `/pokemons?size=VALEUR` où VALEUR représente le nombre d'entité que nous souhaitons par page.

Token in http header : Manager d'en-tête http attaché à la requête Get Entities, qui récupère la valeur du token stockée précédemment et la met dans le header `authorization`. 

Voici les tests effectués. Pour chacun - si applicable - nous avons testé : Le temps de réponse moyen, minimal, maximal. Les résultats seront présentés plus bas.

Nombre d'entité | Nombre de thread |
-- | -- |
1.E+01 | 5 |
1.E+01 | 50 |
1.E+01 | 100 |
1.E+03 | 5 |
1.E+03 | 50 |
1.E+03 | 100 |
1.E+05 | 5 |
1.E+05 | 50 |
1.E+05 | 100 |
1.E+06 | 5 |
1.E+06 | 50 |
1.E+06 | 100 |

## Méthodologie
Pour notre méthodologie nous avons suivi ces guidelines :
1) Lancer le tests plusieurs fois afin d'améliorer la précision statistique
3) L'infra est régulièrement redémarrée pour éviter les incidences entre tests

/!\ Les tests ont été effectués dans notre VM de développement, ainsi les performences sont moindres.

## Résultats numériques
| Nombre d'entité | Nombre de thread | Min   | Max   | Average |
|-----------------|------------------|-------|-------|---------|
| 1               | 5                | 8     | 27    | 14      |
| 1000            | 5                | 26    | 221   | 71      |
| 100000          | 5                | 4072  | 4828  | 4446    |
| 1000000         | 5                | ERROR | ERROR | ERROR   |
| 1               | 50               | 7     | 118   | 44      |
| 1000            | 50               | 28    | 182   | 96      |
| 100000          | 50               | 7912  | 32000 | 21034   |
| 1000000         | 50               | ERROR | ERROR | ERROR   |
| 1               | 100              | 4     | 102   | 18      |
| 1000            | 100              | 7     | 140   | 43      |
| 100000          | 100              | 13700 | 43350 | 37700   |
| 1000000         | 100              | ERROR | ERROR | ERROR   |

## Graphiques

![image](https://user-images.githubusercontent.com/28777250/72666114-5b5d8f00-3a0f-11ea-8d46-6268d5a2fd9c.png)

![image](https://user-images.githubusercontent.com/28777250/72666123-703a2280-3a0f-11ea-8aaa-f834d552c192.png)

![image](https://user-images.githubusercontent.com/28777250/72666133-80ea9880-3a0f-11ea-82a1-71fbba6fc60c.png)

Pour 1 million d'entités, peu importe le nombre d'utilisateurs, le serveur mets trop longtemps à répondre. 

## Observations et conclusions

Premièrement, nous observons que les API tiennent mieux la charge que lors du projet1 (plus d'entités avant le crash)

C'est dû au fait que les données JSON sont bien plus légères et donc bien plus adaptées pour des tests de charge.

Ensuite, nous pouvons remarquer que parfois, en augmentant le nombre de requêtes, les différents temps de réponses sont en diminution. Notre théorie est la suivante : Lorsque le serveur tient encore bien la charge, il n y a pas de ralentissement non-voulu, et au contraire, c'est après quelques secondes que le serveur a bien "mis en cache" ou du moins a initialisé les différents objets utiles pour le transfert. Ainsi, c'est après plusieurs requêtes qu'il est le plus optimisé, et c'est pour ça que nous voyons les temps baisser.

Finalement, nous avons trouvé le point d'équilibre à 100'000 entités pour 100 utilisateurs. Après cela, si l'on augmente le nombre d'utilisateurs ou le nombres d'entités (1 millions) des crashs vont pouvoir être constatés. 
