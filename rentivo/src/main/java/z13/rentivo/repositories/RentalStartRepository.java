package z13.rentivo.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.RentalStart;

@Transactional @Repository
public interface RentalStartRepository extends JpaRepository<RentalStart, Long> {
    @Query
    List<RentalStart> findByStartId(Long startId);

    @Query
    List<RentalStart> findByStartTime(Date startTime);

    @Query
    List<RentalStart> findByStartMileage(Float startMileage);

    @Modifying
    @Query(value = "INSERT INTO rental_start (start_time, start_mileage, rental_id) VALUES (:start_time, :start_mileage, :rental_id)", nativeQuery = true)
    void insertRentalStart(@Param("start_time") Date startTime, 
                           @Param("start_mileage") Float startMileage,
                           @Param("rental_id") Long rentalId);
}