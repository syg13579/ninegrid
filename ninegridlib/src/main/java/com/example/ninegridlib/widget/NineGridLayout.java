package com.example.ninegridlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ninegridlib.R;

import java.util.ArrayList;
import java.util.List;


public class NineGridLayout extends ViewGroup {
    private static final float DEFAULT_SPACE = 10f;
    private static final int MAX_COUNT = 9;
    private static final int COLUMN_NUM = 3;

    private Context mContext;
    private float mSpace = DEFAULT_SPACE;
    private int mColumns;
    private int mRows;
    private int mTotalWidth;
    private int mSingleWidth;

    private int oneImageWidth; //第一张图片的宽度
    private int oneImageHeight; //第一张图片的高度

    private List<String> mUrlList = new ArrayList<>();

    private boolean mOneDefaultShow = false; //只有一张图片时，是否按照图片比例显示
    private GridImageCallback mCallback;



    public NineGridLayout(Context context) {
        super(context);
        init(context);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NineGridLayout);
        mSpace = typedArray.getDimension(R.styleable.NineGridLayout_space,DEFAULT_SPACE);
        mOneDefaultShow = typedArray.getBoolean(R.styleable.NineGridLayout_one_default_show,false);
        typedArray.recycle();
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = getListSize(mUrlList);
        getRowColumn(size);
        mTotalWidth = MeasureSpec.getSize(widthMeasureSpec);
        mSingleWidth = (int)((mTotalWidth-(COLUMN_NUM-1)*mSpace)/3);
        int height = (int)(mSingleWidth*mRows + mSpace*(mRows-1));
        setMeasuredDimension(mTotalWidth, height);
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        showImages();
    }

    private void init(Context context){
        mContext = context;
    }

    private int getListSize(List<String> list){
        if(list == null || list.size() == 0)
            return 0;
        return list.size();
    }

    private void showImages(){
        removeAllViews();
        int size = getListSize(mUrlList);
        setVisibility(size>0?VISIBLE:GONE);
        if(size == 0){
            return;
        }
        if(size ==1&&mOneDefaultShow&&oneImageHeight!=0&&oneImageWidth!=0){
            setOneImageLayout(createImage(0,mUrlList.get(0)),oneImageWidth,oneImageHeight,mUrlList.get(0));
        }else {
            for(int i=0;i<mUrlList.size();i++){
                setImageLayout(createImage(i,mUrlList.get(i)),mUrlList.get(i),i);
            }
        }
    }

    public void setUrlList(List<String> urlList ,@NonNull GridImageCallback callback){
        if(getListSize(urlList) ==0){
            setVisibility(GONE);
            return;
        }
        mCallback = callback;
        mUrlList.clear();

        if(urlList.size()>9){
            mUrlList.addAll(urlList.subList(0,9));
        }else {
            mUrlList.addAll(urlList);
        }
    }

    public void setOneImageUrl(String imageUrl , int imageWidth ,int imageHeight , GridImageCallback callback){
        if(imageUrl ==null || imageUrl.length() ==0 || imageWidth ==0 || imageHeight == 0){
            setVisibility(GONE);
            return;
        }
        oneImageWidth = imageWidth;
        oneImageHeight = imageHeight;
        mCallback = callback;
        mUrlList.clear();
        mUrlList.add(imageUrl);
        showImages();
    }

    private void setOneImageLayout(GridImageView imageView,float imageWidth ,float imageHeight ,String imageUrl){
        int showWidth;
        int showHeight;
        float maxWidth = mTotalWidth;
        float maxHeight = mContext.getResources().getDimension(R.dimen.img_max_height);
        float minWidth = mContext.getResources().getDimension(R.dimen.img_min_width);
        float minHeight = mContext.getResources().getDimension(R.dimen.img_min_height);
        if ((imageWidth / imageHeight) >= (maxWidth / maxHeight)) {
            showWidth = (int) maxWidth;
            showHeight = imageHeight * (maxWidth / imageWidth) < minHeight ? (int) minHeight : (int) (imageHeight * (maxWidth / imageWidth));
        } else {
            showWidth = imageWidth * (maxHeight / imageHeight) < minWidth ? (int) minWidth : (int) (imageWidth * (maxHeight / imageHeight));
            showHeight = (int) maxHeight;
        }
        imageView.setLayoutParams(new LayoutParams(showWidth,showHeight));
        imageView.layout(0,0,showWidth,showHeight);
        LayoutParams params = getLayoutParams();
        params.height = showHeight;
        setLayoutParams(params);
        addView(imageView);
        if(mCallback != null)
            mCallback.displayImage(imageView,imageUrl);
    }

    private void setImageLayout(GridImageView imageView,String imageUrl,int position){
        int[] coordinate = getImageCoordinate(position);
        int left = (int) ((mSingleWidth +mSpace)*coordinate[1]);
        int top = (int) ((mSingleWidth +mSpace)*coordinate[0]);
        imageView.layout(left,top,left+mSingleWidth,top+mSingleWidth);
        addView(imageView);
        if(mCallback != null)
            mCallback.displayImage(imageView,imageUrl);
    }

    private GridImageView createImage(final int position , final String url){
        final GridImageView imageView = new GridImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCallback!=null)
                    mCallback.onClickImage(imageView,position,url);
            }
        });
        return imageView;
    }

    private void getRowColumn(int imageSize){
        if(imageSize <4){
            mRows =1;
            mColumns = 3;
        }else if(imageSize == 4) {
            mRows = 2;
            mColumns = 2;
        }else {
            mRows = (imageSize+2)/3;
            mColumns = 3;
        }
    }

    private int[] getImageCoordinate(int position){
        int[] coordinate = new int[2];
        for(int i =0;i<mRows;i++){
            for(int j = 0;j<mColumns;j++){
                if((i*mColumns+j) == position ){
                    coordinate[0] = i;
                    coordinate[1] = j;
                    break;
                }
            }
        }
        return coordinate;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(mContext,attrs);
    }

    public interface GridImageCallback {
        void displayImage(ImageView imageView, String url);
        void onClickImage(GridImageView imageView, int position, String url);
    }
}
