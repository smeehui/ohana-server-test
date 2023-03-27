package vn.tg.ohana.mappers;

import org.springframework.stereotype.Component;
import vn.tg.ohana.dto.UtilityResult;
import vn.tg.ohana.repository.model.Utility;

@Component
public class UtilityMapper {

    public UtilityResult toDTO(Utility utility) {
        UtilityResult utilityResult = new UtilityResult();
        utilityResult.setId(utility.getId());
        utilityResult.setName(utility.getName());
        utilityResult.setIcon(utility.getIcon());
        utilityResult.setHidden(utility.getStatus());
        utilityResult.setPriority(utility.getPriority());
        return utilityResult;
    }


}
