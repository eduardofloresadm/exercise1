package com.ceshop.lms.web.rest.dto;

import com.ceshop.lms.model.Course;
import com.ceshop.lms.model.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class TaskDTO {

    private Integer id;

    private String name;

    @JsonIgnore
    private Course course;

    @JsonIgnore
    private User user;

    private Boolean completed;

}
