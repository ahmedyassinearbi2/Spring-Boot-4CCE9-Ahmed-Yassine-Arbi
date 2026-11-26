package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.service.IEquipementService;

@Tag(name = "Gestion Equipement", description = "CRUD des équipements")
@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;

    // http://localhost:8081/api/equipements
    @Operation(summary = "Lister les équipements",
            description = "Récupérer tous les équipements de la base de données")
    @GetMapping
    public List<Equipement> findAll() {
        return equipementService.findAll();
    }

    // http://localhost:8081/api/equipements/1
    @Operation(summary = "Récupérer un équipement",
            description = "Récupérer un équipement à partir de son identifiant")
    @GetMapping("/{id}")
    public Equipement getById(@PathVariable Long id) {
        return equipementService.findById(id);
    }

    @Operation(summary = "Ajouter un équipement",
            description = "Créer un équipement dans la base de données")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Equipement create(@RequestBody Equipement equipement) {
        return equipementService.create(equipement);
    }

    @Operation(summary = "Modifier un équipement",
            description = "Mettre à jour un équipement existant(e) à partir de son identifiant")
    @PutMapping("/{id}")
    public Equipement update(@PathVariable Long id, @RequestBody Equipement equipement) {
        return equipementService.update(id, equipement);
    }

    @Operation(summary = "Supprimer un équipement",
            description = "Supprimer un équipement à partir de son identifiant")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        equipementService.deleteById(id);
    }
}
