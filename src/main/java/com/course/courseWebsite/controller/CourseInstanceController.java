package com.course.courseWebsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.course.courseWebsite.exceptions.ResourceNotFoundException;
import com.course.courseWebsite.model.CourseInstance;
import com.course.courseWebsite.repository.CourseInstanceRepository;

//@Controller
@RestController
@CrossOrigin
@RequestMapping("/api")
public class CourseInstanceController {
    @Autowired
    private CourseInstanceRepository instanceRepository;

    @PostMapping("/instances")
    public CourseInstance createInstance(@RequestBody CourseInstance instance) {
        return instanceRepository.save(instance);
    }

    @GetMapping("/instances/{year}/{semester}")
    public List<CourseInstance> getInstancesByYearAndSemester(@PathVariable Integer year, @PathVariable Integer semester) {
        return instanceRepository.findByYearAndSemester(year, semester);
    }

    @GetMapping("instances/{year}/{semester}/{courseId}")
    public CourseInstance getInstanceById(@PathVariable Integer year, @PathVariable Integer semester, @PathVariable Long courseId) {
        return instanceRepository.findByYearAndSemester(year, semester).stream()
                .filter(instance -> instance.getCourse().getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Instance not found"));
    }

    @DeleteMapping("/instances/{year}/{semester}/{courseId}")
    public void deleteInstance(@PathVariable Integer year, @PathVariable Integer semester, @PathVariable Long courseId) {
        CourseInstance instance = getInstanceById(year, semester, courseId);
        instanceRepository.delete(instance);
    }
}

