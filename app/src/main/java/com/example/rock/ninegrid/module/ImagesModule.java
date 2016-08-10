package com.example.rock.ninegrid.module;


import java.util.ArrayList;
import java.util.List;

public class ImagesModule {
    private List<String> urlList ;

    public ImagesModule(){

    }

    public ImagesModule(List<String> urlList){
        this.urlList = urlList;
    }

    public List<String> getUrlList() {
        if(urlList == null)
            return new ArrayList<>();
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

}
