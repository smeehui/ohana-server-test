package vn.tg.ohana.mappers;


import org.springframework.stereotype.Component;
import vn.tg.ohana.dto.PostMediaParam;
import vn.tg.ohana.dto.PostMediaResult;
import vn.tg.ohana.repository.model.*;


@Component
public class PostMediaMapper {

    public PostMediaResult toDTO(PostMedia postMedia) {
        PostMediaResult mediaResult = new PostMediaResult();
        mediaResult.setId(postMedia.getId());
        mediaResult.setFileUrl(postMedia.getFileUrl());
        return mediaResult;
    }

    public PostMedia toPost(PostMediaParam postMediaParam) {
        PostMedia postMedia = new PostMedia();
        return postMedia;
    }
}
