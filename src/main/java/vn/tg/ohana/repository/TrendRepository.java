package vn.tg.ohana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.tg.ohana.repository.model.Trend;

import java.util.List;

@Repository
public interface TrendRepository extends JpaRepository<Trend, Long> {
    @Query( value = "SELECT * FROM trends p ORDER BY p.number_of_searches DESC LIMIT 0, 6",nativeQuery = true)
    List<Trend> getTop6();

    Trend getByName(String location);
}
