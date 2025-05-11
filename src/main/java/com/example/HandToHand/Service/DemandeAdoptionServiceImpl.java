package com.example.HandToHand.Service;

import com.example.HandToHand.entite.*;
import com.example.HandToHand.repository.Admin.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    private NotificationRepository notificationRepository;

    @Autowired
    private OrphelinRepository orphelinRepository;

    @Autowired
    private DonneurRepository donneurRepository;

    // Récupérer toutes les demandes en attente
    @Override
    public List<DemandeAdoption> getDemandesEnAttente() {
        return demandeAdoptionRepository.findByStatut(StatutDemandeAdoption.EN_ATTENTE);
    }

    // Soumettre une nouvelle demande d'adoption
    public void soumettreDemande(DemandeAdoption demande) {
        // Assigner le statut avec l'énumération
        demande.setStatut(StatutDemandeAdoption.EN_ATTENTE);
        demandeAdoptionRepository.save(demande);

        // Créer une notification pour l'admin
        Notification notifAdmin = new Notification(
                null, // id (généralement auto-généré par la base de données, donc tu peux mettre null)
                "Nouvelle demande d’adoption de " + demande.getDonneur().getNom(),
                false, // isSeen (par défaut, la notification n'a pas été vue)
                LocalDateTime.now(), // date actuelle
                "ADMIN", // rôle destinataire
                null // destinataireId (si tu veux envoyer la notification à un admin spécifique, sinon laisse null)
        );

        // Sauvegarder la notification dans la base de données
        notificationRepository.save(notifAdmin);
    }

    // Récupérer toutes les demandes
    @Override
    public List<DemandeAdoption> getAllDemandes() {
        return demandeAdoptionRepository.findAll();
    }

    // Accepter une demande d'adoption
    @Override
    public void accepterDemande(Long demandeId) {
        // Récupérer la demande d'adoption
        DemandeAdoption demande = demandeAdoptionRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        // Mettre à jour le statut de la demande
        demande.setStatut(StatutDemandeAdoption.ACCEPTEE);
        demandeAdoptionRepository.save(demande); // Sauvegarder la demande avec le statut "ACCEPTEE"

        // Créer et sauvegarder le suivi de l'adoption
        SuiviAdoption suivi = new SuiviAdoption();
        suivi.setOrphelin(demande.getOrphelin());
        suivi.setDonneur(demande.getDonneur());
        suivi.setStatutAdoption(StatutAdoption.EN_COURS); // Statut initial de l'adoption
        suivi.setDateAdoption(LocalDateTime.now());
        suivi.setNotesSuivi("Adoption en cours");
        suiviAdoptionRepository.save(suivi); // Sauvegarder dans la table de suivi

        // Supprimer la demande de la table DemandeAdoption
        demandeAdoptionRepository.delete(demande);

        // Notification pour le donneur (seul le donneur est notifié)
        notifierDonneur(demande.getDonneur(), "Votre demande d'adoption a été acceptée et est maintenant en cours.");
    }

    // Méthode pour notifier le donneur avec la notification dans la base de données
    public void notifierDonneur(Donneur donneur, String message) {
        // Création de la notification
        Notification notification = new Notification(
                null, // id généré automatiquement
                message,
                false, // Par défaut, la notification n'a pas été vue
                LocalDateTime.now(), // Date de la notification
                "DONNEUR", // Rôle destinataire
                donneur.getId() // ID du donneur
        );
        notificationRepository.save(notification); // Sauvegarder la notification dans la base de données
    }

    @Override
    public void notifierAdmin(String message) {

    }

    // Refuser une demande d'adoption
    @Override
    public void refuserDemande(Long demandeId) {
        DemandeAdoption demande = demandeAdoptionRepository.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        // Mettre à jour le statut de la demande
        demande.setStatut(StatutDemandeAdoption.REFUSEE); // Correction : utiliser le statut approprié
        demandeAdoptionRepository.save(demande); // Sauvegarder la demande avec le statut "REFUSEE"
        demandeAdoptionRepository.delete(demande); // Suppression de la demande après le refus

        // Notification pour le donneur uniquement (l'administrateur ne reçoit aucune notification)
        notifierDonneur(demande.getDonneur(), "Votre demande d'adoption a été refusée.");
    }
}
