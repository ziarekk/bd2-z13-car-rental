package z13.rentivo.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.Client;
import z13.rentivo.entities.Rental;

@Transactional @Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query
    List<Client> findByClientId(Long clientId);

    @Query
    List<Client> findByName(String name);

    @Query
    List<Client> findBySurname(String surname);

    @Query
    List<Client> findByBirthDate(Date birthDate);

    @Query
    List<Client> findByGender(String gender);

    @Query
    List<Client> findByPhoneNumber(String phoneNumber);

    @Query
    List<Client> findByIsVerified(Boolean isVerified);

    @Query(value = "SELECT clnt.* FROM users u JOIN clients clnt ON(clnt.user_id = u.user_id) WHERE u.user_id = :user_id", nativeQuery = true)
    List<Client> findByUserId(@Param("user_id")Long userId);
    @Modifying @Query(value = "INSERT INTO clients (name, surname, birth_date, gender, phone_number, is_verified, user_id) VALUES (:name, :surname, :birth_date, :gender, :phone_number, :is_verified, :user_id)", nativeQuery = true)
    void insertClient(@Param("name") String name,
                      @Param("surname") String surname,
                      @Param("birth_date") Date birthDate,
                      @Param("gender") String gender,
                      @Param("phone_number") String phoneNumber,
                      @Param("is_verified") Boolean isVerified,
                      @Param("user_id") Long userId
                    );


}
