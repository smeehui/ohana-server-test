package vn.tg.ohana.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import vn.tg.ohana.dto.*;
import vn.tg.ohana.repository.model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface PostService {

    Page<PostResult> findPosts(StatusPost statusPost,Pageable pageable);

    void postNews(PostCreateParam postCreateParam) throws IOException;

    List<PostResult> findAllByCategoryId();

    List<PostResult> findAll();

    Optional<PostResult> findById(Long id);

    Post save(Post post);

    void remove(Long id);

    //    void softUnapproved(Post post);

    List<PostResult> findTop10ByCreatedAt(String province);

    List<PostResult> findAllByWard( String wardName);

    List<PostResult> findAllOptionalAttributePost();

    List<PostResult> findAllByStatusAndUser(StatusPost status, User poster);

    List<PostResult> findAllByStatus(StatusPost status);

    void unapproved(Long postId);

    void published(Long postId);

//    void emptyRoom(Long postId);
//
//    void overRoom(Long postId);

    Long postPreview(PostCreateParam postCreateParam);

    void savePostPreview(Long id);

    void postEdit(PostUpdateParam postParam) throws IOException;

    List<PostResult> findAllByRentHouse_PriceIsBetweenAndRentHouse_Gender(BigDecimal priceStarts, BigDecimal priceEnds, Long genderId);

    List<PostResult> findAllByRentHouse_PriceMax(BigDecimal priceStarts);

    List<PostResult> findAllByRentHouse_Price(BigDecimal priceStarts, BigDecimal priceEnds);

    List<PostResult> findAllByRentHouse_Gender(Long genderId);

    List<PostResult> findAllByCategory(Long categoryId);

    //  List<PostResult> findAllByUtilitiesFilter(String utilitiesId);

    List<PostResult> findAllByUtilitiesFilter(List<Long> utilitiesId);

    List<PostResult> findAllByFilter(FilterParam filterParam);

    PostResult editStatusRentHouse(Long postId);

    Page<PostResult> findPostsByPage(List<PostResult> list,FilterParam filterParam, Pageable pageable);

//    List<Post> findByKeyWord(String keyword);

    Long getQuantityStatus(StatusPost statusPost);

    List<DataSearchResult> getDataSearch(String location,String province);

    void deletePost(Long postId);
}
