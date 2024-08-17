package com.viewnext.infrastructure.adapter.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.viewnext.application.port.out.LoadPricesPort;
import com.viewnext.application.port.out.PriceRepositoryPort;
import com.viewnext.domain.constants.PriceConstants;
import com.viewnext.domain.model.Price;
import com.viewnext.infrastructure.entity.PriceEntity;
import com.viewnext.infrastructure.mapper.PriceMapper;
import com.viewnext.infrastructure.repository.PriceJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class PriceRepositoryAdapter implements PriceRepositoryPort, LoadPricesPort {

    @PersistenceContext
    private EntityManager entityManager;
	
	private final PriceJpaRepository priceJpaRepository;
    private final PriceMapper priceMapper;

    public PriceRepositoryAdapter(PriceJpaRepository priceJpaRepository, PriceMapper priceMapper) {
        this.priceJpaRepository = priceJpaRepository;
        this.priceMapper = priceMapper;
    }

    @Override
    public Optional<Price> findById(Long id) {
        return priceJpaRepository.findById(id)
                .map(priceMapper::toDomain);
    }

    @Override
    public List<Price> findAll() {
        return priceJpaRepository.findAll().stream()
                .map(priceMapper::toDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime fechaAplicacion) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PriceEntity> cq = cb.createQuery(PriceEntity.class);
        Root<PriceEntity> price = cq.from(PriceEntity.class);

        Predicate productIdPredicate = cb.equal(price.get(PriceConstants.PRODUCT_ID), productId);
        Predicate brandIdPredicate = cb.equal(price.get(PriceConstants.BRAND_ID), brandId);
        Predicate datePredicate = cb.between(cb.literal(fechaAplicacion), price.get(PriceConstants.START_DATE), price.get(PriceConstants.END_DATE));

        cq.where(cb.and(productIdPredicate, brandIdPredicate, datePredicate));
        cq.orderBy(cb.desc(price.get(PriceConstants.PRIORITY)));

        TypedQuery<PriceEntity> query = entityManager.createQuery(cq);
        query.setMaxResults(1);

        return query.getResultStream()
                .findFirst()
                .map(priceMapper::toDomain);
    }

}