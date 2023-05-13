package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Rental;

@Transactional @Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    @Query
    List<Rental> findByRentalId(Long rentalId);

    @Modifying
    @Query(value = "INSERT INTO rentals (rental_id, client_id, car_id, start_id) VALUES (:rental_id, :client_id, :car_id, :start_id)", nativeQuery = true)
    void insertRental(@Param("rental_id") Long rentalId, 
                      @Param("client_id") Long clientId, 
                      @Param("car_id") Long carId,
                      @Param("start_id") Long startId);

    @Modifying
    @Query(value = "INSERT INTO rentals (rental_id, client_id, car_id, start_id, end_id) VALUES (:rental_id, :client_id, :car_id, :start_id, :end_id)", nativeQuery = true)
    void insertRental(@Param("rental_id") Long rentalId, 
                    @Param("client_id") Long clientId, 
                    @Param("car_id") Long carId,
                    @Param("start_id") Long startId,
                    @Param("end_id") Long endId);
}