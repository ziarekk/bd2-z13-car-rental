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
import z13.rentivo.entities.Rental;

@Transactional @Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query
    List<Bill> findByBillId(Long billId);

    @Query
    List<Bill> findByAmount(Float amount);

    @Query
    List<Bill> findByDateDue(Date dateDue);

    @Query
    List<Bill> findByRental(Rental rental);

    @Modifying @Query(value = "INSERT INTO bills (amount, date_due, rental_id) VALUES (:amount, :date_due, :rental_id)", nativeQuery = true)
    void insertBill(@Param("amount") Float amount, 
                    @Param("date_due") Date dateDue, 
                    @Param("rental_id") Long rentalId);
}
