package com.tuke.demo.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

/**
 * <p>
 * 功能：悬浮按钮菜单
 * </p>
 * <p>
 * 最后修改人：无
 */
public class FloatingActionsMenu extends ViewGroup {

    private static final int ANIMATION_DURATION = 300;

    private static Interpolator sExpandInterpolator = new OvershootInterpolator();
    private static Interpolator sCollapseInterpolator = new DecelerateInterpolator(3f);
    private static Interpolator sAlphaExpandInterpolator = new DecelerateInterpolator();

    private AnimatorSet mExpandAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);
    private AnimatorSet mCollapseAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);
    // 三个按钮
    private FloatingActionButton fabButtonOne;
    private FloatingActionButton fabButtonTwo;
    private FloatingActionButton fabButtonThree;

    private Context mContext;
    private boolean mExpanded;
    private int mHeaderHeight = 0;
    // 状态监听器
    private OnMenuStatusListener listener;

    public FloatingActionsMenu (Context context) {
        this(context, null);
    }

    public FloatingActionsMenu (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingActionsMenu (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public void init(Context context, AttributeSet attrs, int defStyleAttr){
        mContext = context;
        mHeaderHeight = dip2px(context , 10);
        initViews(context);
        initListeners();
    }

    /**
     * 初始化子布局
     * @param context
     */
    public void initViews(Context context){
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(dip2px(context, 61), dip2px(context, 61));
        LayoutParams lp1 = new LayoutParams(lp);

        fabButtonThree = new FloatingActionButton(context);
        fabButtonThree.setLayoutParams(lp1);
        fabButtonThree.setGravity(Gravity.CENTER);
        fabButtonThree.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cb_post_bg));
        fabButtonThree.setText("想法");
        fabButtonThree.setTextColor(Color.parseColor("#FFFFFFFF"));
        fabButtonThree.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        addView(fabButtonThree,0);

        LayoutParams lp2 = new LayoutParams(lp);
        fabButtonTwo = new FloatingActionButton(context);
        fabButtonTwo.setLayoutParams(lp2);
        fabButtonTwo.setGravity(Gravity.CENTER);
        fabButtonTwo.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cb_post_bg));
        fabButtonTwo.setText("提问");
        fabButtonTwo.setTextColor(Color.parseColor("#FFFFFFFF"));
        fabButtonTwo.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        addView(fabButtonTwo,1);

        fabButtonOne = new FloatingActionButton(context);
        fabButtonOne.setLayoutParams(lp1);
        fabButtonOne.setGravity(Gravity.CENTER);
        fabButtonOne.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.cb_post_bg));
        fabButtonOne.setText("发布");
        fabButtonOne.setTextColor(Color.parseColor("#FFFFFFFF"));
        fabButtonOne.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        addView(fabButtonOne,2);

    }

    /**
     * 监听器
     */
    public void initListeners() {
        fabButtonThree.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View v) {
                if (listener != null && fabButtonThree.getAlpha() == 1f) {
                    listener.onMenuActionThreeClicked();
                }
            }
        });
        fabButtonTwo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View v) {
                if (listener != null && fabButtonTwo.getAlpha() == 1f) {
                    listener.onMenuActionTwoClicked();
                }
            }
        });
        fabButtonOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick (View v) {
                toggle();
            }
        });
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
        // 测量子布局
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
        int childCounts = getChildCount();
        for (int i = 0 ; i < childCounts ; i ++) {
            View child = getChildAt(i);
            height += child.getMeasuredHeight();
            if (width == 0){
                width = child.getMeasuredWidth();
            }
        }
        setMeasuredDimension(width,height + mHeaderHeight);
    }

    @Override
    protected void onLayout (boolean changed, int l, int t, int r, int b) {
        int left = l;
        int top = t;
        int right = r;
        int bottom = b;
        int childCounts = getChildCount();
        // 定位第一个按钮
        View buttonOne = getChildAt(childCounts - 1);
        fabButtonOne = (FloatingActionButton) buttonOne;
        int buttonOneTop = b - t - buttonOne.getMeasuredHeight();
        fabButtonOne.layout(0
                , buttonOneTop
                , buttonOne.getMeasuredWidth()
                , buttonOneTop + buttonOne.getMeasuredHeight());

        // 定位剩下的按钮
        for (int index = childCounts - 1 ; index >= 0 ;index --) {
            View child = getChildAt(index);

            if (child == null){
                return;
            }
            if (child == buttonOne || child.getVisibility() == GONE) {
                continue;
            }

            int childLeft = 0;
            int childTop = buttonOneTop - child.getMeasuredHeight() * (childCounts - 1 - index);
            child.layout(childLeft, childTop, childLeft + child.getMeasuredWidth(), childTop + child.getMeasuredHeight());

            float collapsedTranslation = buttonOneTop - childTop;
            float expandedTranslation = 0f;
            // 计算 动画值
            child.setTranslationY(mExpanded ? expandedTranslation : collapsedTranslation);
            child.setAlpha(mExpanded ? 1f : 0f);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            params.mCollapseDir.setFloatValues(expandedTranslation, collapsedTranslation);
            params.mExpandDir.setFloatValues(collapsedTranslation, expandedTranslation);
            params.setAnimationsTarget(child);
        }
    }


    /**
     * 折叠，扩展
     */
    public void toggle() {
        if (mExpanded) {
            collapse();
        } else {
            expand();
        }
    }

    /**
     * 扩展
     */
    public void expand() {
        if (!mExpanded) {
            mExpanded = true;
            mCollapseAnimation.cancel();
            mExpandAnimation.start();
            if (listener != null) {
                listener.onMenuExpanded();
            }
        }
    }

    /**
     * 折叠
     */
    public void collapse() {
        if (mExpanded) {
            mExpanded = false;
            mCollapseAnimation.setDuration(ANIMATION_DURATION);
            mCollapseAnimation.start();
            mExpandAnimation.cancel();
            if (listener != null) {
                listener.onMenuCollapsed();
            }
        }
    }


    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(super.generateDefaultLayoutParams());
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(super.generateLayoutParams(attrs));
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(super.generateLayoutParams(p));
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p);
    }


    /**
     * 设置 菜单状态 监听器
     * @param listener
     */
    public void setListener (OnMenuStatusListener listener) {
        this.listener = listener;
    }

    /**
     * 子视图布局参数
     */
    private class LayoutParams extends ViewGroup.LayoutParams {

        private ObjectAnimator mExpandDir = new ObjectAnimator();
        private ObjectAnimator mExpandAlpha = new ObjectAnimator();
        private ObjectAnimator mCollapseDir = new ObjectAnimator();
        private ObjectAnimator mCollapseAlpha = new ObjectAnimator();
        private boolean animationsSetToPlay;

        public LayoutParams (ViewGroup.LayoutParams source) {
            super(source);

            mExpandDir.setInterpolator(sExpandInterpolator);
            mExpandAlpha.setInterpolator(sAlphaExpandInterpolator);
            mCollapseDir.setInterpolator(sCollapseInterpolator);
            mCollapseAlpha.setInterpolator(sCollapseInterpolator);

            mCollapseAlpha.setProperty(View.ALPHA);
            mCollapseAlpha.setFloatValues(1f, 0f);

            mExpandAlpha.setProperty(View.ALPHA);
            mExpandAlpha.setFloatValues(0f, 1f);

            mCollapseDir.setProperty(View.TRANSLATION_Y);
            mExpandDir.setProperty(View.TRANSLATION_Y);
        }

        public void setAnimationsTarget (View view) {
            mCollapseAlpha.setTarget(view);
            mCollapseDir.setTarget(view);
            mExpandAlpha.setTarget(view);
            mExpandDir.setTarget(view);

            // Now that the animations have targets, set them to be played
            if (!animationsSetToPlay) {
                addLayerTypeListener(mExpandDir, view);
                addLayerTypeListener(mCollapseDir, view);

                mCollapseAnimation.play(mCollapseAlpha);
                mCollapseAnimation.play(mCollapseDir);
                mExpandAnimation.play(mExpandAlpha);
                mExpandAnimation.play(mExpandDir);
                animationsSetToPlay = true;
            }
        }

        private void addLayerTypeListener (Animator animator, final View view) {
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd (Animator animation) {
                    view.setLayerType(LAYER_TYPE_NONE, null);
                }

                @Override
                public void onAnimationStart (Animator animation) {
                    view.setLayerType(LAYER_TYPE_HARDWARE, null);
                }
            });
        }
    }


    /**
     * 悬浮按钮
     */
    public static class FloatingActionButton extends TextView {

        public FloatingActionButton (Context context) {
            super(context);
        }

        public FloatingActionButton (Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public FloatingActionButton (Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * 菜单状态监听器
     */
    public abstract static class OnMenuStatusListener {
        /**
         * 菜单 展开
         */
        public void onMenuExpanded(){}

        /**
         * 菜单 折叠
         */
        public void onMenuCollapsed(){}

        /**
         * 第二个按钮点击
         */
        public abstract void onMenuActionTwoClicked();

        /**
         * 第三个按钮点击
         */
        public abstract void onMenuActionThreeClicked();
    }
}
