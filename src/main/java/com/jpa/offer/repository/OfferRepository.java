package com.jpa.offer.repository;

import com.jpa.offer.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long>, OfferRepositoryCustom{
}
