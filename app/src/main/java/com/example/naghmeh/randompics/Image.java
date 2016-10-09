package com.example.naghmeh.randompics;

/**
 * Created by naghmeh on 10/8/16.
 */
public class Image
{
    private int id;
    private String url;

    public Image()
    {

    }

    public Image(int id, String url)
    {
        this.id = id;
        this.url = url;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }
}
