package com.tuke.demo.widgets;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
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
 * 功能：组合头像 最多4个
 * </p>
 * <p>Copyright corp.netease.com 2018 All right reserved </p>
 *
 * @author tuke 时间 2019/1/8
 * @email tuke@corp.netease.com
 * <p>
 * 最后修改人：无
 */
public class CombinationImageView extends RelativeLayout {

    private Context context;
    private SimpleDraweeView simpleDraweeView1;
    private SimpleDraweeView simpleDraweeView2;
    private SimpleDraweeView simpleDraweeView3;
    private SimpleDraweeView simpleDraweeView4;

    public CombinationImageView (Context context) {
        this(context, null);
    }

    public CombinationImageView (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CombinationImageView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initViews();
    }
    public void initViews() {
        // 宽和高
        int width = dip2px(context , 50);
        int height = width;
        // 左间隔
        int leftMargin = width / 2 + dip2px(context, 5);

        // 图1
        simpleDraweeView1 = new SimpleDraweeView(context);
        simpleDraweeView1.setId(R.id.CombinationImageView_1);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(width, height);
        simpleDraweeView1.setLayoutParams(params1);
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(getResources())
                .setRoundingParams(RoundingParams.asCircle())
                .setPlaceholderImage(R.drawable.login_default_icon)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        simpleDraweeView1.setHierarchy(hierarchy);
        addView(simpleDraweeView1);

        // 图2
        simpleDraweeView2 = new SimpleDraweeView(context);
        simpleDraweeView2.setId(R.id.CombinationImageView_2);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(width, height);
        params2.addRule(RelativeLayout.ALIGN_LEFT, simpleDraweeView1.getId());
        params2.leftMargin = leftMargin;
        simpleDraweeView2.setLayoutParams(params2);
        GenericDraweeHierarchy hierarchy2 = new GenericDraweeHierarchyBuilder(getResources())
                .setRoundingParams(RoundingParams.asCircle())
                .setPlaceholderImage(R.drawable.login_default_icon)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        simpleDraweeView2.setHierarchy(hierarchy2);
        addView(simpleDraweeView2);

        // 图3
        simpleDraweeView3 = new SimpleDraweeView(context);
        simpleDraweeView3.setId(R.id.CombinationImageView_3);
        RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(width, height);
        params3.addRule(RelativeLayout.ALIGN_LEFT, simpleDraweeView2.getId());
        params3.leftMargin = leftMargin;
        simpleDraweeView3.setLayoutParams(params3);
        GenericDraweeHierarchy hierarchy3 = new GenericDraweeHierarchyBuilder(getResources())
                .setRoundingParams(RoundingParams.asCircle())
                .setPlaceholderImage(R.drawable.login_default_icon)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        simpleDraweeView3.setHierarchy(hierarchy3);
        addView(simpleDraweeView3);

        // 图4
        simpleDraweeView4 = new SimpleDraweeView(context);
        simpleDraweeView4.setId(R.id.CombinationImageView_4);
        RelativeLayout.LayoutParams params4 = new RelativeLayout.LayoutParams(width, height);
        params4.addRule(RelativeLayout.ALIGN_LEFT, simpleDraweeView3.getId());
        params4.leftMargin = leftMargin;
        simpleDraweeView4.setLayoutParams(params4);
        GenericDraweeHierarchy hierarchy4 = new GenericDraweeHierarchyBuilder(getResources())
                .setRoundingParams(RoundingParams.asCircle())
                .setPlaceholderImage(R.drawable.login_default_icon)
                .setPlaceholderImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        simpleDraweeView4.setHierarchy(hierarchy4);
        addView(simpleDraweeView4);
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


    /**
     * 设置图片uri
     * @param urls
     */
    public void setImageUri(String[] urls) {
        if (urls == null || urls.length == 0) {
            return;
        }
        int maxCount = 4;
        int index;
        int length = urls.length > 4 ? maxCount : urls.length;
        for (index = 0; index < length; index ++) {
            if (!TextUtils.isEmpty(urls[index])) {
                SimpleDraweeView imageView = (SimpleDraweeView) getChildAt(index);
                Uri uri = Uri.parse(urls[index]);
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .build();
                imageView.setController(controller);
            }
        }
        for (; index < maxCount; index ++) {
            View child  = getChildAt(index);
            if (child != null) {
                child.setVisibility(GONE);
            }
        }
    }

    /**
     * 设置图片uri
     * @param urls
     */
    public void setImageUri(int[] urls) {
        if (urls == null || urls.length == 0) {
            return;
        }
        int maxCount = 4;
        int index;
        int length = urls.length > 4 ? maxCount : urls.length;
        for (index = 0; index < length; index ++) {
            if (urls[index] != 0) {
                SimpleDraweeView imageView = (SimpleDraweeView) getChildAt(index);
                Uri uri = Uri.parse("res://aaa/" + urls[index]);
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .build();
                imageView.setController(controller);
            }
        }
        for (; index < maxCount; index ++) {
            View child  = getChildAt(index);
            if (child != null) {
                child.setVisibility(GONE);
            }
        }
    }
}
