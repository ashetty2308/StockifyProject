package com.example.stockify;

public class StockData {
    String image;
    String headline;

    public StockData(String image, String headline) {
        this.image = image;
        this.headline = headline;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }
}
