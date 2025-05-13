package com.example.HandToHand.Service;

import com.example.HandToHand.entite.*;
import com.example.HandToHand.repository.Admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DemandeAdoptionServiceImpl implements DemandeAdoptionService {

    @Autowired
    private DemandeAdoptionRepository demandeAdoptionRepository;

    @Autowired
    private SuiviAdoptionRepository suiviAdoptionRepository;

    @Autowired
    private OrphelinRepository orphelinRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private DonneurRepository donneurRepository;

    // Récupérer toutes les demandes en attente
    @Override
    public List<DemandeAdoption> getDemandesEnAttente() {
        return demandeAdoptionRepository.findByStatut(StatutDemandeAdoption.EN_ATTENTE);
    }
    @Override
    public Orphelin getOrphelinDetaille(Long orphelinId) {
        return orphelinRepository.findById(orphelinId)
                .orElseThrow(() -> new RuntimeException("Orphelin non trouvé"));
    }


    // Soumettre une nouvelle demande d'adoption
    @Override
    public void soumettreDemande(DemandeAdoption demande) {
        // Récupérer l'orphelin associé à la demande
        Orphelin orphelin = orphelinRepository.findById(demande.getOrphelin().getIdo())
                .orElseThrow(() -> new RuntimeException("Orphelin non trouvé"));

        demande.setOrphelin(orphelin);

        // Définir le statut de la demande à "EN_ATTENTE"
        demande.setStatut(StatutDemandeAdoption.EN_ATTENTE);

        // Sauvegarder la demande dans la base de données
        demandeAdoptionRepository.save(demande);

        // **Notification à l'admin**
        createNotificationToAdmin(
                "Une nouvelle demande d'adoption a été soumise pour l'orphelin '" + orphelin.getNom() + "'. Veuillez la traiter.");
    }


    @Override
    public void acceptAll() {
        // Récupérer toutes les demandes en attente
        List<DemandeAdoption> demandesEnAttente = getDemandesEnAttente();

        // Accepter chaque demande d'adoption
        for (DemandeAdoption demande : demandesEnAttente) {
            try {
                accepterDemande(demande.getId());
                System.out.println("Demande d'adoption acceptée pour l'orphelin ID: " + demande.getOrphelin().getIdo());
            } catch (Exception e) {
                System.out.println("Erreur lors de l'acceptation de la demande d'adoption ID: " + demande.getId() + " : " + e.getMessage());
            }
        }
    }


    @Override
    public List<SuiviAdoption> getAdoptionsByDonneur(Long donneurId) {
        // Récupérer les suivis d'adoption pour le donneur spécifié
        return suiviAdoptionRepository.findByDonneurId(donneurId);
    }
    private Donneur getDonneurFromAuthentication(Authentication authentication) {
        String email = authentication.getName(); // ou `getPrincipal()` selon ton UserDetails

        return donneurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Donneur non trouvé"));
    }


    @Override
    public List<DemandeAdoption> getAllDemandes() {
        return demandeAdoptionRepository.findAll();
    }

    // Accepter une demande



    @Override
    public void accepterDemande(Long demandeId) {
        // 1. Récupérer la demande
        DemandeAdoption demande = demandeAdoptionRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        // 2. Mettre à jour le statut de la demande
        demande.setStatut(StatutDemandeAdoption.ACCEPTEE);
        demandeAdoptionRepository.save(demande);

        // 3. Mettre à jour l'orphelin
        Orphelin orphelin = demande.getOrphelin();
        orphelin.setAdopte(true);
        orphelinRepository.save(orphelin);

        // 4. Créer le suivi d'adoption
        SuiviAdoption suivi = new SuiviAdoption();
        suivi.setOrphelin(orphelin);
        suivi.setDonneur(demande.getDonneur());
        suivi.setStatutAdoption(StatutAdoption.EN_COURS);
        suivi.setDateAdoption(LocalDateTime.now());
        suivi.setNotesSuivi("Adoption en cours");
        suiviAdoptionRepository.save(suivi);

        // 5. Supprimer la demande traitée
        demandeAdoptionRepository.delete(demande);

        // 6. Notifications
        // ✅ Donneur : notifier l’acceptation
        createNotificationToDonneur(
                "Votre demande d'adoption pour l'orphelin '" + orphelin.getNom() + "' a été acceptée. L'adoption est maintenant en cours.",
                demande.getDonneur());

        // ✅ Admin : notifier l’action effectuée
      //  createNotificationToAdmin(
            //    "Demande d'adoption #" + demandeId + " acceptée pour le donneur " + demande.getDonneur().getNom() + ".");
    }



    @Override
    public void refuserDemande(Long demandeId) {
        DemandeAdoption demande = demandeAdoptionRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        // Mettre à jour le statut
        demande.setStatut(StatutDemandeAdoption.REFUSEE);
        demandeAdoptionRepository.save(demande);

        // Notification au donneur
        createNotificationToDonneur(
                "Votre demande d'adoption pour l'orphelin '" + demande.getOrphelin().getNom() + "' a été refusée.",
                demande.getDonneur());


    }


    @Override
    public void annulerAdoption(Long suiviId, String raisonAnnulation) {
        SuiviAdoption suivi = suiviAdoptionRepository.findById(suiviId)
                .orElseThrow(() -> new RuntimeException("Suivi d'adoption non trouvé"));

        // Annuler le suivi
        suivi.setStatutAdoption(StatutAdoption.ANNULEE);
        suivi.setNotesSuivi("Adoption annulée. Raison: " + raisonAnnulation);
        suiviAdoptionRepository.save(suivi);

        // Réinitialiser l’orphelin
        Orphelin orphelin = suivi.getOrphelin();
        orphelin.setAdopte(false);
        orphelinRepository.save(orphelin);

        // Notifications
        createNotificationToDonneur(
                "Votre adoption de l'orphelin '" + orphelin.getNom() + "' a été annulée. Raison : " + raisonAnnulation,
                suivi.getDonneur());


    }
    private void createNotificationToAdmin(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(LocalDateTime.now());
        notification.setDestinataireRole("ADMIN");
        notification.setExpediteurRole("ADMIN"); // ou "ADMIN", si c’est un admin qui envoie
        notificationRepository.save(notification);
    }

    private void createNotificationToDonneur(String message, Donneur donneur) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDate(LocalDateTime.now());
        notification.setDestinataireRole("DONNEUR");
        notification.setDestinataireId(donneur.getId());  // Utilisation de l'ID du donneur
        notification.setExpediteurRole("ADMIN");
        notification.setDonneur(donneur);  // Enregistrement du donneur dans la notification
        notificationRepository.save(notification);
    }


    @Override
    public void notifierDonneur(Donneur donneur, String message) {

    }

    @Override
    public void notifierAdmin(String message) {

    }
}
