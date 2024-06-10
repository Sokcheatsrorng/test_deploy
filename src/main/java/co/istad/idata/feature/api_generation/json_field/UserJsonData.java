package co.istad.idata.feature.api_generation.json_field;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class UserJsonData implements Serializable {

    private String url;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "jsonb")
    private List<Map<String, Object>> data;

}
