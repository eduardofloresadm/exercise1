package com.ceshop.lms.web.rest;

import com.ceshop.lms.model.Course;
import com.ceshop.lms.model.User;
import com.ceshop.lms.repository.CourseRepository;
import com.ceshop.lms.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
@AllArgsConstructor
public class CourseController {

    private CourseRepository courseRepository;

    private UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<Course>> getCourses() {
        List result = courseRepository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable("id") Integer id) {
        Optional<Course> result = courseRepository.findById(id);
        return result.map(course -> ResponseEntity.of(result)).orElse(ResponseEntity.noContent().header("message", "Can't find Course with id " + id).build());
    }

    @PostMapping("/")
    public ResponseEntity<Course> addCourse(@RequestBody @Valid Course course) {
        if (course.getId() != null) {
            return ResponseEntity.internalServerError().header("message", "Can't create a Course that already have a ID").build();
        }
        Course result = courseRepository.save(course);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/")
    public ResponseEntity<Course> updateCourse(@RequestBody @Valid Course course) {
        Course result = courseRepository.save(course);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/logged")
    public ResponseEntity<List<Course>> getCoursesByUserLoggedIn() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email);
        Optional<List<Course>> result = courseRepository.findAllByUsersId(user.getId());
        return ResponseEntity.of(result);
    }
}
