package com.adele.cosmetics.controllers;

import com.adele.cosmetics.dao.ApplicationDao;
import com.adele.cosmetics.models.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ApplicationController {

    private final ApplicationDao applicationDAO;

    @Autowired
    public ApplicationController(ApplicationDao applicationDAO) {
        this.applicationDAO = applicationDAO;
    }

    @GetMapping("/applications")
    public String listApplications(Model model) {
        List<Application> applications = applicationDAO.findAll();
        model.addAttribute("applicationsList", applications);
        return "application/list";
    }

    @GetMapping("/applications/new")
    public String showAddForm(Model model) {
        model.addAttribute("appInstance", new Application());
        return "application/form";
    }

    @PostMapping("/applications/save")
    public String saveApplication(@ModelAttribute("appInstance") Application application) {
        if (application.getApplicationId() == 0) {
            applicationDAO.insert(application);
        } else {
            applicationDAO.update(application);
        }
        return "redirect:/applications";
    }

    @GetMapping("/applications/edit")
    public String showEditForm(@RequestParam("id") int applicationId, Model model) {
        Application application = applicationDAO.findById(applicationId);
        model.addAttribute("appInstance", application);
        return "application/form";
    }

    @GetMapping("/applications/delete")
    public String deleteApplication(@RequestParam("id") int applicationId) {
        applicationDAO.delete(applicationId);
        return "redirect:/applications";
    }
}
