package com.edutech.payments.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 15, scale = 3)
    private BigDecimal amount;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "payment_date", nullable = false)
    private Instant paymentDate;

    @Size(max = 100)
    @NotNull
    @Column(name = "payment_method", nullable = false, length = 100)
    private String paymentMethod;

    @Size(max = 200)
    @NotNull
    @Column(name = "payment_institution", nullable = false, length = 200)
    private String paymentInstitution;

    @Size(max = 200)
    @NotNull
    @Column(name = "transaction_id", nullable = false, length = 200)
    private String transactionId;

    @Size(max = 50)
    @NotNull
    @Column(name = "status", nullable = false, length = 50)
    private String status;

    // Constructors
    public Payment() {}

    public Payment(Integer userId, BigDecimal amount, Instant paymentDate, 
                   String paymentMethod, String paymentInstitution, 
                   String transactionId, String status) {
        this.userId = userId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.paymentInstitution = paymentInstitution;
        this.transactionId = transactionId;
        this.status = status;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentInstitution() {
        return paymentInstitution;
    }

    public void setPaymentInstitution(String paymentInstitution) {
        this.paymentInstitution = paymentInstitution;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
