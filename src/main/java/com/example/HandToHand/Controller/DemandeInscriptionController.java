package com.example.HandToHand.Controller;

import com.example.HandToHand.entite.DemandeInscription;

import com.example.HandToHand.Service.DemandeInscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")

@RequestMapping("/api/demandes")
public class DemandeInscriptionController {

    @Autowired
    private DemandeInscriptionService service;

    // Endpoint pour ajouter une demande d'inscription
    @PostMapping("/ajouter")
    public ResponseEntity<?> ajouterDemande(@RequestBody DemandeInscription demande) {
        try {
            DemandeInscription saved = service.ajouterDemande(demande);
            return ResponseEntity.ok(saved); // Retourner la demande sauvegardée
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cet email est déjà utilisé par un donneur.");
        }
    }

    // Endpoint pour approuver une demande
    @PutMapping("/accepter/{id}")
    public ResponseEntity<String> accepterDemande(@PathVariable Long id) {
        service.accepterDemande(id);
        return ResponseEntity.ok("Demande approuvée et ajoutée à la table Donneur !");
    }
    // Méthode pour refuser une demande d'inscription
    @DeleteMapping("/refuser/{id}")
    public ResponseEntity<String> refuserDemande(@PathVariable Long id) {
        // Vérifier si la demande existe

        // Supprimer la demande depuis la table DemandeInscription
        service.refuserDemande(id);

        return ResponseEntity.ok("Demande d'inscription refusée et supprimée.");
    }
    // Endpoint pour refuser toutes les demandes
    @DeleteMapping("/refuser/all")
    public ResponseEntity<String> refuserAllDemandes() {
        service.refuserAllDemandes();
        return ResponseEntity.ok("Toutes les demandes ont été refusées et supprimées.");
    }

    // Endpoint pour accepter toutes les demandes
    @PutMapping("/accepter/all")
    public ResponseEntity<String> accepterAllDemandes() {
        service.accepterAllDemandes();
        return ResponseEntity.ok("Toutes les demandes ont été acceptées et ajoutées à la table Donneur.");
    }
    // Endpoint pour obtenir toutes les demandes
    @GetMapping("/all")
    public ResponseEntity<?> getAllDemandes() {
        return ResponseEntity.ok(service.getAllDemandes());
    }
}
