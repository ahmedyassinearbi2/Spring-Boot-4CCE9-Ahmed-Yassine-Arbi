package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.service.IMaintenanceService;

@Tag(name = "Gestion Maintenance", description = "CRUD des maintenances")
@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    // http://localhost:8081/api/maintenances
    @Operation(summary = "Lister les maintenances",
            description = "Récupérer tous les maintenances de la base de données")
    @GetMapping
    public List<Maintenance> findAll() {
        return maintenanceService.findAll();
    }

    // http://localhost:8081/api/maintenances/1
    @Operation(summary = "Récupérer une maintenance",
            description = "Récupérer une maintenance à partir de son identifiant")
    @GetMapping("/{id}")
    public Maintenance getById(@PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @Operation(summary = "Ajouter une maintenance",
            description = "Créer une maintenance dans la base de données")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceService.create(maintenance);
    }

    @Operation(summary = "Modifier une maintenance",
            description = "Mettre à jour une maintenance existant(e) à partir de son identifiant")
    @PutMapping("/{id}")
    public Maintenance update(@PathVariable Long id, @RequestBody Maintenance maintenance) {
        return maintenanceService.update(id, maintenance);
    }

    @Operation(summary = "Supprimer une maintenance",
            description = "Supprimer une maintenance à partir de son identifiant")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
