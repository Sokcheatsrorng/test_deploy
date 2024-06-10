package co.istad.idata.domains;

import co.istad.idata.feature.api_generation.json_field.UserJsonData;
import co.istad.idata.feature.api_generation.converter.UserJsonDataConverter;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "dt_user_datas")
public class UserData {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String tableName;

    @ManyToOne
    @JoinColumn(name = "definition_id", referencedColumnName = "id")
    private UserDefinition definition;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private UserJsonData jsonData;

    @JoinColumn(name = "upload_id", referencedColumnName = "id")
    @OneToMany
    private List<Upload> uploads;

}
