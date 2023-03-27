package vn.tg.ohana.controllers.dashboard.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tg.ohana.dto.PostResult;
import vn.tg.ohana.dto.UserResult;
import vn.tg.ohana.repository.model.StatusPost;
import vn.tg.ohana.repository.model.User;
import vn.tg.ohana.repository.model.UserStatus;
import vn.tg.ohana.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("http://localhost:5173")
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping("/active")
    public ResponseEntity<?> getUtility() {
        List<UserResult> user = userService.findAllByStatus(UserStatus.ACTIVATED);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchUser(@PathVariable String query) {
        List<UserResult> user = userService.searchUsers(query,UserStatus.ACTIVATED);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
