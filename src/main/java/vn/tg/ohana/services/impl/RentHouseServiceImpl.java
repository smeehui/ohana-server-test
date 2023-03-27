package vn.tg.ohana.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.tg.ohana.repository.RentHouseRepository;
import vn.tg.ohana.repository.model.RentHouse;
import vn.tg.ohana.services.RentHouseService;

import java.util.List;
import java.util.Optional;

@Service
public class RentHouseServiceImpl implements RentHouseService {
    @Autowired
    RentHouseRepository rentHouseRepository;


    @Override
    public List<RentHouse> findAll() {
        return null;
    }

    @Override
    public Optional<RentHouse> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public RentHouse getById(Long id) {
        return null;
    }

    @Override
    public RentHouse save(RentHouse rentHouse) {
        return rentHouseRepository.save(rentHouse);
    }

    @Override
    public void remove(Long id) {

    }


}
