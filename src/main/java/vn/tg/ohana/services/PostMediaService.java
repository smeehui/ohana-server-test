package vn.tg.ohana.services;


import vn.tg.ohana.dto.PostMediaResult;
import vn.tg.ohana.repository.model.PostMedia;

import java.util.List;
import java.util.Optional;

public interface PostMediaService  {

    PostMedia save(PostMedia postMedia);

    Optional<PostMedia> findById(String id);

    PostMedia findByFileUrl(String fileUrl);

    List<PostMediaResult> findAllByPost(String thumbnailId,Long postId);

    PostMedia create(PostMedia postMedia);

    void delete(String id);

    boolean existsById(String postMediaId);

    void removeAllByPostId(Long postId);
}
