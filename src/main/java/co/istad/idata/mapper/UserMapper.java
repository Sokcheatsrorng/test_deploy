package co.istad.idata.mapper;

import co.istad.idata.domains.User;
import co.istad.idata.feature.user.registration.RegistrationRequest;
import co.istad.idata.feature.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User fromUserCreateRequest(RegistrationRequest createRequest);

    @Mapping(target = "role", source = "roles")
    UserResponse toUserResponse(User user);

    User fromUserResponse(UserResponse userResponse);

    List<UserResponse> toUserResponseList(List<User> users);

}
