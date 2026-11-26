package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

@Tag(name = "Gestion Agence", description = "CRUD des agences")
@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;

    // http://localhost:8081/api/agences
    @Operation(summary = "Lister les agences",
            description = "Récupérer tous les agences de la base de données")
    @GetMapping
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    // http://localhost:8081/api/agences/1
    @Operation(summary = "Récupérer une agence",
            description = "Récupérer une agence à partir de son identifiant")
    @GetMapping("/{id}")
    public Agence getById(@PathVariable Long id) {
        return agenceService.findById(id);
    }

    @Operation(summary = "Ajouter une agence",
            description = "Créer une agence dans la base de données")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    @Operation(summary = "Modifier une agence",
            description = "Mettre à jour une agence existant(e) à partir de son identifiant")
    @PutMapping("/{id}")
    public Agence update(@PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    @Operation(summary = "Supprimer une agence",
            description = "Supprimer une agence à partir de son identifiant")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }
}
