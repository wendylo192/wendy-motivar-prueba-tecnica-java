package com.viewnext.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viewnext.infrastructure.entity.PriceEntity;

public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {
}
