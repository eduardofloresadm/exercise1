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
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String name;

    @OneToMany(cascade=CascadeType.PERSIST, mappedBy = "course")
    @JsonBackReference
    private List<Task> tasks;

    @ManyToMany(mappedBy = "courses")
    @JsonBackReference
    private List<User> users;
}
