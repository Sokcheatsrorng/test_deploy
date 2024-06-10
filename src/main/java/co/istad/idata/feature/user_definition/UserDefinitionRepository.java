package co.istad.idata.feature.user_definition;

import co.istad.idata.domains.UserDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserDefinitionRepository extends JpaRepository<UserDefinition, Long> {

    Optional<UserDefinition> findByTableName(String tableName);

    boolean existsByTableName(String tableName);

    @Query(value = "SELECT * FROM dt_user_definitions ORDER BY id DESC LIMIT 1", nativeQuery = true)
    Optional<UserDefinition> findLatestUserDefinition();


}
