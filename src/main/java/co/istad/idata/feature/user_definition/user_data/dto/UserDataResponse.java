package co.istad.idata.feature.user_definition.user_data.dto;

import co.istad.idata.feature.api_generation.json_field.UserJsonData;
import co.istad.idata.feature.user.dto.UserResponse;
import co.istad.idata.feature.user_definition.dto.DefinitionResponse;

public record UserDataResponse(

        String id,

        DefinitionResponse definition,

        UserResponse user,

        UserJsonData data

) {
}
