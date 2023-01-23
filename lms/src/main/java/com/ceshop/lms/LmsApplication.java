package com.ceshop.lms;

import com.ceshop.lms.model.Course;
import com.ceshop.lms.model.Task;
import com.ceshop.lms.model.User;
import com.ceshop.lms.repository.CourseRepository;
import com.ceshop.lms.repository.TaskRepository;
import com.ceshop.lms.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class LmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsApplication.class, args);
	}

	@Bean
	public CommandLineRunner createDemoDataIfNeeded(CourseRepository courseRepository, TaskRepository taskRepository, UserRepository userRepository) {
		return args -> {
			if ((courseRepository.count() == 0) && (taskRepository.count() == 0) && (userRepository.count() ==0)){
				userRepository.saveAll(generateUsers());
			}
		};
	}

	private List<User> generateUsers(){
		List<User> users = new ArrayList<>();

		List<Course> courses = generateCourses();

		User userTest1 = new User();
		userTest1.setName("Eduardo");
		userTest1.setEmail("eduardofloresadm@gmail.com");
		userTest1.setPassword("123456");

		userTest1.setCourses(courses.subList(0,2));
		users.add(userTest1);

		User userTest2 = new User();
		userTest2.setName("Isabella");
		userTest2.setEmail("isaflores8@yahoo.com.br");
		userTest2.setPassword("987654");
		userTest2.setCourses(courses.subList(2,4));
		users.add(userTest2);

		return users;
	}

	private List<Course> generateCourses(){
		List<Course> courses = new ArrayList<>();
		for (int c = 1; c < 5; c++) {
			Course course = new Course();
			course.setName("Course Test " + c);
			List<Task> tasks = new ArrayList<>();
			Integer qTask = (int)(Math.random() * ((10) + 1));
			for (int i = 1; i < 5; i++) {
				Task task = new Task();
				task.setName("Task " + i);
				tasks.add(task);
				task.setCourse(course);
			}
			course.setTasks(tasks);
			courses.add(course);
		}
		return courses;
	}

}
