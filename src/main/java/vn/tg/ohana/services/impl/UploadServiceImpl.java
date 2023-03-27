package vn.tg.ohana.services.impl;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.tg.ohana.services.UploadService;

import java.io.IOException;
import java.util.Map;

@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map uploadImage(MultipartFile multipartFile, Map options) throws IOException {
        return cloudinary.uploader().upload(multipartFile.getBytes(), options);
    }

    @Override
    public Map destroyImage(String publicId, Map options) throws IOException {
        return cloudinary.uploader().destroy(publicId, options);
    }

}
