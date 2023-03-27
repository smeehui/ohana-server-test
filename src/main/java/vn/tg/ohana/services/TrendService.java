package vn.tg.ohana.services;

import org.springframework.data.jpa.repository.Query;
import vn.tg.ohana.repository.model.Trend;

import java.util.List;

public interface TrendService {


    List<Trend> getAllTrend();

    Trend getById( Long id);

    void update( Trend trend);

    Trend findByNameIsLike(String location);
}
