package com.example.jobportal.controller;

import com.example.jobportal.model.User;
import com.example.jobportal.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepo;
    public AuthController(UserRepository userRepo) { this.userRepo = userRepo; }

    @GetMapping("/")
    public String root() { return "redirect:/jobs"; }

    @GetMapping("/register")
    public String registerForm(Model m) {
        m.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user, Model model, HttpSession session) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }
        userRepo.save(user);
        session.setAttribute("userId", user.getId());
        return "redirect:/jobs";
    }

    @GetMapping("/login")
    public String loginForm(Model m) {
        m.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session, Model model) {
        var uOpt = userRepo.findByEmail(email);
        if (uOpt.isEmpty() || !uOpt.get().getPassword().equals(password)) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
        session.setAttribute("userId", uOpt.get().getId());
        return "redirect:/jobs";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
