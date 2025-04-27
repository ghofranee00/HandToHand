package com.example.HandToHand.repository.Admin;

import com.example.HandToHand.entite.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository <Donation, Long>{
}
