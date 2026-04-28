package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.model.Job;
import com.example.demo.repository.JobRepository;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobRepository repo;

    // ✅ GET all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return repo.findAll();
    }

    // ✅ GET by ID
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable int id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found with id " + id));
    }

    // ✅ POST add job (with validation)
    @PostMapping
    public Job addJob(@Valid @RequestBody Job job) {
        return repo.save(job);
    }

    // ✅ PUT update job (safe update)
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable int id, @Valid @RequestBody Job job) {

        return repo.findById(id).map(existing -> {

            existing.setTitle(job.getTitle());
            existing.setDescription(job.getDescription());
            existing.setLocation(job.getLocation());
            existing.setSalary(job.getSalary());
            existing.setPhone(job.getPhone());

            return repo.save(existing);

        }).orElseThrow(() -> new RuntimeException("Job not found with id " + id));
    }

    // ✅ DELETE job (safe delete)
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable int id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Job not found with id " + id);
        }

        repo.deleteById(id);
        return "Job deleted successfully";
        
      
    }
}