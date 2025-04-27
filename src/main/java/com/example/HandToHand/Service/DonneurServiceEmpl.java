package com.example.HandToHand.Service;

import com.example.HandToHand.Service.DonneurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HandToHand.entite.Donneur;
import com.example.HandToHand.repository.Admin.DonneurRepository;

import java.util.Optional;

@Service
public class DonneurServiceEmpl implements DonneurService {


    @Autowired
    private DonneurRepository donneurRepository;

    //@Override
    //public void ajouterDonneur(Donneur donneur) {
        // Comparer pwd et pwdconf
      //  if (!donneur.getPwd().equals(donneur.getPwdconf())) {
        //    throw new IllegalArgumentException("Les mots de passe ne correspondent pas.");
        //}

        // Hasher les mots de passe
       // donneur.setPwd(passwordEncoder.encode(donneur.getPwd()));
       // donneur.setPwdconf(donneur.getPwdconf()); // Il n'est pas nécessaire de hasher pwdconf car il est seulement utilisé pour la vérification

       // donneurRepository.save(donneur);
    //}

  //  @Override
 //   public Donneur login(String email, String pwd) {
    //    Donneur donneur = donneurRepository.findByEmail(email).orElse(null);

      //  if (donneur != null && passwordEncoder.matches(pwd, donneur.getPwd())) {
         //   return donneur;
      //  }
     //   return null;
   // }

    @Override
    public Optional<Donneur> findByEmail(String email) {
        return Optional.empty();
    }
}
