package com.wcpanini.demo.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "stickers")
public class Sticker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    private String nationality;

    private String place;

    public Sticker() {}

    public Sticker(String name, String type, String nationality, String place) {
        this.name        = name;
        this.type        = type;
        this.nationality = nationality;
        this.place       = place;
    }

    // ── Getters & Setters ──────────────────────────────────────

    public Long getId()                       { return id; }
    public String getName()                   { return name; }
    public void setName(String name)          { this.name = name; }
    public String getType()                   { return type; }
    public void setType(String type)          { this.type = type; }
    public String getNationality()            { return nationality; }
    public void setNationality(String nat)    { this.nationality = nat; }
    public String getPlace()                  { return place; }
    public void setPlace(String place)        { this.place = place; }
}