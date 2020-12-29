package com.example.demo.controllers;

import com.example.demo.models.Hold;
import com.example.demo.repos.LoginRepo;
import com.example.demo.service.HelpMethods;
import com.example.demo.models.Member;
import com.example.demo.repos.MemberRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MemberController {

    LoginRepo login = new LoginRepo();

    MemberRepo memberRepo = new MemberRepo();

    @GetMapping("members/createMember")
    public String addMemberPage(Model model, HttpSession httpSession) {
        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));
        model.addAttribute("hold",memberRepo.getHold());
        return "createMember";
    }

    @PostMapping("/member/add")
    public String createMember(@RequestParam String firstName, @RequestParam String lastName, @RequestParam int isFemale,
                               @RequestParam String mail, @RequestParam String startDate,
                               @RequestParam String birthday, @RequestParam int phoneNumber,
                               @RequestParam String mail2, @RequestParam String phoneNumber2, @RequestParam String phoneNumber3,
                               @RequestParam int hold, @RequestParam boolean pointStavne,Model model, HttpSession httpSession) {

        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));

        int phoneNumber2Int;
        int phoneNumber3Int;
        if (phoneNumber2.equals(",0")){
            phoneNumber2Int = 0;
        }
        else {
            phoneNumber2Int = Integer.parseInt(phoneNumber2);
        }
        if (phoneNumber3.equals(",0")){
            phoneNumber3Int = 0;
        }
        else {
            phoneNumber3Int = Integer.parseInt(phoneNumber3);
        }

        try {
            Date tempStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
            Date tempBirthday = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
            Member tempMember = new Member(firstName,lastName,isFemale,mail,mail2,hold,pointStavne,tempStartDate,tempBirthday,phoneNumber,phoneNumber2Int,phoneNumber3Int);
            memberRepo.create(tempMember);
        }catch (ParseException e){
            System.out.println("Error at createMember() in HomeController");
            e.printStackTrace();
        }
        return "redirect:/members/createMember";
    }

    @GetMapping("members/editMember/{id}")
    public String editMember(Model model, @PathVariable int id, HttpSession httpSession){
        //later get member from html when person select who to edit.
        //for now added by just getting a random member
        List<Member> members = memberRepo.getAllMembers();
        List<Hold> holdList = memberRepo.getHold();
        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));

        for (Member member : members) {
            if (member.getId() == id){
                if (member.getHold() == 0){
                    member.setHold(1); //in case member doesn't have hold
                }
                for (Hold holdet : holdList){
                    if (member.getHold() == holdet.getId()){
                        member.holdHold = holdet;
                    }
                }

                model.addAttribute("member", member);
            }
        }
        model.addAttribute("holdListe", holdList);
        return "editMember";
    }
    @PostMapping("/editedMember")
    public String editedMember(@RequestParam Map<String, String> AllRequestParam, Member member, Model model, HttpSession httpSession) {
        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));
        //ok so I got the member, but dates crash. How to get that to work?00000000000000000000000000000000000000000000000000000000000000000
        System.out.println(AllRequestParam.entrySet());
        System.out.println(AllRequestParam.keySet());
        System.out.println(member);
        memberRepo.updateMemberInfo(member);
        //so do I just send the info and upate everything?
        //isn't that stupid?
        return "redirect:/members/listMembers";
    }

    @PostMapping("confirmDelete")
    public String confirmDelete(@RequestParam int id, @RequestParam String stopDate, Model model, HttpSession httpSession) {
        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));
        try {
            Date tempStopDate = new SimpleDateFormat("yyyy-MM-dd").parse(stopDate);
            memberRepo.quitNotDelete(id, tempStopDate);
        }catch (ParseException e){
            System.out.println("Error at confirmDelete() : MemberController");
            System.out.println(e.getMessage());
        }
        return "redirect:/members/listMembers";
    }

    @PostMapping("confirmPermDelete")
    public String permConfirmDelete(@RequestParam int id, Model model, HttpSession httpSession) {
        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));
        memberRepo.deletePermanently(id);
        return "redirect:/members/listMembers";
    }

    @GetMapping("members/listMembers")
    public String getListMembers(Model model, HttpSession httpSession) {
        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));
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

    @PostMapping("/listMembersFilter")
    public String getListMembers(@RequestParam boolean maleCheck, @RequestParam boolean femaleCheck,
                                 @RequestParam boolean delMembers, @RequestParam int maxAge,
                                 @RequestParam int minAge, @RequestParam boolean pointStavne, Model model, HttpSession httpSession) {

        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));
        System.out.println(pointStavne);
        List<Member> memberList = memberRepo.getAllMembers();
        List<Member> realMemberList = new ArrayList<>();

        HelpMethods methods = new HelpMethods();

        //kunne også sende hele denne her flotte if liste til en metode i helpmethods, men lige nu er den fin længde.
        for (Member member : memberList) {
            if (methods.addDeleted(delMembers,member.getIsDeleted())){ //doesn't add memembers if their isDeleted doesn't match delMembers variable
                if (femaleCheck && member.getIsFemale() == 1 || maleCheck && member.getIsFemale() == 0) { //gender added/not added
                    if (maxAge > methods.yearsBetween(member.getBirthday()) && minAge < methods.yearsBetween(member.getBirthday())){ //sort out wrong age groups
                        if (pointStavne == member.isPointStavne()){
                            realMemberList.add(member);
                        }

                    }
                }
            }
        }

        model.addAttribute("members", realMemberList);
        model.addAttribute("holdType", memberRepo.getHold());
        return "listMembers";
    }
}
