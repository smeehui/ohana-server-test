package vn.tg.ohana.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.tg.ohana.dto.LoginResult;
import vn.tg.ohana.dto.PostResult;
import vn.tg.ohana.repository.model.Cart;
import vn.tg.ohana.services.PostService;
import vn.tg.ohana.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class PostController {
    @Autowired
    private UserService userService;

    @ModelAttribute("adminLogin")
    public LoginResult getUserLoginFromCookie(@CookieValue(value = "loginAdmin", defaultValue = "0") String loginUsername) {
        LoginResult adminLogin = null;
        if (!loginUsername.equals("0")) {
            adminLogin = userService.findByEmailOrPhone(loginUsername, loginUsername);
        }
        return adminLogin;
    }

    @Autowired
    private PostService postService;


    @GetMapping("/posts")
    public Object showPost(@ModelAttribute("adminLogin") LoginResult userLogin) {
        if (userLogin == null) {
            return "redirect:/dashboard/sign-in";
        }
        ModelAndView modelAndView = new ModelAndView("/dashboard/posts");
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }

    @GetMapping("/unapproved")
    public Object showUnapproved(@ModelAttribute("adminLogin") LoginResult userLogin) {
        if (userLogin == null) {
            return "redirect:/dashboard/sign-in";
        }
        ModelAndView modelAndView = new ModelAndView("/dashboard/unapproved");
        List<PostResult> posts = postService.findAll();
        modelAndView.addObject("posts", posts);
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }


//    @GetMapping
//    public ResponseEntity<?> getAll(){
//        List<PostResult> posts = postService.findAll();
//
//        return  new ResponseEntity<>(posts, HttpStatus.OK);
//    }

//    @GetMapping("/unapproved")
//    public ModelAndView showUnapproved() {
//
//        ModelAndView modelAndView = new ModelAndView();
//
//        modelAndView.setViewName("post");
//
//        List<PostResult> posts = postService.findAll();
//        System.out.println(posts);
//
//        modelAndView.addObject("posts", posts);
//
//        return modelAndView;
//    }

//    @GetMapping("/posts")
//    public ModelAndView showPosts() {
//
//        ModelAndView modelAndView = new ModelAndView("/dashboard/posts");
//
//        List<PostResult> posts = postService.findAll();
//
//        modelAndView.addObject("posts", posts);
//
//        return modelAndView;
//    }


}
