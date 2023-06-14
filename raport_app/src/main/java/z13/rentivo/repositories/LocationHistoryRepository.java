package z13.rentivo.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.LocationHistory;

@Transactional @Repository
public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long> {
    @Query
    List<LocationHistory> findByLocationId(Long locationId);

    @Query
    List<LocationHistory> findByLongitude(Float longitude);

    @Query
    List<LocationHistory> findByLatitude(Float latitude);

    @Query
    List<LocationHistory> findByRegistrationTimestamp(Date registrationTimestamp);

    @Modifying 
    @Query(value = "INSERT INTO location_history (longitude, latitude, registration_timestamp, car_id) VALUES (:longitude, :latitude, :registration_timestamp, :car_id)", nativeQuery = true)
    void insertLocationHistory(@Param("longitude") Float longitude, 
                               @Param("latitude") Float latitude, 
                               @Param("registration_timestamp") Date registrationTimestamp,
                               @Param("car_id") Long carId);

    @Query(value = "SELECT * FROM location_history WHERE car_id = :car_id", nativeQuery = true)
    List<LocationHistory> findByCarID(@Param("car_id") Long carId);
}