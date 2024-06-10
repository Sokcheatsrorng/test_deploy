package co.istad.idata.feature.user;

import co.istad.idata.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

    @Modifying
    @Query("UPDATE User as u SET u.isDeleted = TRUE WHERE u.uuid = ?1")
    void disableByUuid(String uuid);

    @Modifying
    @Query("UPDATE  User as u SET u.isDeleted = FALSE WHERE u.uuid = ?1")
    void enableByUuid(String uuid);

    @Modifying
    @Query("UPDATE  User as u SET u.isBlocked = TRUE WHERE u.uuid = ?1")
    void blockByUuid(String uuid);

    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

    Optional<User> findByEmailAndUuid(String email, String uuid);
}
