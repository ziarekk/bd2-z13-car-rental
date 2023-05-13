package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.PaymentType;

@Transactional @Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    @Query
    List<PaymentType> findByTypeId(Long typeId);

    @Query
    List<PaymentType> findByName(String name);

    @Modifying
    @Query(value = "INSERT INTO payment_type (name) VALUES (:name)", nativeQuery = true)
    void insertPaymentType(@Param("name") String name);
}