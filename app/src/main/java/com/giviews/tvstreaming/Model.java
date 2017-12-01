package com.giviews.tvstreaming;

/**
 * Created by asus on 01/12/2017.
 */

public class Model {
    private String url;
    private String title;
    private String image;

    public Model() {

    }

    public Model(String url, String title, String image) {
        this.title = title;
        this.image = image;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
