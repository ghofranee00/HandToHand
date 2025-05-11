package com.example.HandToHand.Controller;

import com.example.HandToHand.entite.DemandeInscription;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import com.example.HandToHand.Service.DemandeInscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
//import com.example.HandToHand.Service.EmailService;  // Ajout du service Email

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/demandes")
public class DemandeInscriptionController {

    @Autowired
    private DemandeInscriptionService service;

    //@Autowired
    //private EmailService emailService; // Injection du service Email

    // Endpoint pour ajouter une demande d'inscription
    @PostMapping(value = "/ajouter", consumes = "multipart/form-data")
    public ResponseEntity<?> ajouterDemande(
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("email") String email,
            @RequestParam("pwd") String pwd,
            @RequestParam("pwdconf") String pwdconf,
            @RequestParam("cin") String cin,
            @RequestParam("age") int age,
            @RequestParam("tel") int tel,
            @RequestParam("image") MultipartFile imageFile,
            HttpServletRequest request // ⬅️ Ajouter ceci pour accéder au chemin réel
    ) {
        try {
            String imageName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

            // ✅ Obtenir le chemin absolu depuis le serveur
            String uploadDir = request.getServletContext().getRealPath("/uploads/");
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs(); // Crée le dossier s’il n’existe pas

            // ✅ Sauvegarder l’image dans le dossier
            File dest = new File(dir, imageName);
            imageFile.transferTo(dest);

            // Enregistrer l’objet
            DemandeInscription demande = new DemandeInscription();
            demande.setNom(nom);
            demande.setPrenom(prenom);
            demande.setEmail(email);
            demande.setPwd(pwd);
            demande.setPwdconf(pwdconf);
            demande.setCin(cin);
            demande.setAge(age);
            demande.setTel(tel);
            demande.setImage(imageName); // juste le nom

            DemandeInscription saved = service.ajouterDemande(demande);
            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
    }


    // Endpoint pour approuver une demande
    @PutMapping("/accepter/{id}")
    public ResponseEntity<String> accepterDemande(@PathVariable Long id) {

        // Récupérer la demande après acceptation
        DemandeInscription demande = service.trouverParId(id);

        // Vérifier si la demande existe
        if (demande == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demande non trouvée avec l'ID : " + id);
        }
        service.accepterDemande(id);

        String emailDonneur = demande.getEmail();

        // Envoi de l'e-mail (décommenter quand prêt)
        // emailService.envoyerEmail(
        //         emailDonneur,
        //         "Votre inscription sur HandToHand a été acceptée",
        //         "Bonjour " + demande.getPrenom() + ",\n\n" +
        //         "Nous avons le plaisir de vous informer que votre demande d'inscription a été acceptée.\n" +
        //         "Vous pouvez désormais vous connecter à votre compte.\n\n" +
        //         "Merci et bienvenue parmi nous !\n\n" +
        //         "L'équipe HandToHand."
        // );

        return ResponseEntity.ok("Demande approuvée et ajoutée à la table Donneur !");
    }


    // Méthode pour refuser une demande d'inscription
    @DeleteMapping("/refuser/{id}")
    public ResponseEntity<String> refuserDemande(@PathVariable Long id) {
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
