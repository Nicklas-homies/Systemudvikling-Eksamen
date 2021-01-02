package com.example.demo.controllers;

import com.example.demo.models.Member;
import com.example.demo.repos.LoginRepo;
import com.example.demo.repos.MemberRepo;
import com.example.demo.service.HelpMethods;
import com.example.demo.util.MemberExcelExporter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    LoginRepo login = new LoginRepo();
    MemberRepo memberRepo = new MemberRepo();

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

    @PostMapping("/members/export/excel")
    public void exportToExcel(HttpServletResponse response, @RequestParam boolean maleCheck,
                              @RequestParam boolean femaleCheck, @RequestParam boolean delMembers,
                              @RequestParam int maxAge, @RequestParam int minAge,
                              @RequestParam boolean pointStavne, @RequestParam int hold) throws IOException {

            List<Member> memberList = memberRepo.getAllMembers();
            List<Member> realMemberList = new ArrayList<>();

            HelpMethods methods = new HelpMethods();

            //kunne også sende hele denne her flotte if liste til en metode i helpmethods, men lige nu er den fin længde.
            for (Member member : memberList) {
                if (methods.addDeleted(delMembers,member.getIsDeleted())){ //doesn't add memembers if their isDeleted doesn't match delMembers variable
                    if (femaleCheck && member.getIsFemale() == 1 || maleCheck && member.getIsFemale() == 0) { //gender added/not added
                        if (maxAge > methods.yearsBetween(member.getBirthday()) && minAge < methods.yearsBetween(member.getBirthday())){ //sort out wrong age groups
                            if (pointStavne == member.isPointStavne()){
                                if (hold == 0 || member.getHold() == hold){
                                    realMemberList.add(member);
                                }
                            }
                        }
                    }
                }
            }
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=medlemmer_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Member> listMembers = realMemberList;

        MemberExcelExporter excelExporter = new MemberExcelExporter(listMembers);

        excelExporter.export(response);
    }
}
