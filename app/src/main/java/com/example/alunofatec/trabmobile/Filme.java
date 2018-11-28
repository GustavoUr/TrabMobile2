package com.example.alunofatec.trabmobile;

public class Filme {

     private String id;
     private String title;
     private String posterpath;
     private String overview;

    public Filme() {
    }

    public Filme(String id, String title, String posterpath, String overview) {
        this.id = id;
        this.title = title;
        this.posterpath = posterpath;
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
