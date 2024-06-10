package co.istad.idata.mapper;
import co.istad.idata.domains.Upload;
import co.istad.idata.feature.upload_files.dto.FileUploadResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UploadMapper {

    @Mappings({
            @Mapping(source = "fileName", target = "fileName"),
            @Mapping(source = "mimeType", target = "fileType"),
            @Mapping(source = "fileSize", target = "size"),
            @Mapping(source = "data", target = "data")

    })
    FileUploadResponse fromUploadFileResponse(Upload upload);




}
