package com.example.demo.controllers;

import com.example.demo.models.Member;
import com.example.demo.repos.MemberRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController {

    MemberRepo memberRepo = new MemberRepo();

    @GetMapping("members/createMember")
    public String addMemberPage(){
        return "createMember";
    }

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

    @GetMapping("members/delMembers")
    public String deletedMembers(Model model){
        model.addAttribute("delMembers", memberRepo.getDeleted());
        return "listDelMembers";
    }

    @GetMapping("members/editMember")
    public String editMember(Model model){
        //later get member from html when person select who to edit.
        //for now added by just getting a random member
        List<Member> members = memberRepo.getAllMembers();

        model.addAttribute("member", members.get(0));
        return "editMember";
    }
    @PostMapping("/editedMember")
    public String editedMember(@RequestParam Map<String, String> AllRequestParam, Member member){
        //ok so I got the member, but dates crash. How to get that to work?00000000000000000000000000000000000000000000000000000000000000000
        System.out.println(AllRequestParam.entrySet());
        System.out.println(AllRequestParam.keySet());
        System.out.println(member);
        memberRepo.updateMemberInfo(member);
        //so do I just send the info and upate everything?
        //isn't that stupid?
        return "redirect:/editMember";
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

    @GetMapping("members/listMembers")
    public String getListMembers(Model model){
        List<Member> memberList = memberRepo.getAllMembers();
        List<Member> realMemberList = new ArrayList<>();
        for (Member member : memberList) {
            if (member.getIsDeleted() != 1){
                realMemberList.add(member);
            }
        }
        model.addAttribute("members", realMemberList);
        return "listMembers";
    }

    @PostMapping("/listMembers")
    public String getListMembers(@RequestParam boolean maleCheck, @RequestParam boolean femaleCheck, @RequestParam boolean delMembers, Model model){
        List<Member> memberList = memberRepo.getAllMembers();
        List<Member> realMemberList = new ArrayList<>();
        if (delMembers){
            for (Member member : memberList) {
                if (member.getIsDeleted() == 1){
                    if (femaleCheck && member.getIsFemale() == 1 || maleCheck && member.getIsFemale() == 0) {
                        realMemberList.add(member);
                    }
                }
            }
        }else {
            for (Member member : memberList) {
                if (member.getIsDeleted() != 1) {
                    if (femaleCheck && member.getIsFemale() == 1 || maleCheck && member.getIsFemale() == 0) {
                        realMemberList.add(member);
                    }
                }
            }
        }
        model.addAttribute("members", realMemberList);
        return "listMembers";
    }
}
