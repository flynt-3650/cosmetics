package com.adele.cosmetics.controllers;

import com.adele.cosmetics.dao.ProductDao;
import com.adele.cosmetics.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    private final ProductDao productDao;

    @Autowired
    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productDao.findAll();
        model.addAttribute("productsList", products);
        return "product/list";
    }

    @GetMapping("/products/new")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        if (product.getProductId() == 0) {
            productDao.insert(product);
        } else {
            productDao.update(product);
        }
        return "redirect:/products";
    }

    @GetMapping("/products/edit")
    public String showEditForm(@RequestParam("id") int productId, Model model) {
        Product product = productDao.findById(productId);
        model.addAttribute("product", product);
        return "product/form";
    }

    @GetMapping("/products/delete")
    public String deleteProduct(@RequestParam("id") int productId) {
        productDao.delete(productId);
        return "redirect:/products";
    }
}
