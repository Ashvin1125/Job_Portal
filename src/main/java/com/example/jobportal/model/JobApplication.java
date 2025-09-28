package com.example.jobportal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class JobApplication {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Job job;

    @ManyToOne
    private User user;

    private String resumeText; // simple text for demo

    public JobApplication() {}
    public JobApplication(Job job, User user, String resumeText) {
        this.job = job; this.user = user; this.resumeText = resumeText;
    }

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getResumeText() { return resumeText; }
    public void setResumeText(String resumeText) { this.resumeText = resumeText; }
}
