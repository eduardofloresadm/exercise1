package com.ceshop.lms.web.rest;

import com.ceshop.lms.model.Course;
import com.ceshop.lms.model.User;
import com.ceshop.lms.repository.CourseRepository;
import com.ceshop.lms.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){
        List result = userRepository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id){
        Optional<User> result = userRepository.findById(id);
        return result.map(course -> ResponseEntity.of(result)).orElse(ResponseEntity.noContent().header("message","Can't find Course with id " + id).build());
    }

    @PostMapping("/")
    public  ResponseEntity<User> addUser(@RequestBody @Valid User user){
        if(user.getId()!= null){
            return ResponseEntity.internalServerError().header("message","Can't create a User that already have a ID").build();
        }
        User result = userRepository.save(user);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/")
    public  ResponseEntity<User> updateUser(@RequestBody @Valid User user){
        User result = userRepository.save(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/logged")
    public ResponseEntity<User> getUserLoggedin(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User result = userRepository.findUserByEmail(email);
        return ResponseEntity.ok().body(result);
    }

}
