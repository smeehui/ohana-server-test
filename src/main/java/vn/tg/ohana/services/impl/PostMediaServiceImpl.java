package vn.tg.ohana.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.tg.ohana.dto.PostMediaResult;
import vn.tg.ohana.mappers.PostMediaMapper;
import vn.tg.ohana.repository.PostMediaRepository;
import vn.tg.ohana.repository.model.PostMedia;
import vn.tg.ohana.services.PostMediaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PostMediaServiceImpl implements PostMediaService {

    @Autowired
    private PostMediaRepository postMediaRepository;
    @Autowired
    private PostMediaMapper postMediaMapper;

    @Override
    public PostMedia save(PostMedia postMedia) {
        return postMediaRepository.save(postMedia);
    }

    @Override
    public Optional<PostMedia> findById(String id) {
        return postMediaRepository.findById(id);
    }

    @Override
    public PostMedia findByFileUrl(String fileUrl) {
        return postMediaRepository.findByFileUrl(fileUrl);
    }

    @Override
    public List<PostMediaResult> findAllByPost(String thumbnailId,Long postId) {
        List<PostMediaResult> list = new ArrayList<>();
        postMediaRepository.findAllByIdNotAndPostId(thumbnailId,postId).forEach(st -> list.add(postMediaMapper.toDTO(st)));
        return list;
    }

//    @Override
//    public List<String> findAllById(Long postId) {
//        List<String> list = new ArrayList<>();
//        postMediaRepository.findAllByPostId(postId).forEach(st -> list.add(st.getFileUrl()));
//        return list;
//    }


    @Override
    public PostMedia create(PostMedia postMedia) {
        return postMediaRepository.save(postMedia);
    }

    @Override
    public void delete(String id) {
        postMediaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String postMediaId) {
        return postMediaRepository.existsById(postMediaId);
    }

    @Override
    public void removeAllByPostId(Long postId) {
        postMediaRepository.removeAllByPost_Id(postId);
    }


}
