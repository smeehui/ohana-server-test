package vn.tg.ohana.mappers;

import org.springframework.stereotype.Component;
import vn.tg.ohana.dto.RentHouseParam;
import vn.tg.ohana.dto.RentHouseResult;
import vn.tg.ohana.repository.model.Gender;
import vn.tg.ohana.repository.model.RentHouse;

import java.math.BigDecimal;

@Component
public class RentHouseMapper {
    public RentHouseResult toDTO(RentHouse rentHouse) {
        RentHouseResult result = new RentHouseResult();
        result.setId(rentHouse.getId());
        result.setCapacity(rentHouse.getCapacity());
        result.setGender(rentHouse.getGender());
        result.setPrice(rentHouse.getPrice());
        result.setArea(rentHouse.getArea());
        result.setStatus(rentHouse.isStatus());
        return result;
    }

    public RentHouse toRentHouse(RentHouseParam rentHouseParam) {
        RentHouse rentHouse = new RentHouse();

        rentHouse.setPrice(rentHouseParam.getPrice());
        rentHouse.setCapacity(rentHouseParam.getCapacity());
        rentHouse.setArea(rentHouseParam.getArea());
        rentHouse.setGender(new Gender(rentHouseParam.getGenderId()));
        return rentHouse;
    }

}
