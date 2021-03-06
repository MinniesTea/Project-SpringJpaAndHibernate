package com.example.udemyjpaproject.repository;

import com.example.udemyjpaproject.UdemyJpaProjectApplication;
import com.example.udemyjpaproject.entity.Course;
import com.example.udemyjpaproject.entity.Review;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest(classes = UdemyJpaProjectApplication.class)
class CourseRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CourseRepository repository;

    @Autowired
    EntityManager em;


    //Tests Below

    @Test
    public void findById_basic() {
        Course course = repository.findById(10001L);
        assertEquals("JPA in 50 Steps", course.getName());
    }

    @Test
    @DirtiesContext
    public void save_basic() {
        // get a course
        Course course = repository.findById(10001L);
        assertEquals("JPA in 50 Steps", course.getName());

        // update details
        course.setName("JPA in 50 Steps - Updated");
        repository.save(course);

        // check the value
        Course course1 = repository.findById(10001L);
        assertEquals("JPA in 50 Steps - Updated", course1.getName());
    }


    @Test
    @DirtiesContext //After this test is run Spring will automatically reset the data so that the data doesn't change for the other tests
    public void deleteById_basic() {
        repository.deleteById(10002L);
        assertNull(repository.findById(10002L));
    }

    //Entity Manager

    @Test
    @DirtiesContext
    public void playWithEntityManager(){
        repository.playWithEntityManager();
    }

    @Test
    @Transactional
    public void retrieveReviewsForCourse() {
        Course course = repository.findById(10001L);
        logger.info("{}", course.getReviews());
    }

    @Test
    @Transactional
    public void retrieveCourseForReview() {
        Review review = em.find(Review.class, 40001L);
        logger.info("{}", review.getCourse());
    }

    //Cache

    @Test
    @Transactional
    public void findById_firstLevelCacheDemo() {

        Course course = repository.findById(10001L);
        logger.info("First Course Retrieved {}", course);

        Course course1 = repository.findById(10001L);
        logger.info("First Course Retrieved again {}", course1);

        assertEquals("JPA in 50 Steps", course.getName());
        assertEquals("JPA in 50 Steps", course1.getName());
    }

}