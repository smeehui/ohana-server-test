package vn.tg.ohana.controllers.dashboard.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tg.ohana.dto.UtilityResult;
import vn.tg.ohana.repository.model.StatusUtility;
import vn.tg.ohana.repository.model.Utility;
import vn.tg.ohana.services.UtilityService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class UtilityRestController {

    @Autowired
    private UtilityService utilitiesService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Optional<UtilityResult> optionalUtility = utilitiesService.findById(id);
        if (optionalUtility.isPresent()) {
            return new ResponseEntity<>(optionalUtility.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error for get customer", HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/utility")
    public ResponseEntity<?> getUtility() {
        List<UtilityResult> posts = utilitiesService.findAllByStatus(StatusUtility.SHOW);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    @PutMapping("/utility")
    public ResponseEntity<?> doUpdate(@RequestBody Utility utility) {
        Long id = utility.getId();

        Optional<UtilityResult> utilityOptional = utilitiesService.findById(id);

        if (utilityOptional.isPresent()) {

            utility.setId(utilityOptional.get().getId());
            utility.setStatus(utilityOptional.get().getStatus());
            utility.setIcon(utilityOptional.get().getIcon());

            utilitiesService.save(utility);

            return new ResponseEntity<>(utility, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Error for update icon", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/hidden/{utilityId}")
    public ResponseEntity<?> doHidden(@PathVariable Long utilityId) {
         utilitiesService.hidden(utilityId);
        return new ResponseEntity<>(utilityId, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> doDelete(@RequestBody Utility utility) {
        System.out.println(utility);
        utilitiesService.delete(utility);
            return new ResponseEntity<>("Delete customer successful", HttpStatus.OK);

    }
}
