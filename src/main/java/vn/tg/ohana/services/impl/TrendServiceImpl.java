package vn.tg.ohana.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.tg.ohana.repository.TrendRepository;
import vn.tg.ohana.repository.model.Trend;
import vn.tg.ohana.services.TrendService;

import java.util.List;

@Service
public class TrendServiceImpl implements TrendService {

    @Autowired
    TrendRepository trendRepository;

    @Override
    public List<Trend> getAllTrend() {
        return trendRepository.getTop6();
    }

    @Override
    public Trend getById(Long id) {
        return trendRepository.getById(id);
    }

    @Override
    public void update(Trend trend) {
        trendRepository.save(trend);
    }

    @Override
    public Trend findByNameIsLike(String location) {
        return trendRepository.getByName(location);
    }
}
