package com.app.pactoapi.database.repositories;

import com.app.pactoapi.database.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends PagingAndSortingRepository<Sale, Long>, JpaRepository<Sale, Long> {

    @Query("""
           SELECT s FROM Sale s
           LEFT JOIN s.payments p
           WHERE (trim(lower(FUNCTION('unaccent', s.description))) LIKE trim(lower(FUNCTION('unaccent', CONCAT('%', :q, '%'))))
           OR :q IS NULL OR :q = '')
           AND (p.transactionId LIKE CONCAT('%', :transactionId, '%') OR :transactionId IS NULL OR :transactionId = '')
           AND (s.user.id = :userId)
           """)
    Page<Sale> findAll(@Param("q") String q, @Param("transactionId") String transactionId,  @Param("userId") Long userId, Pageable pageable);
}