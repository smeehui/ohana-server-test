package vn.tg.ohana.services;


import vn.tg.ohana.repository.model.RentHouse;

import java.util.List;
import java.util.Optional;

public interface RentHouseService  {

    List<RentHouse> findAll();

    Optional<RentHouse> findById(Long id);

    RentHouse getById(Long id);

    RentHouse save(RentHouse rentHouse);

    void remove(Long id);
}
