package com.example.demo.controllers;

import com.example.demo.models.Trainer;
import com.example.demo.repos.LoginRepo;
import com.example.demo.repos.TrainerRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class TrainerController {

    LoginRepo login = new LoginRepo();
    TrainerRepo trainerRepo = new TrainerRepo();

    @GetMapping("/hours")
    public String getHours(Model model, HttpSession httpSession) {
        model.addAttribute("loginstatus", login.isLoggedIn(httpSession));
        model.addAttribute("trainerList", trainerRepo.getAllTrainers());
        return "trainerPage";
    }

    @PostMapping("/addHours")
    public String addHours(@RequestParam int id, @RequestParam double hoursToAdd){
        trainerRepo.updateHours(hoursToAdd, id);
        return "redirect:/hours";
    }

    @PostMapping("/resetHours")
    public String resetHours(){
        for (Trainer trainer : trainerRepo.getAllTrainers()) {
            trainer.setHours(0);
            trainerRepo.updateTrainerInfo(trainer);
        }
        return "redirect:/hours";
    }

    @PostMapping("/createTrainer")
    public String createTrainer(@RequestParam String firstname, @RequestParam String lastname, @RequestParam double hours){
        trainerRepo.create(new Trainer(firstname, lastname, hours));
        return "redirect:/hours";
    }

    @PostMapping("/confirmDeleteTrainer")
    public String delTrainer(@RequestParam int id){
        trainerRepo.deleteTrainer(id);
        return "redirect:/hours";
    }

    @PostMapping("/editTrainer")
    public String editTrainer(@RequestParam String firstname, @RequestParam String lastname, @RequestParam double hours, @RequestParam int id){
        trainerRepo.updateTrainerInfo(new Trainer(id, firstname, lastname, hours));
        return "redirect:/hours";
    }
}
