package vn.tg.ohana.controllers.dashboard.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.tg.ohana.dto.PostResult;
import vn.tg.ohana.dto.UtilityResult;
import vn.tg.ohana.repository.model.Cart;
import vn.tg.ohana.repository.model.Post;
import vn.tg.ohana.repository.model.StatusPost;
import vn.tg.ohana.repository.model.UserStatus;
import vn.tg.ohana.services.PostService;
import vn.tg.ohana.services.UserService;
import vn.tg.ohana.services.UtilityService;

import java.util.List;
import java.util.Optional;

@RestController
//@SessionAttribute("cart")
@RequestMapping("/api/post")

public class PostRestController {

    @Autowired
    private PostService postService;

    @GetMapping("/newPost")
    public ResponseEntity<?> getPost() {
        List<PostResult> posts = postService.findAllByStatus(StatusPost.PUBLISHED);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/unapproved")
    public ResponseEntity<?> getUnapproved() {
        List<PostResult> unapproved = postService.findAllByStatus(StatusPost.PENDING_REVIEW);
        return new ResponseEntity<>(unapproved, HttpStatus.OK);
    }

    @PostMapping("/unapproved/{postId}")
    public ResponseEntity<?> doUnapproved(@PathVariable Long postId) {
        postService.unapproved(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/published/{postId}")
    public ResponseEntity<?> doPublished(@PathVariable Long postId) {
        postService.published(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
