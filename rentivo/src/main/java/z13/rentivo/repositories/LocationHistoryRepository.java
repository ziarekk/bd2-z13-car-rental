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
}