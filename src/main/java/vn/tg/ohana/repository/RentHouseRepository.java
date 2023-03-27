package vn.tg.ohana.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.tg.ohana.repository.model.RentHouse;

@Repository
public interface RentHouseRepository extends JpaRepository<RentHouse,String> {
}
