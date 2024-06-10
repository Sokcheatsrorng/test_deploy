package co.istad.idata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdataApplication.class, args);
    }

}
