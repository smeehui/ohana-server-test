package vn.tg.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.tg.ohana.dto.*;
import vn.tg.ohana.mappers.UtilityMapper;
import vn.tg.ohana.repository.PostRepository;
import vn.tg.ohana.repository.model.StatusPost;
import vn.tg.ohana.repository.model.Trend;
import vn.tg.ohana.services.PostService;
import vn.tg.ohana.services.TrendService;
import vn.tg.ohana.services.UserService;
import vn.tg.ohana.services.UtilityService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
public class OhanaController {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @Autowired
    UtilityMapper utilityMapper;

    @Autowired
    UtilityService utilityService;

    @Autowired
    TrendService trendService;

    @ModelAttribute("userLogin")
    public LoginResult getUserLoginFromCookie(@CookieValue(value = "loginUser", defaultValue = "0") String loginUsername) {
        LoginResult userLogin = null;
        if (!loginUsername.equals("0")) {
            userLogin = userService.findByEmail(loginUsername);
        }
        return userLogin;
    }

    @GetMapping("/pading")
    public ModelAndView testpage(@ModelAttribute("userLogin") LoginResult userLogin) {
        ModelAndView modelAndView = new ModelAndView("/repository/pagination");
//        List<PostResult> postResults = postService.findTop10ByCreatedAt();
//        modelAndView.addObject("posts", postResults);
//        modelAndView.addObject("userLogin", userLogin);
//        modelAndView.addObject("messLogin", "Đăng nhập thành công");
        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView home(@ModelAttribute("userLogin") LoginResult userLogin) {
        ModelAndView modelAndView = new ModelAndView("/ohana/index");
        List<PostResult> postResults = postService.findTop10ByCreatedAt("%provinceName\": \"Tỉnh Thừa Thiên Huế%");
        modelAndView.addObject("posts", postResults);
        modelAndView.addObject("userLogin", userLogin);
        modelAndView.addObject("messLogin", "Đăng nhập thành công");
        return modelAndView;
    }

    @GetMapping("/view-all")
    public ModelAndView viewAll(@ModelAttribute("userLogin") LoginResult userLogin, String provinceName,String locationName) {
        ModelAndView modelAndView = new ModelAndView("/ohana/view-all");
        List<PostResult> postResults = postService.findAllByStatus(StatusPost.PUBLISHED);
//        Page<Post> postResults = postService.findPosts(StatusPost.PUBLISHED,pageable);
        modelAndView.addObject("provinceName", provinceName);
        modelAndView.addObject("locationName",locationName);
        modelAndView.addObject("posts", postResults);
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }

    @GetMapping("/view-all/{wardId}")
    public ModelAndView viewAllByWard(@ModelAttribute("userLogin") LoginResult userLogin, @PathVariable("wardId") Long wardId) {
        ModelAndView modelAndView = new ModelAndView("/ohana/view-all");
        Trend trend = trendService.getById(wardId);
        List<Trend> list = trendService.getAllTrend();
        boolean existWardTop6 = false;
        for (Trend t : list) {
            if (t.getId() == wardId) {
                existWardTop6 = true;
                break;
            }
        }
        if (existWardTop6) {
            modelAndView.addObject("wardId", wardId);
            modelAndView.addObject("trend", trend);
        } else {
            modelAndView.addObject("mess", "Không tồn tại xu hướng tìm kiếm này!");
        }
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }

    @GetMapping("/view-all/{provinceName}/{locationName}")
    public ModelAndView viewAllByLocation(@ModelAttribute("userLogin") LoginResult userLogin, String provinceName,String locationName) {
        ModelAndView modelAndView = new ModelAndView("/ohana/view-all");
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }

    @GetMapping("/room/{postId}")
    public Object room(@ModelAttribute("userLogin") LoginResult userLogin, @PathVariable Long postId) {
        ModelAndView modelAndView = new ModelAndView("/ohana/room");
        Optional<PostResult> postResult = postService.findById(postId);
        if (postResult.get().getStatus() != StatusPost.PUBLISHED) {
            return "redirect:/errors";
        }
        Trend trend = trendService.findByNameIsLike(postResult.get().getLocation().getWardName());
        if (trend != null) {
            if (trend.getNumberOfSearches() == null) {
                trend.setNumberOfSearches(1L);
            } else {
                trend.setNumberOfSearches(trend.getNumberOfSearches() + 1);
            }
            trendService.update(trend);
        }
        modelAndView.addObject("post", postResult.get());
        List<UtilityResult> utilities = postResult.get().getUtilities();
        modelAndView.addObject("utilities", utilities);
        modelAndView.addObject("userLogin", userLogin);

        return modelAndView;
    }

    @GetMapping("/room-preview/{postId}")
    public ModelAndView roomPreview(@ModelAttribute("userLogin") LoginResult userLogin, @PathVariable Long postId) {
        ModelAndView modelAndView = new ModelAndView("/ohana/room");
        Optional<PostResult> postResult = postService.findById(postId);
        modelAndView.addObject("post", postResult.get());
        modelAndView.addObject("userLogin", userLogin);
        List<UtilityResult> utilities = postResult.get().getUtilities();
        modelAndView.addObject("utilities", utilities);
        modelAndView.addObject("mess", "preview");
        return modelAndView;
    }


    @GetMapping("/search-bar")
    public ModelAndView searchBar() {
        ModelAndView modelAndView = new ModelAndView("/ohana/search-bar");
        return modelAndView;
    }


    @GetMapping("/post-room")
    public ModelAndView postRoom(@ModelAttribute("userLogin") LoginResult userLogin) {
        ModelAndView modelAndView = new ModelAndView();
//        LoginResult checkUserResult = userService.findByEmail(userLogin.getEmail());
        if (userLogin == null) {
            modelAndView.setViewName("/ohana/sign-in");
            modelAndView.addObject("user", new LoginParam());

        } else {
            modelAndView.setViewName("/ohana/post-room");
            LoginResult checkUserResult = userService.findByEmail(userLogin.getEmail());
            if (userService.checkInformationUser(checkUserResult.getEmail())) {
                modelAndView.addObject("checkInformation", true);

            } else {
                UserResult users = userService.getUserByEmail(userLogin.getEmail());
                modelAndView.addObject("users", users);
                modelAndView.addObject("checkInformation", false);
            }
        }
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }

    @GetMapping("/list-post")
    public ModelAndView information(@ModelAttribute("userLogin") LoginResult userLogin) {
        ModelAndView modelAndView = new ModelAndView();
        if (userLogin != null) {
            modelAndView.setViewName("/ohana/list-post");
        } else {
            modelAndView.setViewName("/ohana/sign-in");
            modelAndView.addObject("user", new LoginParam());
        }
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }

    @PostMapping("/preview")
    public ModelAndView saveDraft(@ModelAttribute PostCreateParam postCreateParam) {
        ModelAndView modelAndView = new ModelAndView("/ohana/list-post");
        return modelAndView;
    }

    @GetMapping("edit/{postId}")
    public ModelAndView edit(@ModelAttribute("userLogin") LoginResult userLogin, @PathVariable Long postId) {
        ModelAndView modelAndView = new ModelAndView();

        Optional<PostResult> postResult = postService.findById(postId);
        if (Objects.equals(userLogin.getId(), postResult.get().getUser().getId())
                && (postResult.get().getStatus() == StatusPost.PUBLISHED || postResult.get().getStatus() == StatusPost.DRAFT)) {
            modelAndView.setViewName("/ohana/edit-room");
            modelAndView.addObject("postEdit", postResult.get());
        }
        return modelAndView;
    }
}
