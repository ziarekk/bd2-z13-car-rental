package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Bill;
import z13.rentivo.entities.Payment;

@Transactional @Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query
    List<Payment> findByPaymentId(Long paymentId);

    @Query
    List<Payment> findByStatus(String status);

    @Query
    List<Payment> findByBill(Bill bill);

    @Modifying
    @Query(value = "INSERT INTO payments (status, bill_id, type_id) VALUES (:status, :bill_id, :type_id)", nativeQuery = true)
    void insertPayment(@Param("status") String status,
                       @Param("bill_id") Long billId,
                       @Param("type_id") Long typeId);

    @Query(value = "SELECT COUNT(p) FROM payments p WHERE p.status = 'przyjeta'", nativeQuery = true)
    Long getPaidCount();

    @Query(value = "SELECT COUNT(p) FROM payments p WHERE p.status = 'oczekujaca'", nativeQuery = true)
    Long getPendingCount();

    @Query(value = "SELECT COUNT(p) FROM payments p WHERE p.status = 'odrzucona'", nativeQuery = true)
    Long getDeclinedCount();

    @Query(value = "SELECT COUNT(p) FROM payments p", nativeQuery = true)
    Long getCount();

    @Query(value = "SELECT SUM(b.amount) FROM payments p JOIN bills b ON p.bill_id = b.bill_id WHERE p.status = 'przyjeta'", nativeQuery = true)
    Float getPaidAmount();

    @Query(value = "SELECT SUM(b.amount) FROM payments p JOIN bills b ON p.bill_id = b.bill_id WHERE p.status = 'oczekujaca'", nativeQuery = true)
    Float getPendingAmount();

    @Query(value = "SELECT SUM(b.amount) FROM payments p JOIN bills b ON p.bill_id = b.bill_id WHERE p.status = 'odrzucona'", nativeQuery = true)
    Float getDeclinedAmount();

    @Query(value = "SELECT COUNT(p) " +
            "FROM payments p JOIN bills b ON p.bill_id = b.bill_id " +
            "WHERE p.status = :status AND EXTRACT(MONTH FROM b.date_due) = :month " +
            "GROUP BY DATE_TRUNC('month', b.date_due) " +
            "ORDER BY DATE_TRUNC('month', b.date_due) ", nativeQuery = true)
    Long getCountByMonth(@Param("month") Integer month,
                                   @Param("status") String status);

    @Query(value = "SELECT SUM(b.amount) " +
            "FROM payments p JOIN bills b ON p.bill_id = b.bill_id " +
            "WHERE p.status = :status AND EXTRACT(MONTH FROM b.date_due) = :month " +
            "GROUP BY DATE_TRUNC('month', b.date_due) " +
            "ORDER BY DATE_TRUNC('month', b.date_due) ", nativeQuery = true)
    Long getAmountByMonth(@Param("month") Integer month,
                         @Param("status") String status);
}