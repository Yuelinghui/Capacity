package com.capacity.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capacity.ui.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class TitleBar extends LinearLayout {

    @Bind(R.id.nav_left_img)
    ImageView mLeftImg;
    @Bind(R.id.nav_title)
    TextView mTitleTxt;
    @Bind(R.id.nav_right_text)
    TextView mRightTxt;

    private Activity mContext;
    private OnLeftClickListener mLeftClckListener;
    private OnRightClickListener mRightClickListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = (Activity) context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.title_bar_layout, this, true);
        ButterKnife.bind(this, view);

        mLeftImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLeftClckListener == null) {
                    mContext.finish();
                } else {
                    mLeftClckListener.onLeftClick();
                }
            }
        });

        mRightTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRightClickListener != null) {
                    mRightClickListener.onRightClick();
                }
            }
        });
    }

    public void hideLeft(boolean hide) {
        mLeftImg.setVisibility(hide ? GONE : VISIBLE);
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTxt.setText(title);
        }
    }

    public void setRightTxt(String txt) {
        if (TextUtils.isEmpty(txt)) {
            return;
        }
        mRightTxt.setVisibility(VISIBLE);
        mRightTxt.setText(txt);
    }

    public void setOnLeftClickListener(OnLeftClickListener listener) {
        this.mLeftClckListener = listener;
    }

    public void setOnRightClickListener(OnRightClickListener listener) {
        this.mRightClickListener = listener;
    }


    public interface OnLeftClickListener {
        void onLeftClick();
    }

    public interface OnRightClickListener {
        void onRightClick();
    }
}
