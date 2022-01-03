package com.example.udemyjpaproject.repository;


import com.example.udemyjpaproject.entity.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Repository
@Transactional
public class CourseRepository {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EntityManager em;

    public Course findById(Long id){
        return em.find(Course.class, id);
    }

    public Course save(Course course) {

        if (course.getId() == null) {
            em.persist(course); //Creates entity in the database
        } else {
            em.merge(course); //Save changes to database
        }

        return course;
    }

    public void deleteById(Long id){
        Course course = findById(id);
        em.remove(course);

    }

    //Entity Manager

    /*
    All the entities that are being saved through the entity manager are thieved to something called persistence context.
    The persistence context keeps track of all the different entities which are changed during this specific
    transaction, and also it keeps track of all the changes that needs to be stored back to the database.
     */

    public void playWithEntityManager() {

        //Creating Courses
        Course course1 = new Course("Web Services in 100 Steps");
        em.persist(course1);
        Course course2 = new Course("Finding Udemy Tutorials");
        em.persist(course2);

        em.flush(); //Changes up to this point are sent out to the database.

        em.detach(course2); //The changes to Course2 are no longer tracked by the entity manager. So the update doesn't execute.

        //em.clear() - Clears everything that's in the entity manager.

        //Updating Courses
        course1.setName("Web Services in 100 Steps - Updated");
        course2.setName("Finding Udemy Courses");

        em.refresh(course1); //Change the updated version back to its original form.

        em.flush();

        /*
        Whenever you are inside a transaction, and you are managing something with the entity manager, whenever
        you are updating something or deleting something, or you are inserting something in that particular thing
        continues to be managed by the entity manager until the end of the transaction to the courts here,
        even though I only saved it here.

        Whatever changes you are doing to the court later in the transaction are also being tracked by the entity
        manager, and they are also persistent. So if I do a set, name something after that as well, then entity
        manager keeps track of those changes and persis them.
         */

    }


}
