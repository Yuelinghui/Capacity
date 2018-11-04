package com.capacity.ui.main.ui.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.capacity.manager.ImageManager;
import com.capacity.ui.R;
import com.capacity.ui.core.BaseFragment;
import com.capacity.ui.main.entity.AppInfo;
import com.capacity.ui.main.model.MainModel;
import com.capacity.widget.CustomToast;
import com.capacity.widget.TitleBar;
import com.qdaily.frame.core.RxSubscriber;
import com.qdaily.frame.util.DataUtil;
import com.qdaily.frame.util.LocalDisplay;

import butterknife.Bind;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class DetailFragment extends BaseFragment {

    private static final String KEY_APP_INFO = "KEY_APP_INFO";

    @Bind(R.id.img_logo)
    ImageView mLogoImg;
    @Bind(R.id.txt_name)
    TextView mNameTxt;
    @Bind(R.id.txt_author)
    TextView mAuthorTxt;
    @Bind(R.id.txt_category)
    TextView mCategoryTxt;
    @Bind(R.id.txt_scene)
    TextView mSceneTxt;
    @Bind(R.id.txt_version)
    TextView mVersionTxt;
    @Bind(R.id.txt_time)
    TextView mUpdateTimeTxt;
    @Bind(R.id.txt_request)
    TextView mRequestTxt;
    @Bind(R.id.layout_thumb)
    ViewGroup mThumbLayout;
    @Bind(R.id.txt_detail)
    TextView mDetailTxt;
    @Bind(R.id.title_bar)
    TitleBar mTitleBar;


    private AppInfo mAppInfo;

    private MainModel mMainModel;

    private boolean isSelected;

    private ImageDetailDialog mImageDialog;

    public static DetailFragment newInstance(AppInfo appInfo) {

        Bundle args = new Bundle();
        args.putSerializable(KEY_APP_INFO, appInfo);
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.detail_fragment_layout;
    }

    @Override
    protected void initData() {
        mAppInfo = (AppInfo) getArguments().getSerializable(KEY_APP_INFO);
        if (mAppInfo == null) {
            CustomToast.makeText("数据错误").show();
            mActivity.onBackPressed();
            return;
        }

        mMainModel = new MainModel(mActivity);
    }

    @Override
    protected void initView() {

        mTitleBar.setOnLeftClickListener(new TitleBar.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                mActivity.getSupportFragmentManager().popBackStack();
            }
        });

        mMainModel.isAppSelect(mAppInfo.getId(), new RxSubscriber<Boolean>() {
            @Override
            protected void onNetStart() {

            }

            @Override
            protected void onSuccess(Boolean result) {
                if (result == null) {
                    onFail("");
                    return;
                }
                isSelected = result;
                if (result) {
                    mTitleBar.setRightTxt(mActivity.getString(R.string.detail_delete));
                } else {
                    mTitleBar.setRightTxt(mActivity.getString(R.string.detail_choose));
                }
            }

            @Override
            protected void onFail(String msg) {

            }

            @Override
            protected void onFinish() {

            }
        });

        mTitleBar.setOnRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {
                if (!isSelected) {
                    mMainModel.selectApp(mAppInfo.getId(), new RxSubscriber<Void>() {
                        @Override
                        protected void onNetStart() {
                            mActivity.showLoadingView();
                        }

                        @Override
                        protected void onSuccess(Void aVoid) {
                            isSelected = true;
                            CustomToast.makeText(mActivity.getString(R.string.detail_selected_app)).show();
                            mTitleBar.setRightTxt(mActivity.getString(R.string.detail_delete));
                        }

                        @Override
                        protected void onFail(String msg) {
                            CustomToast.makeText(msg).show();
                        }

                        @Override
                        protected void onFinish() {
                            mActivity.dismissLoadingView();
                        }
                    });
                } else {
                    mMainModel.unSelectApp(mAppInfo.getId(), new RxSubscriber<Void>() {
                        @Override
                        protected void onNetStart() {
                            mActivity.showLoadingView();
                        }

                        @Override
                        protected void onSuccess(Void aVoid) {
                            isSelected = false;
                            CustomToast.makeText(mActivity.getString(R.string.detail_unselected_app)).show();
                            mTitleBar.setRightTxt(mActivity.getString(R.string.detail_choose));
                        }

                        @Override
                        protected void onFail(String msg) {
                            CustomToast.makeText(msg).show();
                        }

                        @Override
                        protected void onFinish() {
                            mActivity.dismissLoadingView();
                        }
                    });
                }

            }
        });


        ImageManager.displayImage(mActivity, mAppInfo.getIcon(), mLogoImg);
        mNameTxt.setText(mAppInfo.getName());
        mAuthorTxt.setText(mAppInfo.getUserId());
        mCategoryTxt.setText(mAppInfo.getCategory());
        if (mAppInfo.getAppTask() != null && mAppInfo.getAppTask().size() != 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mAppInfo.getAppTask().size(); i++) {
                if (i == mAppInfo.getAppTask().size() - 1) {
                    sb.append(mAppInfo.getAppTask().get(i));
                } else {
                    sb.append(mAppInfo.getAppTask().get(i) + "、");
                }
            }
            mSceneTxt.setText(sb);

        }
        mVersionTxt.setText(mAppInfo.getVersion());
        mUpdateTimeTxt.setText(DataUtil.formatTime(mAppInfo.getUpdateTime()));
        mRequestTxt.setText("windows");

        if (mAppInfo.getSnapshotHrefs() != null && mAppInfo.getSnapshotHrefs().size() > 0) {

            for (String url : mAppInfo.getSnapshotHrefs()) {
                ImageView imageView = new ImageView(mActivity);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LocalDisplay.dp2px(256), LocalDisplay.dp2px(192));
                params.leftMargin = LocalDisplay.dp2px(10);
                params.rightMargin = LocalDisplay.dp2px(10);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

                ImageManager.displayImage(mActivity, url, imageView);
                imageView.setTag(url);
                imageView.setOnClickListener(mThumbClickListener);

                mThumbLayout.addView(imageView);
            }
        }

        mDetailTxt.setText(mAppInfo.getDescription());

    }

    private View.OnClickListener mThumbClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = (String) v.getTag();
            if (TextUtils.isEmpty(url)) {
                return;
            }
           new ImageDetailDialog(mActivity,url).show();
        }
    };
}
