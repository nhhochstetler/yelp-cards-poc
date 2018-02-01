package com.whattoeat.natehochstetler.whattoeat.Model;

public class Coordinates {
    private double latitude;
    private double longitutde;

    public Coordinates(double latitude, double longitutde) {
        this.latitude = latitude;
        this.longitutde = longitutde;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitutde() {
        return longitutde;
    }

    public void setLongitutde(double longitutde) {
        this.longitutde = longitutde;
    }
}
