package com.example.HandToHand.Service;

import com.example.HandToHand.entite.Donation;
import com.example.HandToHand.entite.Orphelin;

import java.util.List;

public interface AdminService {
    Orphelin ajouterOrphelin(Orphelin orphelin);
    public Orphelin ModifierAdmin(Orphelin orphelin) ;
    public void SupprimerAdmin(Long id) ;
    public List<Orphelin> AfficherAdmin() ;
    public void supprimerDonation(Long id) ;
    List<Donation> consulterDons();
    }
