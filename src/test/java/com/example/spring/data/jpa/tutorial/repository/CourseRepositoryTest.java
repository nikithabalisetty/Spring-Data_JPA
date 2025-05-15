/*
 * Copyright (c) 2025. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.spring.data.jpa.tutorial.repository;

import com.example.spring.data.jpa.tutorial.SpringDataJpaTutorialApplication;
import com.example.spring.data.jpa.tutorial.entity.Course;
import com.example.spring.data.jpa.tutorial.entity.Student;
import com.example.spring.data.jpa.tutorial.entity.Teacher;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = SpringDataJpaTutorialApplication.class)
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void printCourses(){
        List<Course> courses = courseRepository.findAll();
        System.out.println("Courses: " + courses);
    }

    @Test
    public void saveCourseWithTeacher(){
        Teacher teacher = Teacher.builder()
                .firstName("Priyanka")
                .lastName("Singh")
                .build();

        Course course = Course.builder()
                .courseTitle("Python")
                .credit(5)
                .teacher(teacher)
                .build();

        courseRepository.save(course);
    }

    @Test
    public void findAllPagination(){
        Pageable firstPagewithThreeRecords = PageRequest.of(0,3);
        Pageable secondPagewithThreeRecords = PageRequest.of(1,2);

        List<Course> courses = courseRepository.findAll(firstPagewithThreeRecords).getContent();

        Long totalElements = courseRepository.findAll(firstPagewithThreeRecords).getTotalElements();
        int totalPages = courseRepository.findAll(firstPagewithThreeRecords).getTotalPages();

        System.out.println("Total elements: " + totalElements);
        System.out.println("Total pages: " + totalPages);
        System.out.println("First page courses: " + courses);
    }

    @Test
    public void findAllSorting(){
        Pageable sortByTitle = PageRequest.of(0,2, Sort.by("courseTitle"));

        Pageable sortByCreditDesc = PageRequest.of(0,2, Sort.by("credit").descending());
        Pageable sortByTitleAndCreditDesc = PageRequest.of(0,2, Sort.by("courseTitle").descending().and(Sort.by("credit")));

        List<Course> courses = courseRepository.findAll(sortByTitle).getContent();
        System.out.println("Courses sorted by title: " + courses);
    }

    @Test
    @Transactional
    public void printfindByCourseTitleContaining(){
        Pageable firstPageTenRecords = PageRequest.of(0,10);
        List<Course> courses = courseRepository.findByCourseTitleContaining("D", firstPageTenRecords).getContent();
        System.out.println("Courses found by title containing 'D': " + courses);

    }

    @Test
    public void saveCourseWithStudentAndTeacher(){
        Teacher teacher = Teacher.builder()
                .firstName("Lizze")
                .lastName("Morgan")
                .build();

        Student student = Student.builder()
                .firstName("Abhishek")
                .lastName("Sharma")
                .emailId("abhishek@gmail.com")
                .build();

        Course course = Course.builder()
                .courseTitle("AI")
                .credit(6)
                .teacher(teacher)
                .build();

        course.addStudents(student);

        courseRepository.save(course);
    }
}