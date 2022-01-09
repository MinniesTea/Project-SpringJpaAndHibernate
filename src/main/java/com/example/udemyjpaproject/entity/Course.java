package com.example.udemyjpaproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table - To specify the table name, as it could be different elsewhere.

@NamedQueries(value = {
        @NamedQuery(name="query_get_all_courses", query="SELECT c FROM Course c"),
        @NamedQuery(name="query_get_100_Step_courses", query="SELECT  c  FROM Course c WHERE name LIKE '%100 Steps'")
})
@Cacheable //Because we know it won't change and so can be cacheable.

public class Course {

    @Id
    @GeneratedValue
    private Long id;

    //@Column(name="exampleName"), nullable=false - Specify table columns and that it can't be null.
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "course") //defined Review as owner side
    private List<Review> reviews = new ArrayList<>();
    /*
    By default, on the one too many side of the relationship, fetch strategy is LAZY.
     */

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore //used with spring-boot-starter-rest in pom
    private List<Student> students = new ArrayList<>();


    //Hibernate Annotations

    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    @CreationTimestamp
    private LocalDateTime createdDate;


    //No Argument Constructor
    protected Course(){}

    public Course(String name){
        this.name = name;
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    //We don't want people to be able to set a review, so we change it to add instead and also add a remove method too.

    public void addReviews(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addStudents(Student students) {
        this.students.add(students);
    }

    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                '}';
    }
}
