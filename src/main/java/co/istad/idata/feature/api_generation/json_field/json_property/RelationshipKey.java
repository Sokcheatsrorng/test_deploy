package co.istad.idata.feature.api_generation.json_field.json_property;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipKey implements Serializable{

    private String columnName;

    private boolean primaryKey;

    private boolean foreignKey;

    private String referenceTable;

}
