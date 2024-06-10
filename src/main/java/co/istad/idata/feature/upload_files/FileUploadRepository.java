package co.istad.idata.feature.upload_files;
import co.istad.idata.domains.Upload;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileUploadRepository extends JpaRepository<Upload,String> {

     boolean existsByFileName(String fileName);

}
