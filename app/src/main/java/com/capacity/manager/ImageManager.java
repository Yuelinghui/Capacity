package com.capacity.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.qdaily.frame.managercenter.MManager;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class ImageManager extends MManager {

    public static void displayImage(Context context, String url, ImageView imageView) {
        ImageManager.displayImage(context, url, imageView, -1, null);
    }

    public static void displayImage(Context context, String url, ImageView imageView, int placeHolder) {
        ImageManager.displayImage(context, url, imageView, placeHolder, null);
    }

    public static void displayImage(Context context, String url, ImageView imageView, int placeHolder, RequestListener<String, GlideDrawable> listener) {
        if (context == null || imageView == null || TextUtils.isEmpty(url))
            return;

        Activity activity = null;

        if (context instanceof Activity) {
            activity = (Activity) context;
        }

        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed())
            return;

        DrawableRequestBuilder<String> request = Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (placeHolder != -1) {
            request.placeholder(placeHolder);
        }
        if (listener != null)
            request.listener(listener);
        if (url.contains(".gif")) {
            request.diskCacheStrategy(DiskCacheStrategy.SOURCE);
        }

        request.into(imageView);
    }

    public static void loadImage(Context context,String url) {
        if (TextUtils.isEmpty(url))
            return;

        Glide.with(context)
                .load(url)
                .downloadOnly(SimpleTarget.SIZE_ORIGINAL, SimpleTarget.SIZE_ORIGINAL);

    }
}
