package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Segment;

@Transactional @Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
    @Query
    List<Segment> findBySegmentId(Long segmentId);

    @Query
    List<Segment> findByName(String segmentName);

    @Query
    List<Segment> findByRentalFee(Float rentalFee);

    @Query
    List<Segment> findByKmRate(Float kmRate);

    @Query
    List<Segment> findByHourRate(Float hourRate);

    @Modifying
    @Query(value = "INSERT INTO segments (name, rental_fee, km_rate, hour_rate) VALUES (:name, :rental_fee, :km_rate, :hour_rate)", nativeQuery = true)
    void insertSegment(@Param("name") String name, 
                       @Param("rental_fee") Float rentalFee, 
                       @Param("km_rate") Float kmRate, 
                       @Param("hour_rate") Float hourRate);
}