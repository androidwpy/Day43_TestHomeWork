package com.example.administrator.day43_testhomework.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by softpo on 2016/10/29.
 */

public class CircleImageView extends ImageView {

    private Bitmap mBitmap;
    private int mWidth,mHeight;
    private BitmapShader mBitmapShader;
    private ShapeDrawable mShapeDrawable;
    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //得到图像
        mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        mWidth = mBitmap.getWidth();
        mHeight = mBitmap.getHeight();
        //构造渲染器BitmapShader
        /*
         CLAMP  ：如果渲染器超出原始边界范围，会复制范围内边缘染色。
         REPEAT ：横向和纵向的重复渲染器图片，平铺。
         MIRROR ：横向和纵向的重复渲染器图片，这个和REPEAT 重复方式不一样，它是以镜像方式平铺。
         */
        Bitmap squareScaleBitmap = centerSquareScaleBitmap(mBitmap, 100);

        mBitmapShader = new BitmapShader(squareScaleBitmap, Shader.TileMode.MIRROR,Shader.TileMode.MIRROR);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

//        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
//        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //精确赋值
        /**
         * EXACTLY：当我们设置width或height为match_parent或者固定值时，容器在布局时调用子 view的measure方法传入的模式是EXACTLY，
         * 因为子view会占据剩余容器的空间，所以它大小是确定的；
         *
         * AT_MOST:而当设置为 wrap_content时，容器传进去的是AT_MOST,
         * 表示子view的大小最多是多少，这样子view会根据这个上限来设置自己的尺寸；
         *
         * UNSPECIFIED：是未指定尺寸，这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式
         */
//        if(modeWidth==MeasureSpec.EXACTLY&&modeHeight==MeasureSpec.EXACTLY){
////            mWidth = MeasureSpec.getSize(widthMeasureSpec);
////            mHeight = MeasureSpec.getSize(heightMeasureSpec);
//            Toast.makeText(getContext(),"binggo",Toast.LENGTH_LONG).show();
//        }
        setMeasuredDimension(400 ,400);
// else{
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
//        setMeasuredDimension(400,400);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //将图片裁剪为椭圆形
        //构建ShapeDrawable对象并定义形状为椭圆
        mShapeDrawable = new ShapeDrawable(new OvalShape());
        //得到画笔并设置渲染器
        mShapeDrawable.getPaint().setShader(mBitmapShader);
        //设置显示区域
        mShapeDrawable.setBounds(20, 20,150,150);
        //绘制shapeDrawable
        mShapeDrawable.draw(canvas);
    }
    /**
     * 将给定图片维持宽高比缩放后，截取正中间的正方形部分。
     *
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }
        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();
        if (widthOrg >= edgeLength && heightOrg >= edgeLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;
            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }
            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;
            try {
                result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        }
        return result;
    }
}
