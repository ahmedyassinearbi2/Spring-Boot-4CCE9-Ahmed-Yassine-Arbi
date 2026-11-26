package tn.esprit.autoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.service.IReservationService;

@Tag(name = "Gestion Reservation", description = "CRUD des réservations")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService reservationService;

    // http://localhost:8081/api/reservations
    @Operation(summary = "Lister les réservations",
            description = "Récupérer tous les réservations de la base de données")
    @GetMapping
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }

    // http://localhost:8081/api/reservations/1
    @Operation(summary = "Récupérer une réservation",
            description = "Récupérer une réservation à partir de son identifiant")
    @GetMapping("/{id}")
    public Reservation getById(@PathVariable Long id) {
        return reservationService.findById(id);
    }

    @Operation(summary = "Ajouter une réservation",
            description = "Créer une réservation dans la base de données")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @Operation(summary = "Modifier une réservation",
            description = "Mettre à jour une réservation existant(e) à partir de son identifiant")
    @PutMapping("/{id}")
    public Reservation update(@PathVariable Long id, @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @Operation(summary = "Supprimer une réservation",
            description = "Supprimer une réservation à partir de son identifiant")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
