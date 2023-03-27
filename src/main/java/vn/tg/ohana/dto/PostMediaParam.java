package vn.tg.ohana.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
public class PostMediaParam {
    private String id;
    private MultipartFile file;

    @Override
    public String toString() {
        return "PostMediaParam{" +
                "id='" + id + '\'' +
                ", file=" + file +
                '}';
    }
}
