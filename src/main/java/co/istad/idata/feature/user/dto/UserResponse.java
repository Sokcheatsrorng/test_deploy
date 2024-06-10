package co.istad.idata.feature.user.dto;

import java.util.List;

public record UserResponse(

        Long id,
        String email,
        String firstName,
        String lastName,
        String username,
        String password,
        String avatar,
        String uuid,
        String position,

        List<RoleResponse> role

) {
}
