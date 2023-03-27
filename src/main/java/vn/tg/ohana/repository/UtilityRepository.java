package vn.tg.ohana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tg.ohana.repository.model.Post;
import vn.tg.ohana.repository.model.StatusPost;
import vn.tg.ohana.repository.model.StatusUtility;
import vn.tg.ohana.repository.model.Utility;

import java.util.List;

@Repository
public interface UtilityRepository extends JpaRepository<Utility, Long> {
    List<Utility> findAllById(Iterable<Long> ids);

    List<Utility> findAllByStatus(StatusUtility status);



}
