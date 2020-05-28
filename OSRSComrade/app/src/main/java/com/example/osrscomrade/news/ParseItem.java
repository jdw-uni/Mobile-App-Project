package com.example.osrscomrade.news;

public class ParseItem {

    private String imgUrl;
    private String title;
    private String detailUrl;
    private String date;
    private String info;

    public ParseItem(String imgUrl, String title, String detailUrl, String date, String info) {
        this.imgUrl = imgUrl;
        this.title = title;
        this.detailUrl = detailUrl;
        this.date = date;
        this.info = info;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailUrl() { return detailUrl; }

    public String getDate() { return date; }

    public String getInfo() { return info; }


}
