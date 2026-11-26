package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.service.IEmployeService;

@Tag(name = "Gestion Employe", description = "CRUD des employés")
@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final IEmployeService employeService;

    // http://localhost:8081/api/employes
    @Operation(summary = "Lister les employés",
            description = "Récupérer tous les employés de la base de données")
    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    // http://localhost:8081/api/employes/1
    @Operation(summary = "Récupérer un employé",
            description = "Récupérer un employé à partir de son identifiant")
    @GetMapping("/{id}")
    public Employe getById(@PathVariable Long id) {
        return employeService.findById(id);
    }

    @Operation(summary = "Ajouter un employé",
            description = "Créer un employé dans la base de données")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe employe) {
        return employeService.create(employe);
    }

    @Operation(summary = "Modifier un employé",
            description = "Mettre à jour un employé existant(e) à partir de son identifiant")
    @PutMapping("/{id}")
    public Employe update(@PathVariable Long id, @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }

    @Operation(summary = "Supprimer un employé",
            description = "Supprimer un employé à partir de son identifiant")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeService.deleteById(id);
    }

    @Operation(summary = "Désaffecter un employé",
            description = "Retirer un employé de son agence actuelle")
    @PutMapping("/{idEmploye}/desaffecter-agence")
    public Employe desaffecterEmploye(@PathVariable Long idEmploye) {
        return employeService.desaffecterEmploye(idEmploye);
    }

    @Operation(summary = "Affecter un employé à une agence",
            description = "Affecter un employé sans agence à l'agence indiquée")
    @PutMapping("/{idEmploye}/affecter-agence/{idAgence}")
    public Employe affecterEmploye(@PathVariable Long idEmploye, @PathVariable Long idAgence) {
        return employeService.affecterEmploye(idEmploye, idAgence);
    }
}
