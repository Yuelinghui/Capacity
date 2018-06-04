package com.capacity.ui.main.ui.sort;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.capacity.ui.R;
import com.capacity.ui.core.BaseFragment;
import com.capacity.ui.main.entity.AppInfo;
import com.capacity.ui.main.entity.AppItemData;
import com.capacity.ui.main.model.MainModel;
import com.capacity.ui.main.ui.detail.DetailFragment;
import com.capacity.widget.CustomToast;
import com.capacity.widget.TitleBar;
import com.qdaily.frame.core.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class AppSortFragment extends BaseFragment {

    private static final String TYPE = "FRAGMENT_TYPE";

    private static final String KEY_ID = "KEY_ID";

    private static final String KEY_SORT = "Type";
    private static final String KEY_SCENE = "SCENE";

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.grid_app)
    GridView mGridView;

    private List<AppItemData> mAppItemList;
    private List<AppInfo> mAppInfoList;

    private String mId;
    private String mType;
    private MainModel mMainModel;

    public static AppSortFragment sortInstance(String id) {
        Bundle args = new Bundle();
        args.putString(KEY_ID,id);
        args.putString(TYPE,KEY_SORT);
        AppSortFragment fragment = new AppSortFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static AppSortFragment sceneInstance(String id) {

        Bundle args = new Bundle();
        args.putString(KEY_ID,id);
        args.putString(TYPE,KEY_SCENE);
        AppSortFragment fragment = new AppSortFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.app_sort_fragment_layout;
    }

    @Override
    protected void initData() {
        mId = getArguments().getString(KEY_ID);
        mType = getArguments().getString(TYPE);

        if (mMainModel == null) {
            mMainModel = new MainModel(mActivity);
        }
        mMainModel.getAppSortList(mType, mId, new RxSubscriber<List<AppInfo>>() {
            @Override
            protected void onNetStart() {
                mActivity.showLoadingView();
            }

            @Override
            protected void onSuccess(List<AppInfo> appInfos) {
                if (appInfos == null || appInfos.size() == 0) {
                    onFail("没有应用");
                    return;
                }
                mAppInfoList = appInfos;
                mAppItemList = convert(appInfos);
                mGridView.setAdapter(new SortAdapter(mActivity,mAppItemList));
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

    private List<AppItemData> convert(List<AppInfo> appInfos) {
        List<AppItemData> result = new ArrayList<>();
        if (appInfos == null || appInfos.size() == 0) {
            return result;
        }
        for (AppInfo appInfo : appInfos) {
            if (appInfo != null) {
                AppItemData itemData = new AppItemData();
                itemData.appIcon = appInfo.getIcon();
                itemData.appName = appInfo.getName();
                result.add(itemData);
            }
        }
        return result;
    }

    @Override
    protected void initView() {
        mTitleBar.setOnLeftClickListener(new TitleBar.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                mActivity.onBackPressed();
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mActivity.startFragment(DetailFragment.newInstance(mAppInfoList.get(position)));
            }
        });
    }
}
