package com.capacity.ui.main.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.capacity.ui.R;
import com.capacity.ui.core.BaseActivity;
import com.capacity.ui.login.LoginActivity;
import com.capacity.ui.main.entity.AppInfo;
import com.capacity.ui.main.entity.AppItemData;
import com.capacity.ui.main.entity.AppSortInfo;
import com.capacity.ui.main.model.MainModel;
import com.capacity.ui.main.ui.cockpit.CockpitLayoutFragment;
import com.capacity.ui.main.ui.home.HomeFragment;
import com.capacity.ui.main.ui.planing.MyPlanningFragment;
import com.capacity.ui.main.ui.sort.AppGridAdapter;
import com.capacity.ui.main.ui.sort.AppSortFragment;
import com.capacity.widget.MenuText;
import com.capacity.widget.TitleBar;
import com.qdaily.frame.core.RxSubscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.Bind;

public class MainActivity extends BaseActivity {

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.txt_home)
    TextView mHomeTxt;

    @Bind(R.id.layout_sort)
    ViewGroup mSortLayout;
    @Bind(R.id.img_sort)
    ImageView mSortImg;
    @Bind(R.id.list_sort)
    LinearLayout mSortList;

    @Bind(R.id.layout_scene)
    ViewGroup mSceneLayout;
    @Bind(R.id.img_scene)
    ImageView mSceneImg;
    @Bind(R.id.list_scene)
    LinearLayout mSceneList;

    @Bind(R.id.txt_layout)
    TextView mLayoutTxt;
    @Bind(R.id.txt_planning)
    TextView mPlanningTxt;
    @Bind(R.id.right_drawer)
    ListView mDrawerList;

    private View mSelectedView;

    private Stack<View> mSelectedViewStack;

    private boolean isFirstStart = true;

    private List<AppSortInfo> mSortSourceList;
    private List<AppSortInfo> mSceneSourceList;
    private int mPlanningType = 1;
    private String mPlanningName;

    private AppGridAdapter mAdapter;

    private List<AppInfo> mAppList;

    private MainModel mHomeModel;

    public static Intent newInstance(Context context) {

        Intent intent = new Intent();
        intent.setClass(context, MainActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_layout;
    }

    @Override
    public void initData() {
        mSelectedViewStack = new Stack<>();
        if (mHomeModel == null) {
            mHomeModel = new MainModel(this);
        }
        mHomeModel.getAppSort(new RxSubscriber<List<AppSortInfo>>() {
            @Override
            protected void onNetStart() {

            }

            @Override
            protected void onSuccess(List<AppSortInfo> appSortInfos) {
                if (appSortInfos == null || appSortInfos.size() == 0) {
                    return;
                }
                mSortSourceList = appSortInfos;
                initSort();
            }

            @Override
            protected void onFail(String msg) {

            }

            @Override
            protected void onFinish() {

            }
        });

        mHomeModel.getAppScene(new RxSubscriber<List<AppSortInfo>>() {
            @Override
            protected void onNetStart() {

            }

            @Override
            protected void onSuccess(List<AppSortInfo> appSortInfos) {
                if (appSortInfos == null || appSortInfos.size() == 0) {
                    return;
                }
                mSceneSourceList = appSortInfos;
                initScene();
            }

            @Override
            protected void onFail(String msg) {

            }

            @Override
            protected void onFinish() {

            }
        });
    }

    @Override
    public void initView() {

        mTitleBar.hideLeft(true);
        mTitleBar.setTitle(getString(R.string.app_name));
        mTitleBar.setRightTxt(getString(R.string.logout_title));
        mTitleBar.setOnRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {
                startActivity(LoginActivity.newInstance(MainActivity.this));
                finish();
            }
        });

        mHomeTxt.setOnClickListener(mHomeClick);
        mSceneLayout.setOnClickListener(mSceneTextClick);
        mLayoutTxt.setOnClickListener(mLayoutClick);
        mPlanningTxt.setOnClickListener(mPlaningClick);

        mHomeTxt.performClick();

        mHomeModel.getSelectedApp(new RxSubscriber<List<AppInfo>>() {
            @Override
            protected void onNetStart() {
            }

            @Override
            protected void onSuccess(List<AppInfo> appInfos) {
                mAppList = appInfos;
                mAdapter = new AppGridAdapter(MainActivity.this,conver(mAppList));
                mDrawerList.setAdapter(mAdapter);
            }

            @Override
            protected void onFail(String msg) {

            }

            @Override
            protected void onFinish() {

            }
        });

    }

    private List<AppItemData> conver(List<AppInfo> appInfos) {

        List<AppItemData> result = new ArrayList<>();
        for (AppInfo appinfo : appInfos) {
            AppItemData appItemData = new AppItemData();
            appItemData.appIcon = appinfo.getIcon();
            appItemData.appName = appinfo.getName();
            appItemData.appId = appinfo.getId();
            appItemData.appInfo = appinfo;
            appItemData.hasSelected = true;
            result.add(appItemData);
        }

        return result;
    }

    private void initScene() {
        if (mSceneSourceList == null || mSceneSourceList.size() == 0) {
            return;
        }
        for (AppSortInfo appSortInfo : mSceneSourceList) {
            if (appSortInfo != null) {
                MenuText menuText = new MenuText(this);
                menuText.setText(appSortInfo.getName(), appSortInfo.getId());
                menuText.setOnClickListener(mSceneTextClick);
                mSceneList.addView(menuText);
            }
        }
    }

    private void initSort() {
        if (mSortSourceList == null || mSortSourceList.size() == 0) {
            return;
        }
        for (AppSortInfo appSortInfo : mSortSourceList) {
            if (appSortInfo != null) {
                MenuText menuText = new MenuText(this);
                menuText.setText(appSortInfo.getName(), appSortInfo.getId());
                menuText.setOnClickListener(mSortTextClick);
                mSortList.addView(menuText);
            }
        }

    }

    @Override
    public void startFirstFragment(Fragment fragment) {
        super.startFirstFragment(fragment);
        stackAdd();
    }

    @Override
    public void startFragment(Fragment fragment) {
        super.startFragment(fragment);
        stackAdd();
    }

    public void toMyPlanning(int type, String name) {
        mPlanningType = type;
        mPlanningName = name;
        mPlanningTxt.performClick();
    }

    private View.OnClickListener mHomeClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSelectedView == v) {
                return;
            }
            changeSelectedView(v);
            if (isFirstStart) {
                startFirstFragment(HomeFragment.newInstance());
                isFirstStart = false;
            } else {
                startFragment(HomeFragment.newInstance());
            }
        }
    };

    private View.OnClickListener mSceneClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSelectedView == v) {
                return;
            }
            changeSelectedView(v);
            startFragment(AppSortFragment.sceneInstance(""));
        }
    };

    private View.OnClickListener mLayoutClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSelectedView == v) {
                return;
            }
            changeSelectedView(v);
            startFragment(CockpitLayoutFragment.newInstance());
        }
    };

    private View.OnClickListener mPlaningClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSelectedView == v) {
                return;
            }
            changeSelectedView(v);
            startFragment(MyPlanningFragment.newInstance(mPlanningType, mPlanningName == null ? getString(R.string.cockpit_ground_prepare) : mPlanningName));
        }
    };

    private View.OnClickListener mSortTextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSelectedView == v) {
                return;
            }
            changeSelectedView(v);
            if (v instanceof MenuText) {
                String id = ((MenuText) v).getMenuId();
                startFragment(AppSortFragment.sortInstance(id));
            }
        }
    };

    private View.OnClickListener mSceneTextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSelectedView == v) {
                return;
            }
            changeSelectedView(v);
            String id = "";
            if (v instanceof MenuText) {
                id = ((MenuText) v).getMenuId();
            }
            startFragment(AppSortFragment.sceneInstance(id));
        }
    };

    private void changeSelectedView(View view) {
        if (mSelectedView == null) {
            mSelectedView = view;
            mSelectedView.setSelected(true);
        } else {
            mSelectedView.setSelected(false);
            mSelectedView = view;
            mSelectedView.setSelected(true);
        }
    }

    private void stackAdd() {
        if (mSelectedView == null) {
            return;
        }
        mSelectedViewStack.add(mSelectedView);
    }

    private void stackPop() {
        if (!mSelectedViewStack.empty()) {
            mSelectedViewStack.pop();
            if (!mSelectedViewStack.empty()) {
                View view = mSelectedViewStack.peek();
                changeSelectedView(view);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stackPop();
    }
}
