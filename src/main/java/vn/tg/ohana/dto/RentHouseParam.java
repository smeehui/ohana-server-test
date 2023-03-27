package vn.tg.ohana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RentHouseParam {
    private BigDecimal price;
    private Long capacity;
    private Long area;
    private Long genderId;

    @Override
    public String toString() {
        return "RentHouseParam{" +
                ", price=" + price +
                ", capacity=" + capacity +
                ", area=" + area +
                ", gender='" + genderId + '\'' +
                '}';
    }
}