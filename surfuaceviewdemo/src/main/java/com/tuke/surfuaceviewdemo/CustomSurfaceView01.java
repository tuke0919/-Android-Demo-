package com.tuke.surfuaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

/**
 * <p/>
 * 功能 : 验证SurafceView的缓存原理
 * <p/>
 * <p>
 * <p>Copyright baidu.com 2018 All right reserved</p>
 *
 * @author tuke 时间 2018/10/19
 * @email  tuke@baidu.com
 * <p>
 * 最后修改人 无
 */
public class CustomSurfaceView01 extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "CustomSurfaceView01";

    private SurfaceHolder mSurfaceHolder;

    private Canvas mCanvas;
    private boolean mIsDrawing;
    private Paint mPaint;
    private Path mPath;

    private int distance;
    private int i;
    private int j;

    public CustomSurfaceView01(Context context) {
        super(context);
        initViews();
    }


    public CustomSurfaceView01(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public CustomSurfaceView01(Context context, AttributeSet attrs, int defStyleAttr) {
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



    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    /**
     * 每次锁住画布 画一个数字
     */
    public void draw() {
        Log.e(TAG, "draw: " );
        Paint paint = new Paint();
        paint.setTextSize(30);

        paint.setColor(Color.RED);

        Canvas canvas = mSurfaceHolder.lockCanvas();

        canvas.drawText(String.valueOf(i), getWidth() / 2, distance, paint);
        i++;
        distance = distance + 40;
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }


    /**
     * 锁住屏幕 填充白色
     */
    public void fillWhiteColor() {
        Log.e(TAG, "fillWhiteColor: " );
        Canvas canvas =mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.WHITE);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    /**
     * 锁住屏幕 填充灰色
     */
    public void fillGrayColor() {
        Log.e(TAG, "fillGrayColor: ");
        Canvas canvas =mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.GRAY);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

    /**
     * 锁住屏幕 填充蓝色
     */
    public void fillBlueColor() {
        Log.e(TAG, "fillBlueColor: ");
        Canvas canvas =mSurfaceHolder.lockCanvas();
        canvas.drawColor(Color.BLUE);
        mSurfaceHolder.unlockCanvasAndPost(canvas);
    }

}
