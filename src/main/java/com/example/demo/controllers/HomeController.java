package com.example.demo.controllers;

import com.example.demo.models.Member;
import com.example.demo.repos.MemberRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.model.ITemplateStart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SimpleTimeZone;

@Controller
public class HomeController {

    MemberRepo memberRepo = new MemberRepo();

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/member/add")
    public String createMember(@RequestParam String firstName,@RequestParam String lastName,@RequestParam int isFemale,@RequestParam String mail,@RequestParam String startDate,@RequestParam String birthday,@RequestParam int phoneNumber){
        try {
            Date tempStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date tempBirthday = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Member tempMember = new Member(firstName,lastName,isFemale,mail,tempStartDate,tempBirthday,phoneNumber);
            memberRepo.create(tempMember);
        }catch (ParseException e){
            System.out.println("Error at createMember() in HomeController");
            e.printStackTrace();
        }
        return "confirmation";
    }



    //nedenstående er temp, bruges udelukkende for at sletKnap virker
    @GetMapping("/sletKnap")
    public String sletKnap(Model model){
        Member exampleMember = new Member(); //test member


        model.addAttribute("member", exampleMember);

        return "sletKnap";
    }
    //nedenstående er temp, bruges udelukkende for at sletKnap virker
    @PostMapping("/confirmDelete")
    public String confirmDelete(@RequestParam Map<String,String> AllRequestParam){
        System.out.println(AllRequestParam.entrySet());

        return "redirect:/sletKnap";
    }
}
