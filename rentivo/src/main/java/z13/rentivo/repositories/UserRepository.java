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
}