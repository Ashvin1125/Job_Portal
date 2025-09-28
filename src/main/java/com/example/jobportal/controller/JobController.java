package com.example.jobportal.controller;

import com.example.jobportal.model.Job;
import com.example.jobportal.repository.JobRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/jobs")
public class JobController {
    private final JobRepository jobRepo;
    public JobController(JobRepository jobRepo) { this.jobRepo = jobRepo; }

    @GetMapping
    public String listJobs(Model model) {
        model.addAttribute("jobs", jobRepo.findAll());
        return "jobs";
    }

    @GetMapping("/create")
    public String showCreate(Model m, HttpSession session) {
        m.addAttribute("job", new Job());
        return "createJob";
    }

    @PostMapping("/create")
    public String createJob(@ModelAttribute Job job) {
        jobRepo.save(job);
        return "redirect:/jobs";
    }
}
