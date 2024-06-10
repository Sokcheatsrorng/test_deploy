package co.istad.idata.feature.user_definition.user_data;

import co.istad.idata.domains.User;
import co.istad.idata.domains.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserData, Long> {

    Optional<UserData> findById(String id);

    List<UserData> findByUserAndTableName(User user, String tableName);

    Optional<UserData> findByTableName(String tableName);

    boolean existsByTableName(String tableName);
}
