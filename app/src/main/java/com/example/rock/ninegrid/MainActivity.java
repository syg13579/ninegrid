package com.example.rock.ninegrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.rock.ninegrid.adapter.ImageAdapter;
import com.example.rock.ninegrid.module.ImagesModule;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView image_list;
    private ImageView image;
    private ImageAdapter imageAdapter;

    private String[] mUrls = new String[]{"http://ww1.sinaimg.cn/mw690/006wXAvGgw1f6ma9plhsgj30hs0buwhh.jpg",
            "http://ww3.sinaimg.cn/mw690/68147f68jw1f6n7tih0i0j207f059t8o.jpg",
            "http://ww2.sinaimg.cn/mw690/bf1b7d04gw1f6n7f5cstuj20cs07oaa9.jpg",
            "http://ww1.sinaimg.cn/mw690/bca65a60jw1f6n85s99aqj20p00dwabv.jpg",
            "http://ww4.sinaimg.cn/mw690/005K3J4ijw1f6n3igkkvdj30ow0kpn4f.jpg",
            "http://ww3.sinaimg.cn/mw690/a8d43f7egw1f6moixc3ydj20zk0k0wh3.jpg",
            "http://ww2.sinaimg.cn/mw690/bf1b7d04gw1f6mnx2drs4j20ku0bqgml.jpg",
            "http://ww3.sinaimg.cn/mw690/bf1b7d04gw1f6lkle1hqjj20dw0900tf.jpg",
            "http://ww4.sinaimg.cn/mw690/68147f68jw1f6m2xmri44j208a04p0sw.jpg",
            "http://ww3.sinaimg.cn/mw690/621f4ed4jw1f6mdodubqfj20b407yt9y.jpg",
            "http://ww1.sinaimg.cn/mw690/a8d43f7egw1f6lk2z4q1uj20iy08sjso.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1717647885,4193212272&fm=21&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=2024625579,507531332&fm=21&gp=0.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = (ImageView) findViewById(R.id.image);
        Glide.with(this).load("https://www.baidu.com/img/bd_logo1.png").override(400,400).placeholder(R.mipmap.ic_launcher).into(image);
        image_list = (ListView) findViewById(R.id.image_list);
        imageAdapter = new ImageAdapter(this);
        image_list.setAdapter(imageAdapter);
        imageAdapter.setImageUrls(getDemo());
    }

    private List<ImagesModule> getDemo(){
        List<ImagesModule> list = new ArrayList<>();
        for(int i = 0;i<20;i++){
            ImagesModule imagesModule = new ImagesModule();
            List<String> urls = new ArrayList<>();
            int random = getRandom(0,9);
            for(int j=0;j<random;j++){
                urls.add(mUrls[j]);
            }
            imagesModule.setUrlList(urls);
            list.add(imagesModule);
        }
        return list;
    }

    public int getRandom(int startNum, int endNum) {
        int num = (int) (Math.round(Math.random() * (endNum - startNum) + startNum));
        return num;
    }
}
