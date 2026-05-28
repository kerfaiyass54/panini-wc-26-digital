package com.wcpanini.demo.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "sticker_id")
    private Sticker sticker;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}