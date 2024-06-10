package co.istad.idata.feature.user;

import co.istad.idata.domains.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
