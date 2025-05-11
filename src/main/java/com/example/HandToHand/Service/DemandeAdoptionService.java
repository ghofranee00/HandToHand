package com.example.HandToHand.Service;

import com.example.HandToHand.entite.DemandeAdoption;
import com.example.HandToHand.entite.Donneur;

import java.util.List;

public interface DemandeAdoptionService {

    List<DemandeAdoption> getDemandesEnAttente();

    List<DemandeAdoption> getAllDemandes();

    void accepterDemande(Long demandeId);

    void refuserDemande(Long demandeId);

    void notifierDonneur(Donneur donneur, String message);

    void notifierAdmin(String message);

    void soumettreDemande(DemandeAdoption demandeAdoption);
}
