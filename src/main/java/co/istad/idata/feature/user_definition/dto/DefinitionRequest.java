package co.istad.idata.feature.user_definition.dto;

import co.istad.idata.feature.api_generation.json_field.DefinitionSchema;
import jakarta.validation.constraints.NotBlank;

public record DefinitionRequest(

        DefinitionSchema schema

) {
}
