package com.capacity.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capacity.manager.ImageManager;
import com.capacity.ui.R;
import com.capacity.ui.main.entity.AppItemData;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DragView extends LinearLayout {

    @Bind(R.id.img_app_icon)
    ImageView mIconImg;
    @Bind(R.id.txt_app_name)
    TextView mNameTxt;

    private Context mContext;

    private int lastX;
    private int lastY;
    private int moveX;
    private int moveY;
    private int dx;
    private int dy;
    private int mWidth;
    private int mHeight;

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public void show(AppItemData data) {
        if (data == null) {
            return;
        }

        setVisibility(View.VISIBLE);
        mNameTxt.setText(data.appName);
        ImageManager.displayImage(mContext, data.appIcon, mIconImg);
    }

    private void initView() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.home_app_item_layout, this, true);

        ButterKnife.bind(this, view);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();

                moveX = lastX;
                moveY = lastY;
                break;

            case MotionEvent.ACTION_MOVE:
                dx = (int) event.getRawX() - lastX;
                dy = (int) event.getRawY() - lastY;

                int left = getLeft() + dx;
                int top = getTop() + dy;
                int right = getRight() + dx;
                int bottom = getBottom() + dy;

                if (left < 0) {
                    left = 0;
                    right = left + mWidth;
                }

                if (right > getScreenWidth()) {
                    right = getScreenWidth();
                    left = right - mWidth;
                }

                if (top < 0) {
                    top = 0;
                    bottom = top + mHeight;
                }

                if (bottom > getScreenHeight()) {
                    bottom = getScreenHeight();
                    top = bottom - mHeight;
                }

                layout(left,top,right,bottom);

                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();


        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private int getScreenWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private int getScreenHeight() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
}
