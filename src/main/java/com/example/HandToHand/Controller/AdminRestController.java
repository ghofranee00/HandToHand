package com.example.HandToHand.Controller;

import com.example.HandToHand.Service.AdminService;
import com.example.HandToHand.entite.Donation;
import com.example.HandToHand.entite.Orphelin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/orphelins")
public class AdminRestController {

        @Autowired
        private AdminService adminService;

        // 1. Créer un orphelin
        @PostMapping
        public Orphelin creer(@RequestBody Orphelin orphelin) {
            return adminService.ajouterOrphelin(orphelin);
        }

        // 2. Modifier un orphelin
        @PutMapping("/{id}")
        public Orphelin modifier(
                @PathVariable Long id,
                @RequestBody Orphelin orphelin)
        {
            // s'assurer que l'ID est correct
            orphelin.setIdo(id);
            return adminService.ModifierAdmin(orphelin);
        }

        @DeleteMapping("/{id}")
        public void supprimer(@PathVariable Long id) {
            adminService.SupprimerAdmin(id);
        }

        // 4. Lister tous les orphelins
        @GetMapping
        public Object lister() {
            List<Orphelin> liste = adminService.AfficherAdmin();
            if (liste.isEmpty()) {
                return "Pas d'orphelin";
            }
            return liste;
        }
        @DeleteMapping("/supprimer/{id}")
        public String supprimerDonation(@PathVariable Long id) {
            adminService.supprimerDonation(id);
            return "Le don avec l'ID " + id + " a été supprimé.";
        }

        @GetMapping("/consulter")
        public List<Donation> consulterDons() {
            return adminService.consulterDons();
        }
}
