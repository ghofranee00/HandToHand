package com.example.HandToHand.Service;

import com.example.HandToHand.entite.Donation;
import com.example.HandToHand.entite.Orphelin;
import com.example.HandToHand.repository.Admin.AdminRepository;
import com.example.HandToHand.repository.Admin.DonationRepository;
import com.example.HandToHand.repository.Admin.OrphelinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceEmpl implements  AdminService{
    @Autowired
    DonationRepository donationRepository;
    @Autowired
    OrphelinRepository orphelinRepository ;
    @Override
    public Orphelin ajouterOrphelin(Orphelin orphelin) {
         return orphelinRepository .save(orphelin) ;

    }
    @Override
    public Orphelin ModifierAdmin(Orphelin orphelin) {

        return orphelinRepository.save(orphelin);
    }
    @Override
    public void SupprimerAdmin(Long id) {

        orphelinRepository.deleteById(id);
    }
    @Override
    public List<Orphelin> AfficherAdmin() {

        return orphelinRepository.findAll();
    }
    @Override
    public void supprimerDonation(Long id) {
        donationRepository.deleteById(id);
    }
    @Override
    public List<Donation> consulterDons() {
        return donationRepository.findAll();
    }

}
