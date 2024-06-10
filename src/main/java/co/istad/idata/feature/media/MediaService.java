package co.istad.idata.feature.media;

import co.istad.idata.base.BasedMessage;
import co.istad.idata.feature.media.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {

    MediaResponse uploadSingle(MultipartFile file, String folderName);

    List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName);

    MediaResponse loadMediaByName(String mediaName, String folderName);

    void deleteMediaByName(String mediaName, String folderName);

    List<MediaResponse> loadAllMedias(String folderName);


}
