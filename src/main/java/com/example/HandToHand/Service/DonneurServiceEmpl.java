package com.example.HandToHand.Service;

import com.example.HandToHand.entite.Donneur;
import com.example.HandToHand.repository.Admin.DonneurRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
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
                        "email", donneur.getEmail(),
                        "nom", donneur.getNom(),
                        "prenom", donneur.getPrenom()
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
                    "image", donneur.getImage()
            );
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Session expirée. Veuillez vous reconnecter");
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Token invalide: " + e.getMessage());
        }
    }

    @Override
    public void logout(String token) {
        blacklistedTokens.add(token);
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
        Optional<Donneur> donneur = donneurRepo.findByEmail(email);

        if (donneur.isEmpty()) {
            return null;
        }

        Map<String, Object> profile = new HashMap<>();
        profile.put("email", donneur.get().getEmail());
        profile.put("name", donneur.get().getNom());
        profile.put("Prenom", donneur.get().getPrenom());
        profile.put("tel", donneur.get().getTel());
        profile.put("cin", donneur.get().getCin());
        profile.put("image", donneur.get().getImage());

        return profile;
    }

    @Override
    public String getEmailFromToken(String token) {
        return "";
    }


}