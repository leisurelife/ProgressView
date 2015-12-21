package com.leisure.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by json on 2015/12/17.
 */
public class MyProgress extends View {
    private int width=0;
    private int height=0;
    private Bitmap mBlueBitmap;
    private Bitmap mWhiteBitmap;
    private float progressP=0;
    private static final BitmapFactory.Options options = new BitmapFactory.Options();
    private int res_bg;
    private int res_progress;
    private int orientation;
    private int max=100;
    static
    {
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }
    public MyProgress(Context context) {
        this(context, null);
    }

    public MyProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.MyProgress,defStyleAttr,0);
        //背景图片
        res_bg= typedArray.getResourceId(R.styleable.MyProgress_bg_drawable,0);
        //进度图片
        res_progress=typedArray.getResourceId(R.styleable.MyProgress_progress_drawable,0);
        //方向
        orientation=typedArray.getInteger(R.styleable.MyProgress_orientation, 0);
        //最大进度
        max=typedArray.getInteger(R.styleable.MyProgress_max_progress,100);
        //进度值并转化为百分比
        progressP=typedArray.getInteger(R.styleable.MyProgress_progress,100)*1.0f/max;
        typedArray.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mWhiteBitmap, 0, 0, null);
         ImageCrop(canvas, mBlueBitmap, progressP);
    }

    private void init(){
        //进度图的bitmap
        if(res_progress!=0){
            mBlueBitmap= BitmapFactory.decodeResource(getResources(),res_progress);
        }else{
            mBlueBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.blue);
        }
        //背景图的bitmap
        if(res_bg!=0){
            mWhiteBitmap= BitmapFactory.decodeResource(getResources(),res_bg);
        }else {
            mWhiteBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.white);
        }
        //图片宽
        width=mBlueBitmap.getWidth();
        //图片高
        height=mBlueBitmap.getHeight();
    }

    public void setProgress(int progress){
        this.progressP=progress*1.0f/max;
        invalidate();
    }


    public  void ImageCrop(Canvas canvas,Bitmap bitmap,float progress) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int x=0;
        int y=0;
        if(orientation==0){
            w=(int) (w*progress);
        }
        if(orientation==1){
            h=(int)(h*progress);
        }
        if(orientation==2){
            x=w-(int)(w*progress);
            w=(int)(w*progress);
        }
        if(orientation==3){
            y=h-(int)(h*progress);
            h=(int)(h*progress);
        }
        //下面这句是关键
        Bitmap tempBitmap=Bitmap.createBitmap(bitmap, x, y, w, h, null, false);
        canvas.drawBitmap(tempBitmap,x,y,null);
    }
}
