package vn.tg.ohana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.tg.ohana.repository.model.Gender;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentHouseResult {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long capacity;
    private Long area;
    private Gender gender;
    private boolean status;

    @Override
    public String toString() {
        return "RentHouseParam{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", capacity=" + capacity +
                ", area=" + area +
                ", gender='" + gender + '\'' +
                '}';
    }
}