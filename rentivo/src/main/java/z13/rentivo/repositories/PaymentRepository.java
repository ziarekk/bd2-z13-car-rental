package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Client;
import z13.rentivo.entities.Payment;

@Transactional @Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query
    List<Payment> findByPaymentId(Long paymentId);

    @Query
    List<Payment> findByStatus(String status);

    @Query(value = "SELECT pay.* FROM payments pay JOIN bills b on (pay.bill_id = b.bill_id) WHERE b.bill_id = :bill_id", nativeQuery = true)
    List<Payment> findByBillId(@Param("bill_id")Long billId);

    @Modifying
    @Query(value = "INSERT INTO payments (status, bill_id, type_id) VALUES (:status, :bill_id, :type_id)", nativeQuery = true)
    void insertPayment(@Param("status") String status,
                       @Param("bill_id") Long billId,
                       @Param("type_id") Long typeId);
    @Modifying
    @Query(value = "UPDATE payments SET status =:status WHERE payment_id = :payment_id", nativeQuery = true)
    void updateStatus(@Param("payment_id") Long paymentId,
                      @Param("status") String status);

}