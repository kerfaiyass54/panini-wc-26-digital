// StatisticsResponse.java
package com.wcpanini.demo.dtos;

public class StatisticsResponse {

    private long totalOwnedStickers;
    private long totalFinishedCountries;
    private long totalLogos;
    private long totalPlayers;

    public StatisticsResponse() {
    }

    public StatisticsResponse(
            long totalOwnedStickers,
            long totalFinishedCountries,
            long totalLogos,
            long totalPlayers
    ) {
        this.totalOwnedStickers = totalOwnedStickers;
        this.totalFinishedCountries = totalFinishedCountries;
        this.totalLogos = totalLogos;
        this.totalPlayers = totalPlayers;
    }

    public long getTotalOwnedStickers() {
        return totalOwnedStickers;
    }

    public void setTotalOwnedStickers(long totalOwnedStickers) {
        this.totalOwnedStickers = totalOwnedStickers;
    }

    public long getTotalFinishedCountries() {
        return totalFinishedCountries;
    }

    public void setTotalFinishedCountries(long totalFinishedCountries) {
        this.totalFinishedCountries = totalFinishedCountries;
    }

    public long getTotalLogos() {
        return totalLogos;
    }

    public void setTotalLogos(long totalLogos) {
        this.totalLogos = totalLogos;
    }

    public long getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(long totalPlayers) {
        this.totalPlayers = totalPlayers;
    }
}