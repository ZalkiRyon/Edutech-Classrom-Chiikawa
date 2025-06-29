package com.edutech.support.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Table(name = "support_ticket")
public class SupportTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "support_user_id")
    private Integer supportUserId;

    @Size(max = 200)
    @NotNull
    @Column(name = "subject", nullable = false, length = 200)
    private String subject;

    @Size(max = 800)
    @NotNull
    @Column(name = "description", nullable = false, length = 800)
    private String description;

    @Size(max = 20)
    @NotNull
    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "closed_at")
    private Instant closedAt;

    // Constructors
    public SupportTicket() {}

    public SupportTicket(Integer userId, Integer supportUserId, String subject, 
                        String description, String status, Instant createdAt, Instant closedAt) {
        this.userId = userId;
        this.supportUserId = supportUserId;
        this.subject = subject;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.closedAt = closedAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSupportUserId() {
        return supportUserId;
    }

    public void setSupportUserId(Integer supportUserId) {
        this.supportUserId = supportUserId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Instant closedAt) {
        this.closedAt = closedAt;
    }
}
