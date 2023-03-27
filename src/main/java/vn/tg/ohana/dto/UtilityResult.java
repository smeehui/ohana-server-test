package vn.tg.ohana.dto;

import lombok.Getter;
import lombok.Setter;
import vn.tg.ohana.repository.model.StatusUtility;

@Getter
@Setter
public class UtilityResult {
    private Long id;
    private String name;
    private StatusUtility hidden;
    private String icon;
    private int priority;
    private StatusUtility status;
}

