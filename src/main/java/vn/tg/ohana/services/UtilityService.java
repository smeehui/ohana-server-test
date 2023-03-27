package vn.tg.ohana.services;

import vn.tg.ohana.dto.UtilityResult;
import vn.tg.ohana.repository.model.StatusUtility;
import vn.tg.ohana.repository.model.Utility;

import java.util.List;
import java.util.Optional;

public interface UtilityService {
    List<UtilityResult> findAll();

    Optional<UtilityResult> findById(Long id);

    Utility save(Utility utility);



    void delete(Utility utilityId);

    void show(Long utilityId);

    void hidden(Long utilityId);

    List<UtilityResult> findAllById(Iterable<Long> longs);

    List<UtilityResult> findAllByStatus(StatusUtility status);


    List<UtilityResult> findAllByIdASC(List<Long> utilityIds);
}
