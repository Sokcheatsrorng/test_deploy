package co.istad.idata.feature.media;

import co.istad.idata.feature.media.dto.MediaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medias")
public class MediaController {

    private final MediaService mediaService;

    @PostMapping("/upload-single")
    MediaResponse uploadSingle(@RequestBody MultipartFile file){
        return mediaService.uploadSingle(file, "");
    }

}
