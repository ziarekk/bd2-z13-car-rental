package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.User;

@Transactional @Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query
    List<User> findByUserId(Long userId);

    @Query
    List<User> findByLogin(String login);

    @Query
    List<User> findByHashedPassword(String hashedPassword);

    @Query
    List<User> findByEmail(String email);

    @Modifying
    @Query(value = "INSERT INTO users (login, hashed_password, email, role_id) VALUES (:login, :hashed_password, :email, :role_id)", nativeQuery = true)
    void insertUser(@Param("login") String login, 
                    @Param("hashed_password") String hashedPassword, 
                    @Param("email") String email,
                    @Param("role_id") Long roleId);

    @Modifying
    @Query(value = "INSERT INTO users (login, hashed_password, email, client_id, role_id) VALUES (:login, :hashed_password, :email, :client_id, :role_id)", nativeQuery = true)
    void insertUser(@Param("login") String login, 
                    @Param("hashed_password") String hashedPassword, 
                    @Param("email") String email,
                    @Param("client_id") Long clientId,
                    @Param("role_id") Long roleId);
}