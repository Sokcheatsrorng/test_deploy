package co.istad.idata.feature.api_generation.json_field;

import co.istad.idata.feature.api_generation.json_field.json_property.Property;
import co.istad.idata.feature.api_generation.json_field.json_property.RelationshipKey;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefinitionSchema implements Serializable {

    @Column(nullable = false)
    private String name;

    private String description;

    private String type;

    @JsonProperty("keys")
    private List<RelationshipKey> keys;

    @JsonProperty("properties")
    private List<Property> properties;
}
