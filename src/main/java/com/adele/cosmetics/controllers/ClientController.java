package com.adele.cosmetics.controllers;

import com.adele.cosmetics.dao.ClientDao;
import com.adele.cosmetics.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class ClientController {

    private final ClientDao clientDao;

    @Autowired
    public ClientController(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @GetMapping("/clients")
    public String listClients(Model model) {
        List<Client> clients = clientDao.findAll();
        model.addAttribute("clientsList", clients);
        return "client/list";
    }

    @GetMapping("/clients/new")
    public String showAddForm(Model model) {
        model.addAttribute("client", new Client());
        return "client/form";
    }

    @PostMapping("/clients/save")
    public String saveClient(@ModelAttribute("client") Client client, Model model) {
        if (client.getClientId() == 0) {
            if (clientDao.isContactNumberTaken(client.getContactNumber(), 0)) {
                model.addAttribute("errorMessage", "Contact number already exists");
                return "client/form";
            }
            if (clientDao.isEmailTaken(client.getEmail(), 0)) {
                model.addAttribute("errorMessage", "Email already exists");
                return "client/form";
            }
            clientDao.insert(client);
        } else {
            Client existingClient = clientDao.findById(client.getClientId());

            if (!client.getContactNumber().equals(existingClient.getContactNumber())) {
                if (clientDao.isContactNumberTaken(client.getContactNumber(), client.getClientId())) {
                    model.addAttribute("errorMessage", "Contact number already exists");
                    return "client/form";
                }
            }

            if (!client.getEmail().equals(existingClient.getEmail())) {
                if (clientDao.isEmailTaken(client.getEmail(), client.getClientId())) {
                    model.addAttribute("errorMessage", "Email already exists");
                    return "client/form";
                }
            }

            clientDao.update(client);
        }
        return "redirect:/clients";
    }

    @GetMapping("/clients/edit")
    public String showEditForm(@RequestParam("id") int clientId, Model model) {
        Client client = clientDao.findById(clientId);
        model.addAttribute("client", client);
        return "client/form";
    }

    @GetMapping("/clients/delete")
    public String deleteClient(@RequestParam("id") int clientId) {
        clientDao.delete(clientId);
        return "redirect:/clients";
    }
}
