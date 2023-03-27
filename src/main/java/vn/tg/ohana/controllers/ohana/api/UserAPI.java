package vn.tg.ohana.controllers.ohana.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.tg.ohana.repository.model.User;
import vn.tg.ohana.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
    @Autowired
    UserService userService;

    @PutMapping("/update-user")
    public ResponseEntity<?> doUpdate(@RequestBody User user) {
        Long id = user.getId();

        Optional<User> optionalUserResult = userService.findById(id);

        if (optionalUserResult.isPresent()) {
            user.setId(optionalUserResult.get().getId());
            user.setPassword(optionalUserResult.get().getPassword());
            user.setThumbnailId(optionalUserResult.get().getThumbnailId());
            user.setRole(optionalUserResult.get().getRole());
            user.setStatus(optionalUserResult.get().getStatus());
            userService.save(user);

            return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Error for update icon", HttpStatus.BAD_REQUEST);
        }
    }
}
