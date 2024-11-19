package com.ja.finalproject.domain.user.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ja.finalproject.domain.user.dto.UserDto;
import com.ja.finalproject.domain.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @RequestMapping(value = "loginPage",method = RequestMethod.GET)
    public String loginPage() {

        return "user/loginPage";
    }

    @GetMapping("registerPage")
    public String registerPage(Model model) {

        model.addAttribute("hobbyList", userService.getHobbyList());

        return "user/registerPage";
    }

    @RequestMapping("registerProcess")
    public String registerProcess(@ModelAttribute UserDto params, @RequestParam(value = "hobby") List<Integer> hobbyList) {

        // System.out.println(params);
        log.info("hobby = {}", hobbyList);
        userService.register(params, hobbyList);

        return "user/registerComplete";
    }

    @RequestMapping("loginProcess")
    public String loginProcess(HttpSession session, @ModelAttribute UserDto params) {

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
