package z13.rentivo.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.RentalEnd;

@Transactional @Repository
public interface RentalEndRepository extends JpaRepository<RentalEnd, Long> {
    @Query
    List<RentalEnd> findByEndId(Long endId);

    @Query
    List<RentalEnd> findByEndTime(Date endTime);

    @Query
    List<RentalEnd> findByEndMileage(Float endMileage);

    @Modifying
    @Query(value = "INSERT INTO rental_end (end_time, end_mileage, rental_id) VALUES (:end_time, :end_mileage, :rental_id)", nativeQuery = true)
    void insertRentalEnd(@Param("end_time") Date endTime, 
                         @Param("end_mileage") Float endMileage,
                         @Param("rental_id") Long rentalId);
}