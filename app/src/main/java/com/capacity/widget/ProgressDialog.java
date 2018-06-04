package com.capacity.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.capacity.ui.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class ProgressDialog extends Dialog {

    @Bind(R.id.img_loading)
    ImageView mImageView;

    private Animation mOperatingAnim;
    private Context mContext;


    public ProgressDialog(@NonNull Context context) {
        this(context, R.style.ProgressDialog);
    }

    public ProgressDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
        initDialog();
    }

    private void initDialog() {
        setContentView(R.layout.progress_dialog_layout);
        ButterKnife.bind(this);
        mOperatingAnim = AnimationUtils.loadAnimation(mContext,R.anim.rotate);
        mOperatingAnim.setInterpolator(new LinearInterpolator());
    }

    @Override
    public void show() {
        super.show();
        mImageView.clearAnimation();
        mImageView.startAnimation(mOperatingAnim);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mImageView.clearAnimation();
    }
}
