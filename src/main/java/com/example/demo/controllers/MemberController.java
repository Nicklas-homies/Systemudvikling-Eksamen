package com.example.demo.controllers;

import com.example.demo.models.Member;
import com.example.demo.repos.MemberRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class MemberController {

    MemberRepo memberRepo = new MemberRepo();

    @PostMapping("/member/add")
    public String createMember(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int isFemale, @RequestParam String mail, @RequestParam String startDate, @RequestParam String birthday, @RequestParam int phoneNumber){
        try {
            Date tempStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date tempBirthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            Member tempMember = new Member(firstName,lastName,isFemale,mail,tempStartDate,tempBirthday,phoneNumber);
            memberRepo.create(tempMember);
        }catch (ParseException e){
            System.out.println("Error at createMember() in HomeController");
            e.printStackTrace();
        }
        return "confirmation";
    }

    @GetMapping("/delMembers")
    public String deletedMembers(Model model){
        model.addAttribute("delMembers", memberRepo.getDeleted());
        return "listDelMembers";
    }

    @GetMapping("/editMember")
    public String editMember(Model model){



        return "editMember";
    }

    //viser slet knap i funktion.
    @GetMapping("/sletKnap")
    public String sletKnap(Model model){
        Member member = new Member();
        member.setId(1);
        model.addAttribute("member",member);
        return "sletKnap";
    }
    @PostMapping("confirmDelete")
    public String confirmDelete(@RequestParam Map<String,String> AllReqeustParam){
        System.out.println(AllReqeustParam.entrySet());
        return "redirect:/sletKnap";
    }
}
