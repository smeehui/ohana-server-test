package vn.tg.ohana.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.tg.ohana.dto.LoginResult;
import vn.tg.ohana.repository.model.StatusPost;
import vn.tg.ohana.repository.model.User;
import vn.tg.ohana.repository.model.UserStatus;
import vn.tg.ohana.services.PostService;
import vn.tg.ohana.services.UserService;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    PostService postService;

    @ModelAttribute("adminLogin")
    public LoginResult getUserLoginFromCookie(@CookieValue(value = "loginAdmin", defaultValue = "0") String loginUsername) {
        LoginResult adminLogin = null;
        if (!loginUsername.equals("0")) {
            adminLogin = userService.findByEmailOrPhone(loginUsername, loginUsername);
        }
        return adminLogin;
    }


    @GetMapping()
    public Object showIndex(@ModelAttribute("adminLogin") LoginResult userLogin) {
        if (userLogin == null) {
            return "redirect:/dashboard/sign-in";
        }
        ModelAndView modelAndView = new ModelAndView("/dashboard/index");
        Long quantity = postService.getQuantityStatus(StatusPost.PENDING_REVIEW);
        Long user = userService.getQuantityUser(UserStatus.ACTIVATED);
        modelAndView.addObject("pendingReview", quantity);
        modelAndView.addObject("activated", user);
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }

    @GetMapping("/admin")
    public Object showAdmin(@ModelAttribute("adminLogin") LoginResult userLogin) {
        if (userLogin == null) {
            return "redirect:/dashboard/sign-in";
        }
        ModelAndView modelAndView = new ModelAndView("/dashboard/admin");
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }


    @GetMapping("/users")
    public Object showUser(@ModelAttribute("adminLogin") LoginResult userLogin) {
        if (userLogin == null) {
            return "redirect:/dashboard/sign-in";
        }
        ModelAndView modelAndView = new ModelAndView("/dashboard/users");
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }
//    @GetMapping("/users")
//    public ModelAndView showUser() {
//
//        ModelAndView modelAndView = new ModelAndView();
//
//        modelAndView.setViewName("/dashboard/users");
//
//        List<User> user = userService.findAll();
//
//        modelAndView.addObject("users", user);
//
//        return modelAndView;
//    }

    @GetMapping("/add-user")
    public Object showAddUser(@ModelAttribute("adminLogin") LoginResult userLogin) {
        if (userLogin == null) {
            return "redirect:/dashboard/sign-in";
        }
        ModelAndView modelAndView = new ModelAndView("/dashboard/users-add-user");
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }
}