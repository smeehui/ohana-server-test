package vn.tg.ohana.controllers.ohana.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.tg.ohana.dto.*;
import vn.tg.ohana.mappers.PostMapper;
import vn.tg.ohana.repository.model.StatusPost;
import vn.tg.ohana.repository.model.User;
import vn.tg.ohana.services.PostService;
import vn.tg.ohana.services.UserService;
import vn.tg.ohana.utils.AppUtils;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/posts")
public class PostAPI {
    @Autowired
    PostService postService;

    @Autowired
    PostMapper postMapper;

    @Autowired
    UserService userService;

    @Autowired
    AppUtils appUtils;

    @ModelAttribute("userLogin")
    public LoginResult getUserLoginFromCookie(@CookieValue(value = "loginUser", defaultValue = "0") String loginUsername) {
        LoginResult userLogin = null;
        if (!loginUsername.equals("0")) {
            userLogin = userService.findByEmail(loginUsername);
        }
        return userLogin;
    }


    @PostMapping("/post-news")
    public ResponseEntity<?> doPost(@Validated @ModelAttribute("userLogin") LoginResult userLogin, @ModelAttribute PostCreateParam postCreateParam, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        postCreateParam.setPoster(userLogin.getId());
        postService.postNews(postCreateParam);
        return new ResponseEntity<>("Thêm bài viết thành công", HttpStatus.OK);

    }

    @PostMapping("/post-preview")
    public ResponseEntity<?> doPostPreview(@ModelAttribute("userLogin") LoginResult userLogin, @ModelAttribute PostCreateParam postCreateParam) throws IOException {
        postCreateParam.setPoster(userLogin.getId());
        Long idPostPreview = postService.postPreview(postCreateParam);
        return new ResponseEntity<>(idPostPreview, HttpStatus.OK);
    }

    @GetMapping("/list-post-published")
    public ResponseEntity<?> listPublished(@ModelAttribute("userLogin") LoginResult userLogin) throws IOException {
        List<PostResult> postResults = postService.findAllByStatusAndUser(StatusPost.PUBLISHED, new User(userLogin.getId()));
        return new ResponseEntity<>(postResults, HttpStatus.OK);
    }

    @GetMapping("/list-post-pending-review")
    public ResponseEntity<?> listPendingReview(@ModelAttribute("userLogin") LoginResult userLogin) throws IOException {
        User user = new User();
        user.setId(userLogin.getId());
        List<PostResult> postResults = postService.findAllByStatusAndUser(StatusPost.PENDING_REVIEW, user);
        return new ResponseEntity<>(postResults, HttpStatus.OK);
    }

    @GetMapping("/list-post-refused")
    public ResponseEntity<?> listRefused(@ModelAttribute("userLogin") LoginResult userLogin) throws IOException {
        User user = new User();
        user.setId(userLogin.getId());
        List<PostResult> postResults = postService.findAllByStatusAndUser(StatusPost.REFUSED, user);
        return new ResponseEntity<>(postResults, HttpStatus.OK);
    }

    @GetMapping("/get-data-search/{dataSearch}")
    public ResponseEntity<?> getDataSearch(@PathVariable String dataSearch, @ModelAttribute("userLogin") LoginResult userLogin) throws IOException {
        List<DataSearchResult> dataSearchResults = postService.getDataSearch(dataSearch,"%provinceName\": \"Tỉnh Thừa Thiên Huế%");
        return new ResponseEntity<>(dataSearchResults, HttpStatus.OK);
    }


    @PutMapping("/post-save-preview/{id}")
    public ResponseEntity<?> savePreview(@PathVariable Long id) throws IOException {
        postService.savePostPreview(id);
        return new ResponseEntity<>("Thêm bài viết thành công", HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> edit(@ModelAttribute("userLogin") LoginResult userLogin, @ModelAttribute PostUpdateParam postParam) throws IOException {
        postService.postEdit(postParam);
        return new ResponseEntity<>("Cập nhật bài viết thành công", HttpStatus.OK);
    }

    @PutMapping("/edit-status-rent-house/{postId}")
    public ResponseEntity<?> editStatusRentHouse(@PathVariable("postId") Long postId) throws IOException {

        PostResult postResult = postService.editStatusRentHouse(postId);
        return new ResponseEntity<>(postResult, HttpStatus.OK);
    }


    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestBody FilterParam filter) throws IOException {
        List<PostResult> list = postService.findAllByFilter(filter);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable("postId")Long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
