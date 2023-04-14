package individualassignment.edubridge.business.course;

import org.springframework.web.multipart.MultipartFile;

public interface UploadImageService {
    String uploadImage(MultipartFile image, String imageName);
    void deleteImage(String imageName);
}
