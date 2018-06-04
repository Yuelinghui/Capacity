package com.capacity.ui.main.ui.planing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.capacity.manager.ImageManager;
import com.capacity.ui.R;
import com.capacity.ui.main.entity.AppInfo;
import com.capacity.ui.main.entity.AppItemData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yuelinghui on 17/9/4.
 */

public class PlanningImageView extends FrameLayout {

    @Bind(R.id.img_planning)
    ImageView mImageView;

    private Context mContext;

    private ViewGroup mDragView;

    private OnDismissListener mListener;

    private OnClickListener mClickListner;

    private AppItemData mAppInfo;

    public PlanningImageView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        mDragView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.my_planning_image_layout, this, false);
        ButterKnife.bind(this, mDragView);
        addView(mDragView);
        mDragView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext).setMessage(mContext.getString(R.string.planning_delete_message)).setPositiveButton(mContext.getString(R.string.planning_delete_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mListener != null) {
                            dialog.dismiss();

                            mListener.onDismiss(PlanningImageView.this, mAppInfo == null ? "" : mAppInfo.appIcon);
                        }
                    }
                }).setNegativeButton(mContext.getString(R.string.planning_delete_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                return true;
            }
        });

        mDragView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListner != null) {
                    mClickListner.onClick(PlanningImageView.this);
                }
            }
        });
    }

    public void setData(AppItemData appInfo, int type) {
        if (appInfo == null) {
            return;
        }
        mAppInfo = appInfo;
        String thumb = mAppInfo.appInfo.getThumb(type);
        ImageManager.displayImage(mContext, thumb, mImageView);

    }

    public AppInfo getAppInfo() {
        return mAppInfo == null ? null : mAppInfo.appInfo;
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        mClickListner = l;
    }

    public void setOnDismissListener(OnDismissListener listener) {
        mListener = listener;
    }

    public interface OnDismissListener {
        void onDismiss(PlanningImageView view, String url);
    }

}
