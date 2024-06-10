package co.istad.idata.mapper;

import co.istad.idata.domains.UserData;
import co.istad.idata.feature.user_definition.user_data.dto.UserDataResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDataMapper {

    @Mapping(target = "user", source = "user")
    @Mapping(target = "user.role", source = "user.roles")
    @Mapping(target = "definition", source = "definition")
    @Mapping(target = "data", source = "jsonData")
    UserDataResponse toUserDataResponse(UserData userData);

}
