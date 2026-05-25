// StickerSimpleResponse.java
package com.wcpanini.demo.dtos;

public class StickerSimpleResponse {

    private String name;
    private String nationality;
    private String place;

    public StickerSimpleResponse() {
    }

    public StickerSimpleResponse(
            String name,
            String nationality,
            String place
    ) {
        this.name = name;
        this.nationality = nationality;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPlace() {
        return place;
    }
}