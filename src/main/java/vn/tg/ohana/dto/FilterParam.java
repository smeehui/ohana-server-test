package vn.tg.ohana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterParam {
    private BigDecimal priceStarts;
    private BigDecimal priceEnds;
    private List<Long> utilities;
    private List<Long> categories;
    private Long gender;
    private Long wardId;
    private String provinceName;
    private String locationName;

}
