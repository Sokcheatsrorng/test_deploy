package co.istad.idata.feature.user_definition.dto;

import co.istad.idata.feature.api_generation.json_field.DefinitionSchema;

public record DefinitionResponse(

        Long id,

        String tableName,

        DefinitionSchema schema

) {
}
