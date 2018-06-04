package com.capacity.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.capacity.ui.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class MenuText extends FrameLayout {

    @Bind(R.id.txt_title)
    TextView mTextView;

    private Context mContext;

    private String mMenuId;
    public MenuText(Context context) {
        this(context, null);
    }

    public MenuText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_menu_txt_layout,this,true);
        ButterKnife.bind(this,view);
    }

    public void setText(String text,String id) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        mTextView.setText(text);
        mMenuId = id;
    }

    public String getMenuId() {
        return mMenuId;
    }
}
