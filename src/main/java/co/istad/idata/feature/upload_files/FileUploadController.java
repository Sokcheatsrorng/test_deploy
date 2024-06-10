package co.istad.idata.feature.upload_files;

import co.istad.idata.feature.api_generation.json_field.DefinitionSchema;
import co.istad.idata.feature.upload_files.dto.FileUploadResponse;
import co.istad.idata.feature.upload_files.dto.UpdateSchemaFromJsonRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/files")
public class FileUploadController {

    private final FileUploadService fileUploadService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/multiple-files")
    public List<FileUploadResponse> uploadFiles(@Valid @RequestParam("files") List<MultipartFile> files) throws IOException {
        return fileUploadService.storeFiles(files);
    }


    @GetMapping("/schema")
    public ResponseEntity<DefinitionSchema> generateSchemaFromJson() {
       return fileUploadService.generateSchemaFromJson();
    }

    @GetMapping
    public Map<String, FileUploadResponse> loadAllFiles(){
        return fileUploadService.loadAllFiles();
    }

    @PatchMapping("/schema/{id}")
    public DefinitionSchema updateSchemaFromJsonById(
            @RequestBody UpdateSchemaFromJsonRequest request,
            @PathVariable Long id) {
       return fileUploadService.updateSchemaFromJson(request,id);

    }
}