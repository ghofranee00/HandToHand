package com.example.HandToHand.Controller;

import com.example.HandToHand.Service.DonneurService;
import com.example.HandToHand.entite.Donneur;
import com.example.HandToHand.repository.Admin.DonneurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/donneurs")
public class DonneurController {

    @Autowired
    private DonneurService donneurService;

    @Autowired
    private DonneurRepository donneurRepo;

    // Endpoint pour login du donneur
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginDonneur(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("pwd");

        Map<String, Object> response = donneurService.loginDonneur(email, password);
        if (response.containsKey("message")) {
            return ResponseEntity.status(404).body(response); // Retourne 404 si l'utilisateur n'est pas trouvé ou mot de passe incorrect
        }
        return ResponseEntity.status(200).body(response); // Retourne 200 avec le token si tout est correct
    }

    // Endpoint pour obtenir le profil du donneur connecté
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Map<String, Object> profile = donneurService.getProfileByEmail(email);
        if (profile == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(profile);
    }

    @GetMapping("/profile-from-token")
    public ResponseEntity<Map<String, Object>> getProfileFromToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token manquant ou invalide"));
        }

        String token = authHeader.substring(7);
        try {
            Map<String, Object> profile = donneurService.getProfileFromToken(token);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token invalide"));
        }
    }

    // Endpoint pour logout du donneur
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // stratégie de révocation du token (si nécessaire)
            return ResponseEntity.ok("Logout successful");
        }
        return ResponseEntity.badRequest().body("No token found");
    }

    @GetMapping("/donneurs")
    public ResponseEntity<List<Donneur>> getAllDonneurs() {
        List<Donneur> donneurs = donneurService.getAllDonneurs();
        return ResponseEntity.ok(donneurs);
    }}