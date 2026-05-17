package com.wcpanini.demo.dtos;

public class StatsDTO {

    private String name;

    private Integer total;

    public StatsDTO() {
    }

    public StatsDTO(
            String name,
            Integer total
    ) {
        this.name = name;
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}