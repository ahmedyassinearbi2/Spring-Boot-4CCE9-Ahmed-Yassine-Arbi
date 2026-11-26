package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.service.IClientService;

@Tag(name = "Gestion Client", description = "CRUD des clients")
@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;

    // http://localhost:8081/api/clients
    @Operation(summary = "Lister les clients",
            description = "Récupérer tous les clients de la base de données")
    @GetMapping
    public List<Client> findAll() {
        return clientService.findAll();
    }

    // http://localhost:8081/api/clients/1
    @Operation(summary = "Récupérer un client",
            description = "Récupérer un client à partir de son identifiant")
    @GetMapping("/{id}")
    public Client getById(@PathVariable Long id) {
        return clientService.findById(id);
    }

    @Operation(summary = "Ajouter un client",
            description = "Créer un client dans la base de données")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client create(@RequestBody Client client) {
        return clientService.create(client);
    }

    @Operation(summary = "Modifier un client",
            description = "Mettre à jour un client existant(e) à partir de son identifiant")
    @PutMapping("/{id}")
    public Client update(@PathVariable Long id, @RequestBody Client client) {
        return clientService.update(id, client);
    }

    @Operation(summary = "Supprimer un client",
            description = "Supprimer un client à partir de son identifiant")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
    }
}
