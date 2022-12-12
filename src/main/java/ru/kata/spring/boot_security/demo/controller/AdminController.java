package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImplem;
import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImplem userService;

    @Autowired
    public AdminController(UserServiceImplem userService) {
        this.userService = userService;
    }


    @GetMapping()
    public String getAllUsers(Model model) {

        model.addAttribute("admins", userService.listUsers());
        model.addAttribute("listRoles", userService.listRoles());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userA = (User) authentication.getPrincipal();
        User userU = new User();
        model.addAttribute("userA", userA);
        model.addAttribute("userU", userU);

        return "admins";
    }

    @GetMapping( "/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }
    @PostMapping("/new")
    public String create(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @PostMapping("edit/{id}")
    public String edit(@ModelAttribute("user") User user) {
        userService.update(user);
        return "redirect:/admin";
    }

    @PostMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
