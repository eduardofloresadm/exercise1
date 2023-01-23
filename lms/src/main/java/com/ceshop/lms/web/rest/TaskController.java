package com.ceshop.lms.web.rest;

import com.ceshop.lms.model.Course;
import com.ceshop.lms.model.Task;
import com.ceshop.lms.model.User;
import com.ceshop.lms.repository.CourseRepository;
import com.ceshop.lms.repository.TaskRepository;
import com.ceshop.lms.repository.UserRepository;
import com.ceshop.lms.web.rest.dto.TaskDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/task")
@AllArgsConstructor
public class TaskController {
    private TaskRepository taskRepository;
    private UserRepository userRepository;

    private CourseRepository courseRepository;

    @GetMapping("/")
    public ResponseEntity<List<Task>> getTasks(){
        List result = taskRepository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable("id") Integer id){
        Optional<Task> result = taskRepository.findById(id);
        return result.map(Task -> ResponseEntity.of(result)).orElse(ResponseEntity.noContent().header("message","Can't find Task with id " + id).build());
    }

    @PostMapping    ("/")
    public  ResponseEntity<Task> addTask(@RequestBody @Valid Task Task){
        if(Task.getId()!= null){
            return ResponseEntity.internalServerError().header("message","Can't create a Task that already have a ID").build();
        }
        Task result = taskRepository.save(Task);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/complete")
    public  ResponseEntity<Task> completeTask(@RequestBody @Valid TaskDTO task){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email);
        List<Task> taskByUser = taskRepository.findAllByUsers(user).get();
        Task dbTask = taskRepository.findById(task.getId()).get();
        taskByUser.add(dbTask);
        user.setTasks(taskByUser);
        userRepository.save(user);
        Optional<Task> result = taskRepository.findById(dbTask.getId());
        return ResponseEntity.of(result);
    }

    @PatchMapping("/")
    public  ResponseEntity<Task> updateTask(@RequestBody @Valid Task Task){
        Task result = taskRepository.save(Task);

        return ResponseEntity.ok(result);
    }

    @GetMapping("course/{courseId}")
        public ResponseEntity<List<TaskDTO>> findAllTasksByCourseIdAndUserLoggedIn(@PathVariable("courseId") Integer id){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(email);
        Optional<Course> course = courseRepository.findByUsersIdAndId(user.getId(),id);
        Optional<List<Task>> tasksCompleted = course.map(course1 -> taskRepository.findAllByCourseAndUsers(course1, user).orElse(Collections.emptyList()));
        Optional<List<Task>> tasks = course.map(course1 -> taskRepository.findAllByCourseQuery(course1.getId()).orElse(Collections.emptyList()));
        List<TaskDTO> tasksDTO = tasks.get().stream().map(task -> {
            TaskDTO dto = new TaskDTO();
            dto.setId(task.getId());
            dto.setName(task.getName());
            dto.setUser(user);
            dto.setCourse(course.get());
            dto.setCompleted(tasksCompleted.get().stream().filter(task1 -> task1.getId() == task.getId()).count() == 1);
            return  dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(tasksDTO);
    }

}
