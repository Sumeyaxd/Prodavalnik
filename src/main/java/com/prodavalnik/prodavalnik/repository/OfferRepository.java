package com.prodavalnik.prodavalnik.repository;

import com.prodavalnik.prodavalnik.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

}
