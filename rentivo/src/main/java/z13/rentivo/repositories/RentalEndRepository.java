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

import z13.rentivo.entities.RentalEnd;

@Transactional @Repository
public interface RentalEndRepository extends JpaRepository<RentalEnd, Long> {
    @Query
    List<RentalEnd> findByEndId(Long endId);

    @Query
    List<RentalEnd> findByEndTime(Date endTime);

    @Query
    List<RentalEnd> findByEndMileage(Float endMileage);
}