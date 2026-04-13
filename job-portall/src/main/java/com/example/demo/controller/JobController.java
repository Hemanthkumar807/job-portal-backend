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

    // GET all jobs
    @GetMapping
    public List<Job> getAllJobs() {
        return repo.findAll();
    }

    // POST add job
//    @PostMapping
//    public Job addJob(@RequestBody Job job) {
//        return repo.save(job);
//    }

    // PUT update job ✅
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable int id, @RequestBody Job job) {
        job.setId(id);
        return repo.save(job);
    }

    // DELETE job ✅
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable int id) {
        repo.deleteById(id);
        return "Job deleted successfully";
    }
    @GetMapping("/{id}")
    public Job getJobById(@PathVariable int id) {
        return repo.findById(id).orElse(null);
    }
    @PostMapping
    public Job addJob(@Valid @RequestBody Job job) {
        return repo.save(job);
    }
}