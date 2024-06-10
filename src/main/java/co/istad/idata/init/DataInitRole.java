package co.istad.idata.init;

import co.istad.idata.domains.Authority;
import co.istad.idata.domains.Role;
import co.istad.idata.feature.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitRole {

    private final RoleRepository roleRepository;

    @PostConstruct
    void initRole(){

        // Auto generate role (USER, ADMIN)
        if (roleRepository.count() < 1){

            Authority userRead = new Authority();
            userRead.setName("user:read");

            Authority userWrite = new Authority();
            userWrite.setName("user:write");

            Authority userDelete = new Authority();
            userDelete.setName("user:delete");

            Authority definitionRead = new Authority();
            definitionRead.setName("definition:read");

            Authority definitionWrite = new Authority();
            definitionWrite.setName("definition:write");

            Authority mediaRead = new Authority();
            mediaRead.setName("media:read");

            Authority mediaWrite = new Authority();
            mediaWrite.setName("media:write");

            Authority dataRead = new Authority();
            dataRead.setName("data:read");

            Authority dataWrite = new Authority();
            dataWrite.setName("data:write");

            Role user = new Role();
            user.setName("USER");
            user.setAuthorities(List.of(
                    userWrite, definitionWrite, definitionRead, dataRead, dataWrite
            ));
            Role admin = new Role();
            admin.setName("ADMIN");
            admin.setAuthorities(List.of(
                    userRead, userWrite,
                    definitionWrite, definitionRead,
                    userDelete, mediaRead, mediaWrite
            ));

            roleRepository.saveAll(List.of(
                    user, admin
            ));

        }

    }

}

