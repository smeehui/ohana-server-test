package vn.tg.ohana.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.tg.ohana.dto.LoginParam;
import vn.tg.ohana.dto.LoginResult;
import vn.tg.ohana.repository.model.StatusPost;
import vn.tg.ohana.services.PostService;
import vn.tg.ohana.services.UserService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/dashboard")
public class LoginDashboardController {

    @Autowired
    UserService userService;

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

    @GetMapping("/sign-in")
    public Object showFormSignIn(@ModelAttribute("adminLogin") LoginResult adminLogin) {
        ModelAndView modelAndView = new ModelAndView("/dashboard/sign-in");
        if (adminLogin == null) {
            modelAndView.addObject("user", new LoginParam());
            return modelAndView;
        } else {
            return "redirect:/dashboard";
        }

    }

    @PostMapping("/sign-in")
    public Object SignIn(@ModelAttribute("user") LoginParam loginParam, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView();
        boolean check = userService.checkAdmin(loginParam);
        if (check) {
            LoginResult adminLogin = userService.findByEmailOrPhone(loginParam.getPhoneOrEmail(), loginParam.getPhoneOrEmail());
            modelAndView.setViewName("/dashboard/index");
            Long quantity = postService.getQuantityStatus(StatusPost.PENDING_REVIEW);
            modelAndView.addObject("pendingReview", quantity);
            Cookie cookie = new Cookie("loginAdmin", adminLogin.getEmail());
            cookie.setMaxAge(24 * 60 * 60 * 30);
            response.addCookie(cookie);
        } else {
            modelAndView.setViewName("/dashboard/sign-in");
        }
        return modelAndView;

    }
    @GetMapping("/sign-out")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("loginAdmin", "");
        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        cookie.setComment("EXPIRING COOKIE at " + System.currentTimeMillis());
        response.addCookie(cookie);
        return "redirect:/dashboard/sign-in";
    }
}
