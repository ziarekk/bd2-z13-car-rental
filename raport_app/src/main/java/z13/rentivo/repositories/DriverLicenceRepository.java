package z13.rentivo.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.DriverLicence;

@Transactional @Repository
public interface DriverLicenceRepository extends JpaRepository<DriverLicence, Long> {
    @Query
    List<DriverLicence> findByLicenceId(Long licenceId);

    @Query
    List<DriverLicence> findByNumber(String number);

    @Query
    List<DriverLicence> findByDateObtained(Date dateObtained);

    @Query
    List<DriverLicence> findByExpirationDate(Date expirationDate);

    @Query
    List<DriverLicence> findByCategory(String category);

    @Modifying
    @Query(value = "INSERT INTO driver_licence (number, date_obtained, expiration_date, category, cleint_id) VALUES (:number, :date_obtained, :expiration_date, :category, :client_id)", nativeQuery = true)
    void insertDriverLicence(@Param("number") String number, 
                             @Param("date_obtained") Date dateObtained, 
                             @Param("expiration_date") Date expirationDate,
                             @Param("category") String category,
                             @Param("client_id") Long clientId);
}