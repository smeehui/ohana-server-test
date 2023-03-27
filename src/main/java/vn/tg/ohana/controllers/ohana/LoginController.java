package vn.tg.ohana.controllers.ohana;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.tg.ohana.dto.*;
import vn.tg.ohana.services.UserService;
import vn.tg.ohana.services.impl.GoogleService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@Controller
public class LoginController {

    @Autowired
    UserService userService;
    @Autowired
    GoogleService googleService;

    @ModelAttribute("userLogin")
    public LoginResult getUserLoginFromCookie(@CookieValue(value = "loginUser", defaultValue = "0") String loginUsername) {
        LoginResult userLogin = null;
        if (!loginUsername.equals("0")) {
            userLogin = userService.findByEmailOrPhone(loginUsername, loginUsername);
        }
        return userLogin;
    }


    @GetMapping("/sign-in")
    public Object signIn(String email,@CookieValue(value = "loginUser", defaultValue = "0") String loginUsername) {
        if(loginUsername.equals("0")) {
            ModelAndView modelAndView = new ModelAndView("/ohana/sign-in");
            if (email != null) {
                LoginParam loginParam = new LoginParam();
                loginParam.setPhoneOrEmail(email);
                modelAndView.addObject("user", loginParam);
            } else {
                modelAndView.addObject("user", new LoginParam());
            }

            return modelAndView;
        }else {
            return "redirect:/";
        }
    }

    @GetMapping("/sign-up")
    public ModelAndView signUp(String t) throws GeneralSecurityException, IOException {
        GooglePojo googlePojo = null;
        SignUpParam signUpParam = new SignUpParam();
        if (t != null) {
            googlePojo = googleService.verifyToken(t);
            signUpParam.setFullName(googlePojo.getName());
            signUpParam.setPhoneOrEmail(googlePojo.getEmail());
        }
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
        modelAndView.addObject("user", signUpParam);
        return modelAndView;
    }


    @PostMapping("/sign-in")
    public Object doLoginGmail(@ModelAttribute GGSignInParam ggSignInParam, @CookieValue(value = "loginUser", defaultValue = "0") String loginUsername, HttpServletResponse response, HttpServletRequest request) throws GeneralSecurityException, IOException {
        GooglePojo googlePojo = googleService.verifyToken(ggSignInParam.getCredential());
        ModelAndView modelAndView = new ModelAndView();
        LoginResult loginResult = userService.findByEmail(googlePojo.getEmail());
        if (loginResult != null) {
            modelAndView.setViewName("/ohana/index");
            Cookie cookie = new Cookie("loginUser", loginResult.getEmail());
            cookie.setMaxAge(24 * 60 * 60 * 30);
            response.addCookie(cookie);
            return "redirect:/";
        }
        return String.format("redirect:/sign-up?t=%s", ggSignInParam.getCredential());
    }

    @PostMapping("/sign-up")
    public Object handleSignUp(@ModelAttribute("user") SignUpParam signUpParam, String t, HttpServletResponse response) throws GeneralSecurityException, IOException {
        GooglePojo googlePojo = null;
        ModelAndView modelAndView = new ModelAndView("/ohana/sign-up");
        try {
            if (t != null) {
                googlePojo = googleService.verifyToken(t);
                if (!googlePojo.getEmail().equalsIgnoreCase(signUpParam.getPhoneOrEmail()))
                    throw new RuntimeException("Thông tin đăng nhập không hợp lệ");
            }

            if (googlePojo != null) {
                System.out.println("vaof day");
                googlePojo.setPassword(signUpParam.getPassword());
                System.out.println(signUpParam);
                LoginResult userResult = userService.saveGoogleEmail(googlePojo);
                Cookie cookie = new Cookie("loginUser", userResult.getEmail());
                cookie.setMaxAge(24 * 60 * 60 * 30);
                response.addCookie(cookie);
                return "redirect:/";
            } else {
//                Đăng kí
//                userService.saveGoogleEmail(googlePojo);
                UserResult userResult = userService.signUp(signUpParam);
                return "redirect:/sign-in?email=" + signUpParam.getPhoneOrEmail();
            }
//            modelAndView.addObject("message", "Đăng nhập thành công");
//            modelAndView.addObject("userLogin", userResult);
        } catch (Exception ex) {
            modelAndView.addObject("messageExits", ex.getMessage());
            System.out.println(ex.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/sign-out")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("loginUser", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setComment("EXPIRING COOKIE at " + System.currentTimeMillis());
        response.addCookie(cookie);
        return "redirect:/";
    }
}
