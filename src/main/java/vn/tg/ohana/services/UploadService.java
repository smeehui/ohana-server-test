package vn.tg.ohana.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface UploadService {
    Map uploadImage(MultipartFile multipartFile, Map params) throws IOException;

    Map destroyImage(String publicId, Map params) throws IOException;

}
