package com.ceshop.lms.repository;

import com.ceshop.lms.model.Course;
import com.ceshop.lms.model.Task;
import com.ceshop.lms.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query(value = "Select t from Task t where t.course.id = :courseid")
    public Optional<List<Task>> findAllByCourseQuery(@Param("courseid") Integer courseId);

    public Optional<List<Task>> findAllByCourseAndUsers(Course course, User user);

    public Optional<List<Task>> findAllByUsers(User user);

}
