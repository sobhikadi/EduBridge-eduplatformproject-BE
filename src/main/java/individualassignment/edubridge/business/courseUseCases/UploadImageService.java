package individualassignment.edubridge.business.courseUseCases;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public interface UploadImageService {
    String uploadImage(MultipartFile image, String imageName);
    void deleteImage(String imageName);
}
