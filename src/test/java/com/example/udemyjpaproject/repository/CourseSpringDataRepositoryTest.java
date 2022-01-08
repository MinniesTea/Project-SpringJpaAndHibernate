package com.example.udemyjpaproject.repository;

import com.example.udemyjpaproject.UdemyJpaProjectApplication;
import com.example.udemyjpaproject.entity.Course;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = UdemyJpaProjectApplication.class)
class CourseSpringDataRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseSpringDataRepository repository;

    @Test
    public void findById_CoursePresent() {
        Optional<Course> courseOptional = repository.findById(10001L);
        assertTrue(courseOptional.isPresent());
    }

    @Test
    public void findById_CourseNotPresent() {
        Optional<Course> courseOptional = repository.findById(20001L);
        assertFalse(courseOptional.isPresent());
    }

    //CRUD

    @Test
    public void playingAroundWithSpringDataRepository() {
        Course course = new Course("Microservices in 100 Steps");
        repository.save(course);

        course.setName("Microservices in 100 Steps - Updated");
        repository.save(course);
    }

    @Test
    public void playingAroundWithSpringDataRepository2() {
        logger.info("Courses -> {} ", repository.findAll());
        logger.info("Count -> {} ", repository.count());
    }

    //SORT

    @Test
    public void sort() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        logger.info("Sorted Courses -> {} ", repository.findAll(sort));
    }

    @Test
    //Control how many 'pages' of results you want returned
    public void pagination() {
        PageRequest pageRequest = PageRequest.of(0, 3);
        Page<Course> firstPage = repository.findAll(pageRequest);
        logger.info("First Page -> {} ", firstPage.getContent());

        Pageable secondPageable = firstPage.nextPageable();
        Page<Course> secondPage = repository.findAll(secondPageable);
        logger.info("Second Page -> {} ", secondPage.getContent());
    }

    @Test
    public void findUsingName() {
        logger.info("FindByName -> {} ", repository.findByName("JPA in 50 Steps"));
    }

    @Test
    public void findUsingIdAndName(){
        logger.info("Find by ID and name -> {}", repository.findByNameAndId("Mary", 30001L));
    }

    @Test
    public void countByName(){
        logger.info("Count by name -> {}", repository.countByName("Mary"));
    }









}