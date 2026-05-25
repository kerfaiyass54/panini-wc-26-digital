package com.wcpanini.demo.entities;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(
        name = "stickers",
        indexes = {
                @Index(name = "idx_stickers_name", columnList = "name"),
                @Index(name = "idx_stickers_type", columnList = "type")
        }
)
public class Sticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 100)
    private String type;

    @Column(length = 100)
    private String nationality;

    @Column(length = 150)
    private String place;


    protected Sticker() {
        // JPA requires a no-args constructor
    }

    public Sticker(String name, String type, String nationality, String place) {
        this.name = name;
        this.type = type;
        this.nationality = nationality;
        this.place = place;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sticker sticker)) return false;
        return Objects.equals(id, sticker.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Sticker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", nationality='" + nationality + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}