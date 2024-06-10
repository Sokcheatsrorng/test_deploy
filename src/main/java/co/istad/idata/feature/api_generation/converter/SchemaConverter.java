package co.istad.idata.feature.api_generation.converter;


import co.istad.idata.feature.api_generation.json_field.DefinitionSchema;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;

import java.io.Serializable;

@Converter(autoApply = true)
public class SchemaConverter implements AttributeConverter<DefinitionSchema, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DefinitionSchema definitionSchema) {
        try {
            return objectMapper.writeValueAsString(definitionSchema);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DefinitionSchema convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, DefinitionSchema.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
