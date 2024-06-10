package co.istad.idata.feature.api_generation.converter;

import co.istad.idata.feature.api_generation.json_field.UserJsonData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter(autoApply = true)
public class UserJsonDataConverter implements AttributeConverter<UserJsonData, String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(UserJsonData data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting MySchema to JSON", e);
        }
    }

    @Override
    public UserJsonData convertToEntityAttribute(String s) {
        try {
            return objectMapper.readValue(s, UserJsonData.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading MySchema from JSON", e);
        }
    }
}
