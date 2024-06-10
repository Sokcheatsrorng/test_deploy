package co.istad.idata.feature.upload_files;

import co.istad.idata.feature.api_generation.json_field.DefinitionSchema;
import co.istad.idata.feature.upload_files.dto.FileUploadResponse;
import co.istad.idata.feature.upload_files.dto.UpdateSchemaFromJsonRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * Service interface for handling file upload operations.
 * This interface provides methods to store multiple files, load all files, and download a JSON file by its name.
 * Implementations of this interface should handle the specific logic for processing and managing uploaded files.
 * Methods included:
 * - {@link #storeFiles(List)}: Stores multiple files and returns a list of file upload response
 * Usage of this interface allows for flexible implementations and testing of file upload operations.
 * @author: Srorng Sokcheat
 * @Date:9/06/2024
 * @version 1.0
 */

public interface FileUploadService {


    /**
     * Stores multiple files and returns a list of file upload responses.
     * This method processes and saves each file, generating metadata and potentially storing data in a database.
     * @param files The list of files to be uploaded.
     * @return A list of file upload responses containing metadata about the uploaded files.
     * @throws IOException If an I/O error occurs during file processing.
     */
    List<FileUploadResponse> storeFiles(List<MultipartFile> files) throws IOException;


    /**
     * Loads all files and returns a map of file names to file upload responses.
     * This method retrieves all stored files and their associated metadata.
     * @return A map where the keys are file names and the values are file upload responses.
     */
    Map<String, FileUploadResponse> loadAllFiles() ;


    /**
     * Generates schema from JSON.
     * @return ResponseEntity containing the generated DefinitionSchema.
     */
    ResponseEntity<DefinitionSchema> generateSchemaFromJson();

    /**
     * Updates schema from JSON by ID.
     * @param request The request containing update data.
     * @param id The ID of the schema to update.
     * @return The updated DefinitionSchema.
     */
    DefinitionSchema updateSchemaFromJson(UpdateSchemaFromJsonRequest request, Long id);
}
