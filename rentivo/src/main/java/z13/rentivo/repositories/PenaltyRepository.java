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

import z13.rentivo.entities.Penalty;

@Transactional @Repository
public interface PenaltyRepository extends JpaRepository<Penalty, Long> {
    @Query
    List<Penalty> findByPenaltyId(Long penaltyId);

    @Query
    List<Penalty> findByAmount(Float amount);

    @Query
    List<Penalty> findByDescription(String description);
}