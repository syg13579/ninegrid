package com.example.rock.ninegrid.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.example.ninegridlib.widget.GridImageView;
import com.example.ninegridlib.widget.NineGridLayout;
import com.example.rock.ninegrid.R;
import com.example.rock.ninegrid.module.ImagesModule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rock on 16/8/5.
 */
public class ImageAdapter extends BaseAdapter implements NineGridLayout.GridImageCallback {
    private List<ImagesModule> imageUrls = new ArrayList<>();
    private Context mContext;

    public ImageAdapter(Context context){
        mContext = context;
    }

    public void setImageUrls(List<ImagesModule> imageUrls){
        this.imageUrls.clear();
        this.imageUrls.addAll(imageUrls);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_nine_grid, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.e("adapter","position========:"+position);
        holder.nineGridLayout.setUrlList(imageUrls.get(position).getUrlList(),this);
        return convertView;
    }

    @Override
    public void displayImage(ImageView imageView, String url) {
//        Glide.with(this).load(Uri.parse(r1)).signature(new StringSignature("33d33")).thumbnail(0.3f).placeholder(R.mipmap.ic_launcher).into(imageView1);
//        Log.e("url","url:"+url);
//        Glide.w
        Glide.with((Activity)mContext).load(url).into(imageView);
    }

    @Override
    public void onClickImage(GridImageView imageView, int position, String url) {
        Toast.makeText(mContext,"position:"+position,Toast.LENGTH_SHORT).show();
    }

    public class ViewHolder{
        NineGridLayout nineGridLayout;
        public ViewHolder(View view){
            nineGridLayout = (NineGridLayout) view.findViewById(R.id.layout_nine_grid);
        }
    }
}
