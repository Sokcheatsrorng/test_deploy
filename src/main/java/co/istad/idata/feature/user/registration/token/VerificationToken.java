package co.istad.idata.feature.user.registration.token;

import co.istad.idata.domains.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "dt_verification_tokens")
@Setter
@Getter
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;

    private static final int EXPIRATION_TIME = 15;

    @Column(nullable = false)
    private Boolean idUsed;


    public enum TokenType{
        EMAIL_VERIFICATION,
        PASSWORD_RESET
    }

    public VerificationToken(String token, User user, TokenType type){
        super();
        this.createdAt = LocalDateTime.now();
        this.type = type;
        this.idUsed = false;
        this.token = token;
        this.user = user;
        this.expiresAt = this.getTokenExpirationTime();
    }

    public VerificationToken(String token){
        super();
        this.token = token;
        this.expiresAt = this.getTokenExpirationTime();
    }

    public Date getTokenExpirationTime() {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);

        return new Date(calendar.getTime().getTime());

    }

}
