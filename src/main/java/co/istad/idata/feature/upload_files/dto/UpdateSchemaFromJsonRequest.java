package co.istad.idata.feature.upload_files.dto;

import co.istad.idata.feature.api_generation.json_field.json_property.Property;
import co.istad.idata.feature.api_generation.json_field.json_property.RelationshipKey;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UpdateSchemaFromJsonRequest(

        @NotNull(message = "Relationship key is required")
        List<RelationshipKey> keys,

        @NotNull(message = "Properties is required")
        List<Property> properties
) {
}
