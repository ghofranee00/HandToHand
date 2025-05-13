package com.example.HandToHand.Service;

import com.example.HandToHand.entite.DemandeInscription;
import com.example.HandToHand.entite.Notification;
import com.example.HandToHand.repository.Admin.DemandeInscriptionRepository;
import com.example.HandToHand.entite.Donneur;
import com.example.HandToHand.repository.Admin.DonneurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DemandeInscriptionServiceEmpl implements DemandeInscriptionService {

    @Autowired
    private DemandeInscriptionRepository repo;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DonneurRepository donneurRepo;

    @Override
    public DemandeInscription ajouterDemande(DemandeInscription demande) {
        // Vérifier si l'email existe déjà chez les donneurs
        if (donneurRepo.findByEmail(demande.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Cet email est déjà utilisé par un donneur.");
        }

        // Vérifier que les mots de passe correspondent
        if (!demande.getPwd().equals(demande.getPwdconf())) {
            throw new IllegalArgumentException("Les mots de passe ne correspondent pas.");
        }

        // Crypter les mots de passe
        String encryptedPwd = passwordEncoder.encode(demande.getPwd());
        String encryptedPwdConf = passwordEncoder.encode(demande.getPwdconf());
        demande.setPwd(encryptedPwd);
        demande.setPwdconf(encryptedPwdConf);

        // Sauvegarder la demande d'inscription dans la base de données
        DemandeInscription savedDemande = repo.save(demande);

        // Créer une notification pour l'admin



        return savedDemande;
    }
    private void createNotification(String message, String role) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setDestinataireRole(role);
        notification.setDate(LocalDateTime.now());

    }








    @Override
    public List<DemandeInscription> getAllDemandes() {
        return repo.findAll();
    }

    @Override
    public void refuserDemande(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void refuserAllDemandes() {
        repo.deleteAll();
    }

    @Override
    public void accepterDemande(Long id) {
        DemandeInscription demande = repo.findById(id).orElse(null);
        if (demande != null) {
            demande.setApprouvee(true);

            Donneur donneur = new Donneur();
            donneur.setNom(demande.getNom());
            donneur.setPrenom(demande.getPrenom());
            donneur.setEmail(demande.getEmail());
            donneur.setPwd(demande.getPwd());
            donneur.setPwdconf(demande.getPwdconf());
            donneur.setImage(demande.getImage());
            donneur.setCin(demande.getCin());
            donneur.setAge(demande.getAge());
            donneur.setTel(String.valueOf(demande.getTel()));

            donneurRepo.save(donneur); // Enregistrer le donneur
            repo.deleteById(id); // Supprimer la demande d'inscription

            // Envoyer un email de confirmation
            // Si tu veux envoyer un email de confirmation, tu peux appeler une méthode d'email ici
        }
    }

    @Override
    public void accepterAllDemandes() {
        List<DemandeInscription> demandes = repo.findAll();
        for (DemandeInscription demande : demandes) {
            accepterDemande(demande.getId());
        }
    }

    @Override
    public DemandeInscription trouverParId(Long id) {
        return repo.findById(id).orElse(null); // Utilisation correcte de findById
    }

    @Override
    public DemandeInscription trouverParEmail(String email) {
        return repo.findByEmail(email); // Assure-toi que cette méthode existe dans le repository
    }
}
