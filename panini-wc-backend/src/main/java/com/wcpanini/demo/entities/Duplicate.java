package com.wcpanini.demo.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "duplicates",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_duplicates_email_code_number",
                        columnNames = {"email", "code", "number"}
                )
        },
        indexes = {
                @Index(name = "idx_duplicates_email", columnList = "email"),
                @Index(name = "idx_duplicates_code", columnList = "code"),
                @Index(name = "idx_duplicates_number", columnList = "number")
        }
)
public class Duplicate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 100)
    private String code;

    @Column(nullable = false)
    private Integer number;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    public Duplicate() {
    }

    public Duplicate(String email, String code, Integer number) {
        this.email = email;
        this.code = code;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Duplicate duplicate)) return false;
        return Objects.equals(id, duplicate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}