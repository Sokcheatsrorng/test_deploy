package co.istad.idata.feature.user.registration.token;

import co.istad.idata.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByTokenAndType(String token, VerificationToken.TokenType type);

    Optional<VerificationToken> findByUserIdAndType(Long userId, VerificationToken.TokenType type);

    void deleteAllByUser(User user);

    VerificationToken findByToken(String token);
}
