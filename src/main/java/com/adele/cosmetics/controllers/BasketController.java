package com.adele.cosmetics.controllers;

import com.adele.cosmetics.dao.BasketDao;
import com.adele.cosmetics.models.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BasketController {

    private final BasketDao basketDao;

    @Autowired
    public BasketController(BasketDao basketDao) {
        this.basketDao = basketDao;
    }

    @GetMapping("/basket")
    public String listBasketEntries(Model model) {
        List<Basket> basketList = basketDao.findAll();
        model.addAttribute("basketList", basketList);
        return "basket/list";
    }

    @GetMapping("/basket/new")
    public String showAddForm(Model model) {
        model.addAttribute("basket", new Basket());
        return "basket/form";
    }

    @PostMapping("/basket/save")
    public String saveBasket(@ModelAttribute("basket") Basket basket) {
        if (basket.getEntryBasket() == 0) {
            basketDao.insert(basket);
        } else {
            basketDao.update(basket);
        }
        return "redirect:/basket";
    }

    @GetMapping("/basket/edit")
    public String showEditForm(@RequestParam("id") int entryBasket, Model model) {
        Basket basket = basketDao.findById(entryBasket);
        model.addAttribute("basket", basket);
        return "basket/form";
    }

    @GetMapping("/basket/delete")
    public String deleteBasket(@RequestParam("id") int entryBasket) {
        basketDao.delete(entryBasket);
        return "redirect:/basket";
    }
}

