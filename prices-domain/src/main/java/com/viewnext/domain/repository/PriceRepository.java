package com.viewnext.domain.repository;

import java.util.List;
import java.util.Optional;

import com.viewnext.domain.model.Price;

public interface PriceRepository {
    Optional<Price> findById(Long id);

	void save(Price price);

	List<Price> findAll();
}