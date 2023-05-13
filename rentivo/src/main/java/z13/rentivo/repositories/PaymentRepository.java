package z13.rentivo.repositories;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Payment;

@Transactional @Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query
    List<Payment> findByPaymentId(Long paymentId);

    @Query
    List<Payment> findByStatus(String status);
}