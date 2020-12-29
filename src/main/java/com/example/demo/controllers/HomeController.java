package com.example.demo.controllers;

import com.example.demo.repos.LoginRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class HomeController {

    LoginRepo login = new LoginRepo();

    @GetMapping("/")
    public String index(Model model, HttpSession httpSession) {
        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));
        return "index";
    }

    @GetMapping("/calendar")
    public String getCalendar(Model model, HttpSession httpSession) {
        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));
        return "calendar";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession httpSession, Model model){
        Map<String, String> loginMap = login.readAllLogin();
        System.out.println(loginMap);
        if (password.equals(loginMap.get(username))){
            httpSession.setAttribute("loginstatus", "yes");
        }

        if (httpSession.getAttribute("loginstatus") != null){
            if (httpSession.getAttribute("loginstatus").equals("yes")){
                model.addAttribute("loginstatus", "yes");
            }else{
                model.addAttribute("loginstatus", "no");
            }
        }else{
            model.addAttribute("loginstatus", "no");
        }
        return "redirect:/";
    }
}
