package vn.tg.ohana.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.tg.ohana.dto.PostResult;
import vn.tg.ohana.repository.model.*;
import vn.tg.ohana.repository.model.Post;
import vn.tg.ohana.repository.model.StatusPost;
import vn.tg.ohana.repository.model.User;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT  P.user.fullName, P.user.address, P.id FROM Post P ")
    List<PostResult> findAllOptionalAttributePost();

    @Query("SELECT p FROM Post p WHERE p.status=:status AND p.user=:poster")
    List<Post> findAllByStatusAndUser(@Param("status") StatusPost status, @Param("poster") User poster);

    //    @Query("SELECT p FROM Post p")
    Page<Post> findAll(Pageable pageable);

    List<Post> findAllByStatus(StatusPost status);

    @Query(value = "SELECT * FROM post p WHERE  p.location LIKE :province AND p.status=1 ORDER BY p.created_at DESC LIMIT 0, 10", nativeQuery = true)
    List<Post> findTop10ByCreatedAt(@Param("province") String province);

    @Query(value = "SELECT * FROM post p WHERE  p.location LIKE :location AND p.status=1 ORDER BY p.created_at DESC ", nativeQuery = true)
    List<Post> findAllByLocation(@Param("location") String location);

    List<Post> findAllByRentHouse_PriceIsBetweenAndRentHouse_Gender(BigDecimal priceStarts, BigDecimal priceEnds, Gender gender);

    @Query("SELECT p FROM Post p WHERE p.rentHouse.price>=:priceEnds")
    List<Post> findAllByRentHouse_PriceMax(@Param("priceEnds") BigDecimal priceStarts);

    List<Post> findAllByRentHouse_PriceIsBetween(BigDecimal priceStarts, BigDecimal priceEnds);

    List<Post> findAllByRentHouse_Gender(Gender gender);

    //    equals all
    List<Post> findAllByUtilitiesIsLike(String jsonUtility);


    @Query("SELECT p FROM Post p WHERE p.utilities LIKE :utility")
    List<Post> findAllByUtilitiesFilter(@Param("utility") String utilityId);

    List<Post> findAllByCategory(Category category);

    @Query("SELECT count(p.status) FROM Post p WHERE p.status=:statusPost")
    Long getQuantityStatus(@Param("statusPost") StatusPost statusPost);

    @Query(value = "SELECT * FROM post p WHERE  p.location LIKE :location AND p.location LIKE :province AND p.status=1 ORDER BY p.created_at DESC LIMIT 0, 10", nativeQuery = true)
    List<Post> getDataSearch(@Param("location") String location, @Param("province") String province);

//    @Query(value = "SELECT * FROM Post p WHERE p.location LIKE %:keyword%", nativeQuery = true)
//     List<Post> findByKeyWord(@Param("keyword") String keyword);
//    List<Post> findByKeyWord(String keyword);
}
