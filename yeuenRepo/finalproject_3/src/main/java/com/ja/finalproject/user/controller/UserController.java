package com.ja.finalproject.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ja.finalproject.dto.UserDto;
import com.ja.finalproject.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("loginPage")
    public String loginPage() {

        return "user/loginPage";
    }

    @RequestMapping("registerPage")
    public String registerPage(Model model) {

        model.addAttribute("hobbyList", userService.getHobbyList());

        return "user/registerPage";
    }

    @RequestMapping("registerProcess")
    public String registerProcess(UserDto params, @RequestParam("hobby") List<Integer> hobbyList) {

        // System.out.println(params);
        userService.register(params, hobbyList);

        return "user/registerComplete";
    }

    @RequestMapping("loginProcess")
    public String loginProcess(HttpSession session, UserDto params) {

        UserDto sessionUserInfo = userService.getUserByUserIdAndPassword(params);

        // 로그인 실패
        if(sessionUserInfo == null) {
            return "user/loginFail";
        }

        // 로그인 성공
        session.setAttribute("sessionUserInfo", sessionUserInfo);

        return "redirect:/board/mainPage";
    }

    @RequestMapping("logoutProcess")
    public String logoutProcess(HttpSession session){
        session.invalidate();
        return "redirect:/board/mainPage";
    }

    @RequestMapping("mailAuthProcess")
    public String mailAuthProcess(@RequestParam String key){

        userService.authenticateMail(key);
        
        return "user/mailAuthComplete";
    }


    @RequestMapping("sessionNullPage")
    public String sessionNullPage() {
        return "user/sessionNullPage";
    }
   

}
