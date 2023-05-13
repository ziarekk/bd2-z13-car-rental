package z13.rentivo.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import z13.rentivo.entities.UserRole;

@Transactional @Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query
    List<UserRole> findByRoleId(Long roleId);

    @Query
    List<UserRole> findByName(String name);

    @Modifying
    @Query(value = "INSERT INTO user_role (role_id, name) VALUES (:role_id, :name)", nativeQuery = true)
    void insertUserRole(@Param("role_id") Long roleId, 
                        @Param("name") String name);

}