package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Discount;
import z13.rentivo.entities.Rental;

@Transactional @Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    @Query
    List<Discount> findByDiscountId(Long discountId);

    @Query
    List<Discount> findByPercent(Float percent);

    @Query
    List<Discount> findByDescription(String description);


    @Modifying
    @Query(value = "INSERT INTO discount (percent, description, rental_id) VALUES (:percent, :description, :rental_id)", nativeQuery = true)
    void insertDiscount(@Param("percent") Float percent, 
                        @Param("description") String description, 
                        @Param("rental_id") Long rentalId);
}