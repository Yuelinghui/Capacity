package com.capacity.ui.main.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.capacity.manager.UserInfoManager;
import com.capacity.ui.R;
import com.capacity.ui.core.BaseFragment;
import com.capacity.ui.main.entity.AppInfo;
import com.capacity.ui.main.entity.AppItemData;
import com.capacity.ui.main.model.MainModel;
import com.capacity.ui.main.ui.detail.DetailFragment;
import com.capacity.widget.CustomToast;
import com.qdaily.frame.core.RxSubscriber;
import com.qdaily.frame.managercenter.MManagerCenter;
import com.qdaily.frame.util.DataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class HomeFragment extends BaseFragment {

    @Bind(R.id.txt_name)
    TextView mNameTxt;
    @Bind(R.id.txt_login_time)
    TextView mLoginTimeTxt;
    @Bind(R.id.txt_clear_app)
    TextView mClearAppTxt;
    @Bind(R.id.grid_app)
    GridView mAppGrid;

    private List<AppItemData> mAppItemList;

    private UserInfoManager mManager;

    private MainModel mMainModel;

    private SelectedAppGridAdapter mAdapter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected void initData() {
        mAppItemList = new ArrayList<>();
        mManager = MManagerCenter.getManager(UserInfoManager.class);
    }

    @Override
    protected void initView() {

        mNameTxt.setText(mManager.getUserName());
        mLoginTimeTxt.setText(DataUtil.formatTime(mManager.getLoginTime()));

        if (mManager.getUserApp() != null) {
            for (AppInfo appInfo : mManager.getUserApp()) {
                if (appInfo != null) {
                    AppItemData itemData = new AppItemData();
                    itemData.appName = appInfo.getName();
                    itemData.appIcon = appInfo.getIcon();
                    itemData.appId = appInfo.getId();
                    mAppItemList.add(itemData);
                }
            }
            mAdapter = new SelectedAppGridAdapter(mActivity, mAppItemList);
            mAppGrid.setAdapter(mAdapter);
        }

        mAppGrid.setOnItemClickListener(mItemClickListener);

        mClearAppTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mAppItemList == null || mAppItemList.size() == 0) {
                    CustomToast.makeText(R.string.home_clear_app_error).show();
                    return;
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0;i< mAppItemList.size();i++) {
                    sb.append(mAppItemList.get(i).appId);
                    if (i != mAppItemList.size() -1) {
                        sb.append(",");
                    }
                }
                if (mMainModel == null) {
                    mMainModel = new MainModel(mActivity);
                }
                mMainModel.unSelectApp(sb.toString(), new RxSubscriber<Void>() {
                    @Override
                    protected void onNetStart() {
                        showLoadingView();
                    }

                    @Override
                    protected void onSuccess(Void aVoid) {
                        mAppItemList.clear();
                        if (mAdapter != null) {
                            mAdapter.notifyDataSetChanged();
                        }
                        mManager.clearUserApp();
                    }

                    @Override
                    protected void onFail(String msg) {
                        CustomToast.makeText(msg).show();
                    }

                    @Override
                    protected void onFinish() {
                        dismissLoadingView();
                    }
                });
            }
        });
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mActivity.startFragment(DetailFragment.newInstance(mManager.getUserApp().get(position)));
        }
    };


}
