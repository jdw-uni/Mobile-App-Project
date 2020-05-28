package com.example.osrscomrade.news;

public class ParseItem {

    private String imgUrl;
    private String title;
    private String detailUrl;
    private String date;

    public ParseItem() {
    }

    public ParseItem(String imgUrl, String title, String detailUrl, String date) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.detailUrl = detailUrl;
        this.date = date;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailUrl() { return detailUrl; }

    public void setDetailUrl(String detailUrl) { this.detailUrl = detailUrl; }

    public String getDate() { return date; }

    public void setDate(String title) {
        this.date = date;
    }


}
