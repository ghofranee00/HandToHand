package com.example.HandToHand.Service;

import com.example.HandToHand.entite.Donneur;
import com.example.HandToHand.repository.Admin.DonneurRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class DonneurServiceEmpl implements DonneurService {

    private DonneurService donneurService;

    @Autowired
    private DonneurRepository donneurRepo;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final Set<String> blacklistedTokens = Collections.synchronizedSet(new HashSet<>());

    // Supprimez cette ligne qui crée une clé aléatoire
    // private final SecretKey jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private SecretKey getJwtSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Map<String, Object> loginDonneur(String email, String pwd) {
        Donneur donneur = donneurRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou mot de passe incorrect"));

        if (!passwordEncoder.matches(pwd, donneur.getPwd())) {
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        String token = Jwts.builder()
                .setSubject(donneur.getEmail())
                .claim("userId", donneur.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24h
                .signWith(getJwtSecretKey(), SignatureAlgorithm.HS256)
                .compact();

        return Map.of(
                "token", token,
                "user", Map.of(
                        "id", donneur.getId(),
                        "nom", donneur.getNom(),
                        "prenom", donneur.getPrenom(),
                        "email", donneur.getEmail(),
                        "cin", donneur.getCin(),
                        "tel", donneur.getTel(),
                        "image", donneur.getImage(),
                        "age", donneur.getAge()
                )
        );
    }

    @Override
    public Map<String, Object> getProfileFromToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getJwtSecretKey())
                    .build()
                    .parseClaimsJws(token);

            if (isTokenBlacklisted(token)) {
                throw new RuntimeException("Session terminée. Veuillez vous reconnecter");
            }

            Claims claims = claimsJws.getBody();
            Donneur donneur = donneurRepo.findById(claims.get("userId", Long.class))
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            return Map.of(
                    "id", donneur.getId(),
                    "nom", donneur.getNom(),
                    "prenom", donneur.getPrenom(),
                    "email", donneur.getEmail(),
                    "cin", donneur.getCin(),
                    "tel", donneur.getTel(),
                    "image", donneur.getImage(),
                    "age", donneur.getAge()
            );
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Session expirée. Veuillez vous reconnecter");
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Token invalide: " + e.getMessage());
        }
    }
    public Donneur updateProfile(String email, Map<String, Object> updateRequest) {
        Donneur donneur = donneurRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        donneur.setNom((String) updateRequest.get("nom"));
        donneur.setPrenom((String) updateRequest.get("prenom"));
        donneur.setCin((String) updateRequest.get("cin"));
        // Récupère le téléphone sous forme de String
        String tel = (String) updateRequest.get("tel");

        // Assure-toi que le numéro de téléphone respecte le format
        if (!tel.matches("^\\+?\\d{9,15}$")) {
            throw new RuntimeException("Le numéro de téléphone doit être valide (ex: +21612345678)");
        }        donneur.setImage((String) updateRequest.get("image"));
        donneur.setAge(Integer.parseInt(updateRequest.get("age").toString()));

        return donneurRepo.save(donneur);
    }


    public void updatePassword(String email, String currentPassword, String newPassword, String confirmPassword) {
        Donneur donneur = donneurRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(currentPassword, donneur.getPwd())) {
            throw new RuntimeException("Mot de passe actuel incorrect");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Le nouveau mot de passe et la confirmation ne correspondent pas");
        }

        donneur.setPwd(passwordEncoder.encode(newPassword));
        donneurRepo.save(donneur);
    }

    @Override
    public Donneur getDonneurById(Long donneurId) {
        return null;
    }


    @Override
    public void logout(String token) {
        // Ajouter le jeton à la liste noire
        blacklistedTokens.add(token);

        // Réinitialiser le contexte de sécurité
        SecurityContextHolder.clearContext();
    }


    @Override
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    @Override
    public List<Donneur> getAllDonneurs() {
        return donneurRepo.findAll();
    }

    public Map<String, Object> getProfileByEmail(String email) {
        Donneur donneur = donneurRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return Map.of(
                "id", donneur.getId(),
                "nom", donneur.getNom(),
                "prenom", donneur.getPrenom(),
                "email", donneur.getEmail(),
                "cin", donneur.getCin(),
                "tel", donneur.getTel(),
                "image", donneur.getImage(),
                "age", donneur.getAge()
        );
    }


    @Override
    public String getEmailFromToken(String token) {
        return "";
    }


}