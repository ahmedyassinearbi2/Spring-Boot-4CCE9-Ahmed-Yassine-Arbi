package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.service.IVehiculeService;

@Tag(name = "Gestion Vehicule", description = "CRUD des véhicules")
@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    // http://localhost:8081/api/vehicules
    @Operation(summary = "Lister les véhicules",
            description = "Récupérer tous les véhicules de la base de données")
    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    // http://localhost:8081/api/vehicules/1
    @Operation(summary = "Récupérer un véhicule",
            description = "Récupérer un véhicule à partir de son identifiant")
    @GetMapping("/{id}")
    public Vehicule getById(@PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Ajouter un véhicule",
            description = "Créer un véhicule dans la base de données")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    @Operation(summary = "Modifier un véhicule",
            description = "Mettre à jour un véhicule existant(e) à partir de son identifiant")
    @PutMapping("/{id}")
    public Vehicule update(@PathVariable Long id, @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @Operation(summary = "Supprimer un véhicule",
            description = "Supprimer un véhicule à partir de son identifiant")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }

    @Operation(summary = "Affecter un véhicule à une agence",
            description = "Rattacher un véhicule à l'agence indiquée")
    @PutMapping("/{idVehicule}/affecter-agence/{idAgence}")
    public Vehicule affecterVehicule(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        return vehiculeService.affecterVehicule(idVehicule, idAgence);
    }
}
