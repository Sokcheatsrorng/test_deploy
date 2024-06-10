package co.istad.idata.feature.media;

import co.istad.idata.feature.media.dto.MediaResponse;
import co.istad.idata.util.MediaUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediaServiceImpl implements MediaService{

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    @Override
    public MediaResponse uploadSingle(MultipartFile file, String folderName) {

        String newMediaName = UUID.randomUUID().toString();

        newMediaName += "." + MediaUtil.getExtension(file.getOriginalFilename());

        Path path = Paths.get(serverPath + folderName + "\\" + newMediaName);

        try {

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Something went wrong!"
            );
        }

        return MediaResponse.builder()
                .name(newMediaName)
                .contentType(file.getContentType())
                .extension(MediaUtil.getExtension(newMediaName))
                .size(file.getSize())
                .uri(String.format("%s%s/%s", baseUri, folderName, newMediaName))
                .build();

    }

    @Override
    public List<MediaResponse> uploadMultiple(List<MultipartFile> files, String folderName) {
        return null;
    }

    @Override
    public MediaResponse loadMediaByName(String mediaName, String folderName) {
        return null;
    }

    @Override
    public void deleteMediaByName(String mediaName, String folderName) {

    }

    @Override
    public List<MediaResponse> loadAllMedias(String folderName) {
        return null;
    }
}
