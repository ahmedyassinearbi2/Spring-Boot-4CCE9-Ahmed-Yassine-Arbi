# Notes de la couche Service, IoC et Injection de Dépendances - AutoLoc

## A. Modes d'injection comparés

| Critère | Injection par Constructeur (`@RequiredArgsConstructor` + `final`) | Injection par Attribut (`@Autowired`) |
| :--- | :--- | :--- |
| **Champ `final` possible ?** | Oui (garantit l'immutabilité de la référence). | Non (le champ doit être modifiable). |
| **Dépendance visible dans la signature ?** | Oui (visible dans le constructeur). | Non (cachée, pas de constructeur explicite). |
| **Utilisable hors conteneur (ex. tests unitaires avec `new`) ?** | Oui (on peut passer manuellement un mock/repository dans le constructeur). | Non (l'objet sera `null` hors du contexte Spring). |
| **Signalé par SonarQube for IDE ?** | Non (c'est la bonne pratique recommandée). | Oui (règle S6813 : évitez l'injection par attribut). |

## B. Services implémentés

| Service | Dépendances injectées | Mode d'injection | Justification |
| :--- | :--- | :--- | :--- |
| `ContratServiceImpl` | `IContratRepository` | Constructeur | Isolation de la logique métier, sécurité et testabilité. |
| `AgenceServiceImpl` | `IAgenceRepository` | Constructeur | Validation des champs obligatoires et gestion des erreurs. |
| `EmployeServiceImpl` | `IEmployeRepository` | Constructeur | Centralisation de la logique métier des employés. |
| `VehiculeServiceImpl` | `IVehiculeRepository` | Constructeur | Vérification de la validité des tarifs journaliers. |
| `EquipementServiceImpl` | `IEquipementRepository` | Constructeur | Contrôle de la présence d'un libellé valide. |
| `ClientServiceImpl` | `IClientRepository` | Constructeur | Gestion de l'unicité et des attributs obligatoires du client. |
| `ReservationServiceImpl` | `IReservationRepository` | Constructeur | Cohérence des dates de début et de fin de réservation. |
| `PaiementServiceImpl` | `IPaiementRepository` | Constructeur | Service en lecture seule (pas de création/modification directe). |
| `MaintenanceServiceImpl` | `IMaintenanceRepository` | Constructeur | Validation des dates et description des interventions. |

## C. Diagnostic des messages d'erreur du conteneur

1. **Message 1 (`NoSuchBeanDefinitionException` sur `IContratService`) :**
    * **Causes probables :** L'annotation `@Service` est oubliée sur `ContratServiceImpl`, ou la classe se trouve en dehors du package racine (`tn.esprit.autoloc`) et n'est pas scannée.
    * **Correction :** Ajouter `@Service` et s'assurer que le package respecte l'arborescence.

2. **Message 2 (`NoUniqueBeanDefinitionException` - plusieurs beans trouvés) :**
    * **Causes probables :** Il existe plusieurs implémentations d'une même interface sans que Spring sache laquelle injecter.
    * **Correction :** Utiliser `@Primary` sur l'implémentation par défaut, ou `@Qualifier("nomDuBean")` au niveau de l'injection.

3. **Message 3 (Dépendance circulaire - `BeanCurrentlyInCreationException`) :**
    * **Pourquoi l'injection par constructeur le met en évidence :** Le constructeur exige que la dépendance soit créée *avant* l'objet courant. Si le service A a besoin de B et B a besoin de A, le conteneur bloque immédiatement au démarrage.
    * **Piste de correction :** Revoir l'architecture pour extraire la logique commune dans un troisième service tiers.

## D. Anomalies SonarQube for IDE corrigées

| Anomalie SonarQube | Règle / Explication | Correction apportée |
| :--- | :--- | :--- |
| **Injection par attribut** | *S6813* - L'utilisation de `@Autowired` sur les champs réduit la testabilité. | Remplacement par l'injection par constructeur (`@RequiredArgsConstructor` + `final`). |
| **Exception générique** | *S112* - Éviter de lancer `RuntimeException` directement pour garder un typage clair. | Utilisation de l'exception métier dédiée `ResourceNotFoundException`. |
| **Imports superflus** | *S1128* - Présence d'imports non utilisés dans les fichiers de services. | Nettoyage des imports inutiles pour alléger le code. |

## E. Questions de compréhension

1. **Où est le `new` de `ContratServiceImpl` et qui l'exécute ?**  
   Il n'y a aucun `new` explicite dans le code source. C'est le conteneur IoC de Spring qui se charge d'instancier dynamiquement la classe au démarrage via la réflexion.
2. **Pourquoi un contrôleur dépendra-t-il de `IContratService` et non de `ContratServiceImpl` ?**  
   Pour respecter le principe de couplage faible et d'abstraction (Clean Architecture). Le contrôleur dépend d'un contrat (l'interface) et non d'une implémentation concrète.
3. **Pourquoi un service singleton doit-il rester sans état ?**  
   Puisqu'une seule instance partagée gère toutes les requêtes (threads multiples), un attribut modifiable (ex. `private String currentClient`) causerait des problèmes de concurrence et de corruption de données (*race conditions*).
4. **Pourquoi `update` charge-t-il l'entité existante avant d'appeler `save` ?**  
   Pour s'assurer que l'entité existe (et lever une exception si ce n'est pas le cas) et pour éviter d'écraser des relations ou des champs non fournis dans la requête de mise à jour.
5. **Différences fondamentales :**
    * `@Component` (stéréotype générique) vs `@Bean` (déclaration explicite de méthode dans une classe de configuration).
    * `@Primary` (choix par défaut d'un bean) vs `@Qualifier` (sélection explicite par son nom).
    * `IPaiementService` est en lecture seule car la gestion des paiements est liée par cascade au cycle de vie du Contrat.