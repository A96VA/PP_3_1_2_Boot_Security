package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;


@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public String userShow(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userShow = (User) authentication.getPrincipal();
        model.addAttribute("user", userShow);

        return "user";
    }
}