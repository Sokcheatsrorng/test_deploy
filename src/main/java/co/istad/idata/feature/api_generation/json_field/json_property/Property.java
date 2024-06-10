package co.istad.idata.feature.api_generation.json_field.json_property;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Property implements Serializable {

    private String dataType;

    private String columnName;

    private boolean isRequired;

}
