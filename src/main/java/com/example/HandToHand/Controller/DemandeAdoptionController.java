package com.example.HandToHand.Controller;

import com.example.HandToHand.Service.DemandeAdoptionService;
import com.example.HandToHand.entite.*;
import com.example.HandToHand.repository.Admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/demande-adoption")
public class DemandeAdoptionController {

    @Autowired
    private DemandeAdoptionService demandeAdoptionService;
    @Autowired
    private DonneurRepository donneurRepository;
    @Autowired
    private OrphelinRepository orphelinRepository;
    @Autowired
    private DemandeAdoptionRepository demandeAdoptionRepository;


    @PostMapping
    public ResponseEntity<String> soumettreDemande(@RequestBody DemandeAdoption demandeAdoption) {
        if (demandeAdoption.getDonneur() == null || demandeAdoption.getDonneur().getId() == null) {
            return new ResponseEntity<>("Le donneur est requis", HttpStatus.BAD_REQUEST);
        }

        if (demandeAdoption.getOrphelin() == null || demandeAdoption.getOrphelin().getIdo() == null) {
            return new ResponseEntity<>("L'orphelin est requis", HttpStatus.BAD_REQUEST);
        }

        try {
            Optional<Donneur> donneur = donneurRepository.findById(demandeAdoption.getDonneur().getId());
            if (!donneur.isPresent()) {
                return new ResponseEntity<>("Le donneur spécifié n'existe pas.", HttpStatus.BAD_REQUEST);
            }

            Optional<Orphelin> orphelin = orphelinRepository.findById(demandeAdoption.getOrphelin().getIdo());
            if (!orphelin.isPresent()) {
                return new ResponseEntity<>("L'orphelin spécifié n'existe pas.", HttpStatus.BAD_REQUEST);
            }

            // Appel au service pour gérer la logique
            demandeAdoptionService.soumettreDemande(demandeAdoption);

            demandeAdoptionService.notifierDonneur(
                    donneur.get(),
                    "Votre demande d’adoption a été soumise et est en attente de validation."
            );

            return new ResponseEntity<>("Demande d'adoption soumise avec succès!", HttpStatus.CREATED);

        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Erreur d'intégrité des données : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de l'enregistrement : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/en-attente")
    public ResponseEntity<List<DemandeAdoption>> getDemandesEnAttente() {
        List<DemandeAdoption> demandes = demandeAdoptionService.getDemandesEnAttente();
        return new ResponseEntity<>(demandes, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<DemandeAdoption>> getAllDemandes() {
        try {
            List<DemandeAdoption> demandes = demandeAdoptionService.getAllDemandes();
            return new ResponseEntity<>(demandes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/accepter")
    public ResponseEntity<String> accepterDemande(@PathVariable Long id) {
        try {
            demandeAdoptionService.accepterDemande(id);
            return new ResponseEntity<>("Demande d’adoption acceptée", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/refuser")
    public ResponseEntity<String> refuserDemande(@PathVariable Long id) {
        try {
            demandeAdoptionService.refuserDemande(id);
            return new ResponseEntity<>("Demande d’adoption refusée", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/accepter-all")
    public ResponseEntity<String> accepterToutesLesDemandes() {
        demandeAdoptionService.acceptAll(); // C’est cette méthode qu’il faut appeler
        return ResponseEntity.ok("Toutes les demandes en attente ont été acceptées avec succès.");
    }



        @PutMapping("/annuler-adoption/{id}")
        public ResponseEntity<?> annulerSuivi(@PathVariable Long id, @RequestBody Map<String, String> body) {
            try {
                // Appeler le service pour annuler le suivi
                demandeAdoptionService.annulerAdoption(id, body.get("raison"));

                return ResponseEntity.ok("Suivi d'adoption annulé avec succès.");
            } catch (RuntimeException e) {
                // Si le suivi n'est pas trouvé, renvoyer une erreur 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suivi non trouvé.");
            } catch (Exception e) {
                // Gérer toute autre exception
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue.");
            }
        }

    // Endpoint pour récupérer les détails d'un orphelin par son ID
    @GetMapping("/{orphelinId}")
    public ResponseEntity<Orphelin> getOrphelinDetaille(@PathVariable Long orphelinId) {
        try {
            // Appeler le service pour obtenir l'orphelin
            Orphelin orphelin = demandeAdoptionService.getOrphelinDetaille(orphelinId);
            return ResponseEntity.ok(orphelin); // Retourner un statut 200 OK avec l'orphelin
        } catch (Exception e) {
            // Retourner une erreur si l'orphelin n'est pas trouvé
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @GetMapping("/mes-adoptions")
    public ResponseEntity<List<SuiviAdoption>> getAdoptionsDonneur(Authentication authentication) {
        // Récupérer le donneur connecté depuis le contexte de sécurité
        Donneur donneurConnecte = getDonneurFromAuthentication(authentication);

        List<SuiviAdoption> adoptions = demandeAdoptionService.getAdoptionsByDonneur(donneurConnecte.getId());
        return ResponseEntity.ok(adoptions);
    }

    private Donneur getDonneurFromAuthentication(Authentication authentication) {
        String email = authentication.getName(); // Assure-toi que c'est bien l'email ou username du donneur
        return donneurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Donneur non trouvé"));
    }



}
