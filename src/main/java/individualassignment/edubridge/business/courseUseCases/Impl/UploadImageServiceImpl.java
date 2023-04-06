package individualassignment.edubridge.business.courseUseCases.Impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import individualassignment.edubridge.business.courseUseCases.UploadImageService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;

@Component
public class UploadImageServiceImpl implements UploadImageService {
    @Override
    public String uploadImage(MultipartFile image, String imageName) {

        HashMap<String, String> config = new HashMap<>();
        config.put("cloud_name", "diwgbx1f9");
        config.put("api_key", "249747327848349");
        config.put("api_secret", "jsGuZRAER3RJikpNeGqx8eTrNX8");
        Cloudinary cloudinary = new Cloudinary(config);

        // Upload
        try {
            cloudinary.uploader()
                    .upload(image.getBytes(), ObjectUtils.asMap("public_id", imageName));
        } catch (IOException exception) {
            return exception.getMessage();
        }

        // Transform

        //noinspection rawtypes
        return cloudinary.url()
                .transformation(new Transformation()
                                .width(265)
                                .height(200)
                                .crop("fit"))
                .generate(imageName);


    }
}
