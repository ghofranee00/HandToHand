package com.example.HandToHand.Service;

import com.example.HandToHand.entite.DemandeInscription;
import java.util.List;

public interface DemandeInscriptionService {

    DemandeInscription ajouterDemande(DemandeInscription demande);

    List<DemandeInscription> getAllDemandes();

    void refuserDemande(Long id);

    void accepterDemande(Long id);
    void refuserAllDemandes();

    // Accepter toutes les demandes
    void accepterAllDemandes();
    DemandeInscription trouverParEmail(String email);
}
