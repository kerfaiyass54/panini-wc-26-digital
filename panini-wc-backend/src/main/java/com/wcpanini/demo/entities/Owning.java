package com.wcpanini.demo.entities;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(
        name = "owning",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_owning_email_code",
                        columnNames = {"email", "code"}
                )
        },
        indexes = {
                @Index(name = "idx_owning_email", columnList = "email"),
                @Index(name = "idx_owning_code", columnList = "code")
        }
)
public class Owning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 100)
    private String code;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = OffsetDateTime.now();
    }

    public Owning() {
    }

    public Owning(String email, String code) {
        this.email = email;
        this.code = code;
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

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Owning owning)) return false;
        return Objects.equals(id, owning.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}