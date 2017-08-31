package com.example.clock;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;


/**
 * 自定义View,继承水平滚动条
 */
class LeftSlideView extends HorizontalScrollView {

    private TextView mTextView_Delete;//删除按钮
    private TextView mTextView_Set;//设置按钮

    private int mScrollWidth;//记录滚动条可以滚动的距离

    private IonSlidingButtonListener mIonSlidingButtonListener;//自定义的接口，用于传达滑动事件等

    private Boolean isOpen = false;//记录按钮菜单是否打开，默认关闭false

    private Boolean once = false;//在onMeasure中只执行一次的判断


    //构造方法
    public LeftSlideView(Context context) {
        super(context, null);
    }

    public LeftSlideView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public LeftSlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }


    //在onMeasure中先取得作为“设置”、“删除”按钮的TextView
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!once) {
            mTextView_Delete = (TextView) findViewById(com.example.clock.R.id.tv_delete);
            mTextView_Set = (TextView) findViewById(com.example.clock.R.id.tv_set);
            once = true;
        }
    }


    //在onLayout中使Item在每次变更布局大小时回到初始位置，并且获取滚动条的可移动距离
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(0, 0);
            //获取水平滚动条可以滑动的范围，即右侧按钮的宽度
            mScrollWidth = mTextView_Delete.getWidth() + mTextView_Set.getWidth();
            Log.i("asd", "mScrollWidth:" + mScrollWidth);
        }
    }


    //滑动监听，传递按下、移动这些事件，并按滑动的距离大小控制菜单开关
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mIonSlidingButtonListener.onDownOrMove(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollx();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        mTextView_Set.setTranslationX(1);
    }


    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    public void changeScrollx() {
        if (getScrollX() >= (mScrollWidth / 2)) {
            this.smoothScrollTo(mScrollWidth, 0);
            isOpen = true;
            mIonSlidingButtonListener.onMenuIsOpen(this);
        } else {
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) {
            return;
        }
        this.smoothScrollTo(mScrollWidth, 0);
        isOpen = true;
        mIonSlidingButtonListener.onMenuIsOpen(this);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (!isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }


    //接口定义及注册方法
    public void setSlidingButtonListener(IonSlidingButtonListener listener) {
        mIonSlidingButtonListener = listener;
    }

    public interface IonSlidingButtonListener {
        void onMenuIsOpen(View view);
        void onDownOrMove(LeftSlideView leftSlideView);
    }

}

