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
       SELECT DISTINCT s FROM Sale s LEFT JOIN s.payments p
       WHERE s.user.id = :userId AND (
           trim(lower(FUNCTION('unaccent', s.description))) LIKE trim(lower(FUNCTION('unaccent', CONCAT('%', :q, '%'))))
           OR :#{#q == null} = TRUE OR :q = ''
       )
       AND (p.transactionId LIKE CONCAT('%', :transactionId, '%') OR :#{#transactionId == null} = TRUE OR :transactionId = '')
       """)
    Page<Sale> findAll(@Param("q") String q, @Param("transactionId") String transactionId,  @Param("userId") Long userId, Pageable pageable);
}