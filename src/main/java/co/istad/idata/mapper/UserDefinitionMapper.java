package co.istad.idata.mapper;

import co.istad.idata.domains.UserDefinition;
import co.istad.idata.feature.user_definition.dto.DefinitionRequest;
import co.istad.idata.feature.user_definition.dto.DefinitionResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface UserDefinitionMapper {

    @Mapping(source = "schema", target = "schema")
    DefinitionResponse toDefinitionResponse(UserDefinition definition);

    @Mapping(target = "schema", source = "schema")
    List<DefinitionResponse> toDefinitionResponseList(List<UserDefinition> definition);


}
