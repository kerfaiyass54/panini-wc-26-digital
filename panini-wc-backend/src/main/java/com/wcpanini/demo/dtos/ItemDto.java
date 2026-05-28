package com.wcpanini.demo.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {

    private String code;

    private String name;

    private LocalDateTime createdAt;
}