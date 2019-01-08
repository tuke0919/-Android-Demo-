package com.tuke.demo.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * <p>
 * 功能： 右下角带icon的圆形图标
 * </p>
 * <p>Copyright corp.netease.com 2018 All right reserved </p>
 *
 * @author tuke 时间 2019/1/7
 * @email tuke@corp.netease.com
 * <p>
 * 最后修改人：无
 */
public class CircleImageViewWithIcon extends RelativeLayout {

    public static final String ANDROID_NAMESPACE = "http://schemas.android.com/apk/res/android";
    private Context context;
    private SimpleDraweeView simpleDraweeView;
    private ImageView smallImage;

    private Drawable largeImageSrc;
    private Drawable smallImageSrc;
    private int defaultWidth = 86;


    public CircleImageViewWithIcon (Context context) {
        this(context,null);
    }

    public CircleImageViewWithIcon (Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleImageViewWithIcon (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
        initViews(attrs);
    }
    public void init(Context context , AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageViewWithIcon);
        largeImageSrc = typedArray.getDrawable(R.styleable.CircleImageViewWithIcon_largeImageSrc);
        smallImageSrc = typedArray.getDrawable(R.styleable.CircleImageViewWithIcon_smallImageSrc);
        typedArray.recycle();
    }

    /**
     * 初始化
     */
    public void initViews(AttributeSet attrs) {
        // 背景
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.timeline_portrait_bg));

        // 大图
        int circleImageWidth = defaultWidth;
        String circleImageWidthStr = attrs.getAttributeValue(ANDROID_NAMESPACE, "layout_width");
        if (circleImageWidthStr.contains("dip")){
            circleImageWidth = (int) Float.parseFloat((circleImageWidthStr.substring(0, circleImageWidthStr.indexOf("dip"))));
        }
        simpleDraweeView = new SimpleDraweeView(context);
        if (circleImageWidth < 10){
            circleImageWidth = defaultWidth;
        }
        int width =  dip2px(context, circleImageWidth - 10);
        int height = width;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width,height);
        params.bottomMargin = dip2px(context, 5);
        params.rightMargin =  params.bottomMargin;
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        simpleDraweeView.setLayoutParams(params);

        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setRoundAsCircle(true);

        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                .setRoundingParams(roundingParams)
                .setPlaceholderImage(R.drawable.login_default_icon)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        simpleDraweeView.setHierarchy(hierarchy);

        addView(simpleDraweeView);

        // 小图
        smallImage  = new ImageView(context);
        int smallWidth = (int) (width / 3.3);
        int smallHeight = smallWidth;
        Log.e("image", "smallWidth = " + smallWidth + " smallHeight = " + smallHeight);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(smallWidth, smallHeight);
        params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        params1.bottomMargin =  dip2px(context, 5);
        params1.rightMargin =  params1.bottomMargin;
        smallImage.setLayoutParams(params1);
        smallImage.setScaleType(ImageView.ScaleType.FIT_XY);
        if (smallImageSrc != null) {
            smallImage.setImageDrawable(smallImageSrc);
        }
        addView(smallImage);
    }

    /**
     * 设置圆形图片资源
     * @param url
     */
    public void setLargeImageSrc(String url) {
        if (!TextUtils.isEmpty(url)) {
            Uri uri = Uri.parse(url);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .build();
            simpleDraweeView.setController(controller);
        } else {
            simpleDraweeView.setImageURI(Uri.parse("res://" + R.drawable.login_default_icon));
        }
    }

    /**
     * 设置icon图标
     * @param resId
     */
    public void setSmallImageSrc(int resId) {
        if (smallImage != null) {
            if (resId != 0) {
                smallImage.setImageResource(resId);
            } else {
                smallImage.setVisibility(GONE);
            }
        }
    }
    /**
     * dp 转 px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 正方形
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }
}
