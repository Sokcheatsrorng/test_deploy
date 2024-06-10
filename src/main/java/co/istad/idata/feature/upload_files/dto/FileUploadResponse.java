package co.istad.idata.feature.upload_files.dto;

import co.istad.idata.domains.UserData;

public record FileUploadResponse(

         String fileName,

         String fileType,

         Long size,

         byte[] data,

         UserData userData
) {

}
