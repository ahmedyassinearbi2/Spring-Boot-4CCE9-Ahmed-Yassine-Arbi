package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.service.IContratService;

@Tag(name = "Gestion Contrat", description = "CRUD des contrats de location")
@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final IContratService contratService;

    @Operation(summary = "Lister les contrats",
            description = "Récupérer tous les contrats de la base de données")
    @GetMapping
    public List<Contrat> findAll() {
        return contratService.findAll();
    }


    @Operation(summary = "Récupérer un contrat",
            description = "Récupérer un contrat à partir de son identifiant")
    @GetMapping("/{id}")
    public Contrat getById(@PathVariable Long id) {
        return contratService.findById(id);
    }

    @Operation(summary = "Ajouter un contrat",
            description = "Créer un nouveau contrat dans la base de données")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }

    @Operation(summary = "Modifier un contrat",
            description = "Mettre à jour un contrat existant à partir de son identifiant")
    @PutMapping("/{id}")
    public Contrat update(@PathVariable Long id, @RequestBody Contrat contrat) {
        return contratService.update(id, contrat);
    }

    @Operation(summary = "Supprimer un contrat",
            description = "Supprimer un contrat à partir de son identifiant")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        contratService.deleteById(id);
    }
}