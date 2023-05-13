package z13.rentivo.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Bill;

@Transactional @Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query
    List<Bill> findByUserId(Long userId);

    @Query
    List<Bill> findByAmount(Float amount);

    @Query
    List<Bill> findByDateDue(Date dateDue);

    @Modifying @Query(value = "INSERT INTO bills (amount, date_due, payment_id) VALUES (:amount, :dateDue, :paymentId)", nativeQuery = true)
    void insert(@Param("amount") Float amount, 
                @Param("date_due") Date dateDue, 
                @Param("payment_id") Long paymentId);

}
