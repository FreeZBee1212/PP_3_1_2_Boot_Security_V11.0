package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;

    private final UserServiceImpl userService;
    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(RegistrationService registrationService, UserServiceImpl userService, RoleService roleService, UserRepository userRepository) {
        this.registrationService = registrationService;
        this.userService = userService;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@ModelAttribute("user") @Valid User user,
                               BindingResult bindingResult,
                               @RequestParam("roleIds") List<Long> roleIds) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (userService.isEmailUnique(user.getUsername())) {
            registrationService.registration(user, roleIds);
            return "redirect:/auth/login";
        } else {
            bindingResult.rejectValue("username", "", "This username is already registered");
            return "registration";
        }
    }
}
