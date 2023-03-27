package vn.tg.ohana.controllers.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import vn.tg.ohana.dto.LoginResult;
import vn.tg.ohana.services.UserService;

@Controller
@RequestMapping("/dashboard")
public class UtilityController {
    @Autowired
    UserService userService;

    @ModelAttribute("adminLogin")
    public LoginResult getUserLoginFromCookie(@CookieValue(value = "loginAdmin", defaultValue = "0") String loginUsername) {
        LoginResult adminLogin = null;
        if (!loginUsername.equals("0")) {
            adminLogin = userService.findByEmailOrPhone(loginUsername, loginUsername);
        }
        return adminLogin;
    }

    @GetMapping("/newUtility")
    public Object showNewUtility(@ModelAttribute("adminLogin") LoginResult userLogin) {
        if (userLogin == null) {
            return "redirect:/dashboard/sign-in";
        }
        ModelAndView modelAndView = new ModelAndView("/dashboard/new-utility");
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }

    @GetMapping("/utility")
    public Object showUtility(@ModelAttribute("adminLogin") LoginResult userLogin) {
        if (userLogin == null) {
            return "redirect:/dashboard/sign-in";
        }
        ModelAndView modelAndView = new ModelAndView("/dashboard/new-utility");
        modelAndView.addObject("userLogin", userLogin);
        return modelAndView;
    }
}
