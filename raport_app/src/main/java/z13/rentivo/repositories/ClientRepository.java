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
import z13.rentivo.querries.IClientsGendersCount;
import z13.rentivo.querries.IClientsHighestPenaltySum;
import z13.rentivo.querries.IClientsMostRentalsCount;

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

    @Query(value = "select c.name as name, c.surname as surname, count(r.rental_id) as totalCount\n" +
            "from clients c\n" +
            "    join rentals r on c.client_id = r.client_id\n" +
            "group by c.name, c.surname\n" +
            "order by totalCount desc\n" +
            "limit 10;", nativeQuery = true)
    List<IClientsMostRentalsCount> getTenClientsWithMostRentals();

    @Query(value = "select c.name as name, c.surname as surname, sum(p.amount) as penaltySum\n" +
            "from rentals r\n" +
            "    join penalties p on r.rental_id = p.rental_id\n" +
            "    join clients c on r.client_id = c.client_id\n" +
            "group by c.name, c.surname\n" +
            "order by penaltySum desc\n" +
            "limit 5;", nativeQuery = true)
    List<IClientsHighestPenaltySum> getFiveClientsWithHighestPenaltySum();

    @Query(value = "select gender, count(*) from clients group by gender;", nativeQuery = true)
    List<IClientsGendersCount> countClientByGender();

    @Modifying @Query(value = "INSERT INTO clients (name, surname, birth_date, gender, phone_number, is_verified, licence_id, user_id) VALUES (:name, :surname, :birth_date, :gender, :phone_number, :is_verified, :licence_id, :user_id)", nativeQuery = true)
    void insertClient(@Param("name") String name,
                      @Param("surname") String surname,
                      @Param("birth_date") Date birthDate,
                      @Param("gender") String gender,
                      @Param("phone_number") String phoneNumber,
                      @Param("is_verified") Boolean isVerified,
                      @Param("licence_id") Long licenceId,
                      @Param("user_id") Long userId
                    );
}