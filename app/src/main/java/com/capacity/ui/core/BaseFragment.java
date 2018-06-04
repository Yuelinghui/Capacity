package com.capacity.ui.core;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by yuelinghui on 17/8/31.
 */

public abstract class BaseFragment extends Fragment {

    protected BaseActivity mActivity = null;

    private View mContainerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (BaseActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContainerView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mContainerView);
        initData();
        initView();
        return mContainerView;
    }

    @Override
    public void startActivity(Intent intent) {
        mActivity.startActivity(intent);
    }

    protected void showLoadingView() {
        mActivity.showLoadingView();
    }

    protected void dismissLoadingView() {
        mActivity.dismissLoadingView();
    }

    protected boolean onBackPressed() {
        return false;
    }

    protected abstract int getLayoutId();

    protected abstract void initData();

    protected abstract void initView();
}
