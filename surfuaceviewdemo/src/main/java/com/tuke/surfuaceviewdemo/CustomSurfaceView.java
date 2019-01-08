package com.tuke.surfuaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * <p/>
 * 功能 :  SurfaceView使用方式，实例：实现屏幕手写字
 * <p/>
 * <p>
 * <p>Copyright baidu.com 2018 All right reserved</p>
 *
 * @author tuke 时间 2018/10/18
 * @email tuke@baidu.com
 * <p>
 * 最后修改人 无
 */
public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback ,Runnable{

    private static final String TAG = "CustomSurfaceView";

    private SurfaceHolder mSurfaceHolder;

    private Canvas mCanvas;
    private boolean mIsDrawing;
    private Paint mPaint;
    private Path mPath;



    public CustomSurfaceView(Context context) {
        super(context);
        initViews();
    }

    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    public void  initViews(){
        //2. 获取Holder，设置回调
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(50);
        mPath = new Path();


        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // 3, 在surfaceCreated 开启绘图线程
        mIsDrawing = true;
        new Thread(this).start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
       mIsDrawing = false;
    }


    @Override
    public void run() {

        // 4. 线程死循环绘图
        while (mIsDrawing){

            long start = System.currentTimeMillis();

            // 绘图
            draw();

            long end = System.currentTimeMillis();


            Log.e(TAG, "draw: end - start = " + (end - start));

            if(end - start < 100){
                try {
                    Thread.sleep(100 - end + start);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    private void draw(){
        try {
            // 5. Holder锁住屏幕，绘图，解锁屏幕，每一锁屏绘制都是全量绘制
            mCanvas = mSurfaceHolder.lockCanvas();

            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath,mPaint);


        }catch (Exception e){
              e.printStackTrace();
        }finally {

            if(mCanvas != null){
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG , "ACTION_DOWN : " + " [ x = " + x + ", y = " + y + " ]" );

                mPath.moveTo(x,y);
                break;

            case MotionEvent.ACTION_MOVE:

                Log.d(TAG , "ACTION_MOVE : " + " [ x = " + x + ", y = " + y + " ]" );

                mPath.lineTo(x,y);

                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG , "ACTION_UP : " + " [ x = " + x + ", y = " + y + " ]" );

                break;

        }

        return true;
    }
}
