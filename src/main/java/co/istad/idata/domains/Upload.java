package co.istad.idata.domains;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="dt_uploads")
public class Upload {

    @Id
    private String id;

    private String fileName;

    private String mimeType;

    private Long fileSize;

    private byte[] data;

    @ManyToOne
    private UserData userData;

}
