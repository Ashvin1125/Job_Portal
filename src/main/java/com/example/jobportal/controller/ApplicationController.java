package com.example.jobportal.controller;

import com.example.jobportal.model.Job;
import com.example.jobportal.model.JobApplication;
import com.example.jobportal.model.User;
import com.example.jobportal.repository.JobApplicationRepository;
import com.example.jobportal.repository.JobRepository;
import com.example.jobportal.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/apply")
public class ApplicationController {
    private final JobRepository jobRepo;
    private final UserRepository userRepo;
    private final JobApplicationRepository appRepo;

    public ApplicationController(JobRepository jobRepo, UserRepository userRepo, JobApplicationRepository appRepo) {
        this.jobRepo = jobRepo; this.userRepo = userRepo; this.appRepo = appRepo;
    }

    @GetMapping("/{jobId}")
    public String applyForm(@PathVariable Long jobId, Model m) {
        Job job = jobRepo.findById(jobId).orElseThrow();
        m.addAttribute("job", job);
        m.addAttribute("application", new JobApplication());
        return "apply";
    }

    @PostMapping("/{jobId}")
    public String applySubmit(@PathVariable Long jobId,
                              @RequestParam String resumeText,
                              HttpSession session,
                              Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please login to apply.");
            return "login";
        }
        User user = userRepo.findById(userId).orElseThrow();
        Job job = jobRepo.findById(jobId).orElseThrow();
        JobApplication ja = new JobApplication(job, user, resumeText);
        appRepo.save(ja);
        return "redirect:/jobs";
    }
}
