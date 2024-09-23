package com.app.pactoapi.database.repositories;

import com.app.pactoapi.database.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllBySaleId(Long saleId);
}
