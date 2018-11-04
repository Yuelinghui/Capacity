package com.capacity.ui.core;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.capacity.ui.R;
import com.capacity.widget.ProgressDialog;
import com.capacity.widget.TitleBar;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by yuelinghui on 17/8/31.
 */

public abstract class BaseActivity extends FragmentActivity {

    protected static final int FRAGMENT_LAYOUT_DEFAULT = R.layout.base_activity_layout;
    protected static final int FRAGMENT_CONTAINER_DEFAULT = R.id.fragment_container;
    private TitleBar mTitleBar;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mTitleBar = (TitleBar) findViewById(R.id.title_bar);
        // android 6.0及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 有权限没有授予
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
            return;
        }

        initData();
        initView();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length == 0 || grantResults.length == 0) {
            return;
        }

        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {
            initData();
            initView();
        }

    }

    public void setTitle(String title) {
        if (mTitleBar != null) {
            mTitleBar.setTitle(title);
        }
    }

    public void setLeftHide(boolean hide) {
        if (mTitleBar != null) {
            mTitleBar.hideLeft(hide);
        }
    }

    public void setTitleRightTxt(String text) {
        if (mTitleBar != null) {
            mTitleBar.setRightTxt(text);
        }
    }

    public void setTitleLeftClickListener(TitleBar.OnLeftClickListener listener) {
        if (mTitleBar != null) {
            mTitleBar.setOnLeftClickListener(listener);
        }
    }

    public void setTitleRightClickListener(TitleBar.OnRightClickListener listener) {
        if (mTitleBar != null) {
            mTitleBar.setOnRightClickListener(listener);
        }
    }

    public void showLoadingView() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setCanceledOnTouchOutside(false);
//        }
//        mProgressDialog.show();
    }

    public void dismissLoadingView() {
//        if (!isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.dismiss();
//        }
    }

    /**
     * 切换fragment
     *
     * @param fragment
     */
    public void startFirstFragment(Fragment fragment) {
        startFirstFragment(fragment, FRAGMENT_CONTAINER_DEFAULT);
    }

    public void startFirstFragment(Fragment fragment, int containerId) {
        if (isFinishing()) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.replace(containerId, fragment);
        ft.commitAllowingStateLoss();
    }

    /**
     * 启动做滑动动画fragment
     *
     * @param fragment
     * @author wyqiuchunlong
     */
    public void startFragment(Fragment fragment) {
        startFragment(fragment, FRAGMENT_CONTAINER_DEFAULT);
    }

    public void startFragment(Fragment fragment, int containerId) {
        if (isFinishing()) {
            return;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,
                R.anim.push_left_in, R.anim.push_right_out);
        ft.replace(containerId, fragment, fragment.getClass()
                .getName());
        ft.addToBackStack(fragment.getClass().getName());
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        List<Fragment> list = getSupportFragmentManager().getFragments();
        Fragment fragment = null;
        if (list != null && list.size() > 0) {
            if (list.size() > count) {
                // 处理startFragment和startFirstFragment混合模式
                for (int i = list.size() - 1; i >= 0; i--) {
                    if (list.get(i) != null) {
                        fragment = list.get(i);
                        if (!fragment.isAdded() || !fragment.isVisible()) {
                            continue;
                        }
                        break;
                    }
                }
            } else {
                // 处理startFragment模式
                if (count > 0 && list.get(count - 1) != null
                        && list.get(count - 1) instanceof BaseFragment) {
                    fragment = list.get(count - 1);
                }
            }
        }
        if (fragment == null || !fragment.isAdded() || !fragment.isVisible()) {
            super.onBackPressed();
            return;
        }
        if (fragment instanceof BaseFragment && !((BaseFragment) fragment).onBackPressed()) {
            if (count <= 0) {
                super.onBackPressed();
                return;
            }
            getSupportFragmentManager().popBackStack();
            return;
        }
    }

    protected int getLayoutId() {
        return FRAGMENT_LAYOUT_DEFAULT;
    }

    protected abstract void initData();

    protected abstract void initView();

}
