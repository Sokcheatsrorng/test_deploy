package co.istad.idata.domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Setter
@Getter
@Entity
@Table(name = "dt_social_logins")
@EntityListeners(AuditingEntityListener.class)
public class SocialLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;

    @ManyToOne
    private User user;

}
