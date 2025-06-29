package com.edutech.courses.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "description", nullable = false, length = 800)
    private String description;

    @JoinColumn(name = "category_id", nullable = false)
    private Integer categoryId;

    @JoinColumn(name = "manager_id", nullable = false)
    private Integer managerId;

    @JoinColumn(name = "instructor_id", nullable = false)
    private Integer instructorId;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "price", nullable = false, precision = 15, scale = 3)
    private BigDecimal price;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    // Constructors
    public Course() {}

    public Course(Integer id, String title, String description, Integer categoryId, 
                  Integer managerId, Integer instructorId, LocalDate publishDate, 
                  BigDecimal price, String image, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.managerId = managerId;
        this.instructorId = instructorId;
        this.publishDate = publishDate;
        this.price = price;
        this.image = image;
        this.status = status;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}