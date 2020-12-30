package com.example.demo.controllers;

import com.example.demo.models.Login;
import com.example.demo.repos.LoginRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

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

    @GetMapping("/createLogin")
    public String createLogin(Model model, HttpSession httpSession) {
        String adminStatus = login.isAdmin(httpSession);
        if (adminStatus.equals("yes")){
            model.addAttribute("adminMessage", "Logget ind som administrator");
            model.addAttribute("loginList", login.readAllLogin());
        }else{
            model.addAttribute("adminMessage", "Log venligst ind som administrator");
        }
        model.addAttribute("adminStatus", adminStatus);
        return "newLogin";
    }

    @PostMapping("/createLoginPost")
    public String createLoginPost(@RequestParam String username, @RequestParam String password, @RequestParam boolean isAdmin){
        login.createLogin(username, password, isAdmin);
        return "redirect:/createLogin";
    }

    @PostMapping("/confirmDeleteLogin")
    public String deleteLogin(@RequestParam int id){
        login.deleteLogin(id);
        return "redirect:/createLogin";
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam String username, @RequestParam String password, HttpSession httpSession, Model model){
        List<Login> loginList = login.readAllLogin();
        for (Login login : loginList) {
            if (password.equals(login.getPassword()) && username.equals(login.getUsername())){
                httpSession.setAttribute("loginstatus", "yes");
                if (login.getIsAdmin() == 1){
                    httpSession.setAttribute("adminStatus", "yes");
                }
            }
        }

        if (httpSession.getAttribute("loginstatus") != null){
            if (httpSession.getAttribute("loginstatus").equals("yes")){
                model.addAttribute("loginstatus", "yes");
                if (httpSession.getAttribute("adminStatus") != null){
                    if (httpSession.getAttribute("adminStatus").equals("yes")){
                        model.addAttribute("adminStatus", "yes");
                    }
                }
            }else{
                model.addAttribute("loginstatus", "no");
            }
        }else{
            model.addAttribute("loginstatus", "no");
        }
        return "redirect:/";
    }
}
