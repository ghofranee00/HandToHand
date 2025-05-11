package com.example.HandToHand.Controller;

import com.example.HandToHand.Service.DemandeAdoptionService;
import com.example.HandToHand.entite.DemandeAdoption;
import com.example.HandToHand.entite.Donneur;
import com.example.HandToHand.entite.Orphelin;
import com.example.HandToHand.repository.Admin.DemandeAdoptionRepository;
import com.example.HandToHand.repository.Admin.DonneurRepository;
import com.example.HandToHand.repository.Admin.OrphelinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
}
