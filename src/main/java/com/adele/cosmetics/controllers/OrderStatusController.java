package com.adele.cosmetics.controllers;

import com.adele.cosmetics.dao.OrderStatusDao;
import com.adele.cosmetics.models.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OrderStatusController {

    private final OrderStatusDao orderStatusDao;

    @Autowired
    public OrderStatusController(OrderStatusDao orderStatusDao) {
        this.orderStatusDao = orderStatusDao;
    }

    @GetMapping("/order-status")
    public String listOrderStatuses(Model model) {
        List<OrderStatus> orderStatuses = orderStatusDao.findAll();
        model.addAttribute("orderStatuses", orderStatuses);
        return "order_status/list";
    }

    @GetMapping("/order-status/new")
    public String showAddForm(Model model) {
        model.addAttribute("orderStatus", new OrderStatus());
        return "order_status/form";
    }

    @PostMapping("/order-status/save")
    public String saveOrderStatus(@ModelAttribute("orderStatus") OrderStatus orderStatus) {
        if (orderStatus.getStatusId() == 0) {
            orderStatusDao.insert(orderStatus);
        } else {
            orderStatusDao.update(orderStatus);
        }
        return "redirect:/order-status";
    }

    @GetMapping("/order-status/edit")
    public String showEditForm(@RequestParam("id") int statusId, Model model) {
        OrderStatus orderStatus = orderStatusDao.findById(statusId);
        model.addAttribute("orderStatus", orderStatus);
        return "order_status/form";
    }

    @GetMapping("/order-status/delete")
    public String deleteOrderStatus(@RequestParam("id") int statusId) {
        orderStatusDao.delete(statusId);
        return "redirect:/order-status";
    }
}
