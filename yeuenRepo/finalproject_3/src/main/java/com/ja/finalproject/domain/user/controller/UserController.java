package com.ja.finalproject.domain.user.controller;

import java.util.List;

import com.ja.finalproject.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;

    @RequestMapping(value = "loginPage",method = RequestMethod.GET)
    public String loginPage() {

        return "user/loginPage";
    }

    @GetMapping("registerPage")
    public String registerPage(Model model) {

        model.addAttribute("hobbyList", userRepository.getHobbyList());

        return "user/registerPage";
    }

    @PostMapping("registerProcess")
    public String registerProcess(@ModelAttribute UserDto params, @RequestParam(value = "hobby") List<Integer> hobbyList) {

        log.info("hobby = {}", hobbyList);
        userService.register(params, hobbyList);

        return "user/registerComplete";
    }

    @PostMapping("loginProcess")
    public String loginProcess(HttpSession session, @ModelAttribute UserDto params) {

        UserDto sessionUserInfo = userRepository.getUserByUserIdAndPassword(params);

        // 로그인 실패
        if(sessionUserInfo == null) {
            log.info("session이 없어서 로그인 실패");
            return "user/loginFail";
        }

        // 로그인 성공
        session.setAttribute("sessionUserInfo", sessionUserInfo);

        return "redirect:/board/mainPage";
    }

    @GetMapping("logoutProcess")
    public String logoutProcess(HttpSession session){
        session.invalidate();
        return "redirect:/board/mainPage";
    }

    @GetMapping("mailAuthProcess")
    public String mailAuthProcess(@RequestParam String key){

        userRepository.authenticateMail(key);
        
        return "user/mailAuthComplete";
    }


    @GetMapping("sessionNullPage")
    public String sessionNullPage() {
        return "user/sessionNullPage";
    }
   

}
