package com.kaishengit.controller;

import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.service.AccountService;
import com.kaishengit.crm.service.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by 蔡林红 on 2017/11/8.
 */
@Controller
public class HelloController {

    @Autowired
    private AccountService accountService;


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login/new")
    public String login(String mobile,String password,RedirectAttributes redirectAttributes,
                        HttpSession session) {
        try {
            Account acount = accountService.longin(mobile, password);
           session.setAttribute("curr_account", acount);
            return "home";
        } catch (AuthenticationException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/login";
        }


    }
    @GetMapping("/loginout")
    public String loginout(HttpSession session,RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("message","您已安全退出");
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
