package com.ceshop.lms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "task")
@Setter
@Getter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String name;


    @ManyToOne
    @JsonBackReference("task-course")
    private Course course;

    @ManyToMany(mappedBy = "tasks", cascade = CascadeType.PERSIST)
    @JsonBackReference("task-users")
    private List<User> users;
}
