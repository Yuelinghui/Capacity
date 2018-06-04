package com.capacity.ui.main.ui.detail;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.widget.ImageView;

import com.capacity.manager.ImageManager;
import com.capacity.ui.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yuelinghui on 17/10/30.
 */

public class ImageDetailDialog extends Dialog {

    @Bind(R.id.img_detail)
    ImageView mImageView;

    private Context mContext;

    private String mUrl;

    public ImageDetailDialog(@NonNull Context context,String url) {
        this(context, R.style.ProgressDialog,url);
    }

    public ImageDetailDialog(@NonNull Context context, @StyleRes int themeResId,String url) {
        super(context, themeResId);
        mContext = context;
        mUrl = url;
        initDialog();
    }

    private void initDialog() {

        setContentView(R.layout.image_detail_dialog);

        ButterKnife.bind(this);

        ImageManager.displayImage(mContext,mUrl,mImageView);


    }

}
