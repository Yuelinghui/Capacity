package com.capacity.ui.main.ui.cockpit;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.capacity.ui.R;
import com.capacity.ui.core.BaseFragment;
import com.capacity.ui.main.ui.MainActivity;
import com.capacity.widget.CustomToast;
import com.capacity.widget.TitleBar;

import butterknife.Bind;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class CockpitLayoutFragment extends BaseFragment implements View.OnClickListener{

    @Bind(R.id.img_layout_1)
    ImageView mLayout1;
    @Bind(R.id.img_layout_2)
    ImageView mLayout2;
    @Bind(R.id.img_layout_3)
    ImageView mLayout3;
    @Bind(R.id.img_layout_4)
    ImageView mLayout4;
    @Bind(R.id.img_layout_5)
    ImageView mLayout5;


    @Bind(R.id.txt_name_1)
    TextView mName1;
    @Bind(R.id.txt_name_2)
    TextView mName2;
    @Bind(R.id.txt_name_3)
    TextView mName3;
    @Bind(R.id.txt_name_4)
    TextView mName4;
    @Bind(R.id.txt_name_5)
    TextView mName5;

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;

    private View mSelectedView;
    private int mLayoutType = 0;
    private String mLayoutName;

    public static CockpitLayoutFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CockpitLayoutFragment fragment = new CockpitLayoutFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.cockpit_layout_fragment_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTitleBar.setRightTxt(mActivity.getString(R.string.detail_choose));
        mTitleBar.setOnLeftClickListener(new TitleBar.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                mActivity.onBackPressed();
            }
        });
        mTitleBar.setOnRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {
                if (mLayoutType == 0) {
                    CustomToast.makeText("没有选定布局，请先选定布局").show();
                    return;
                }
                ((MainActivity)mActivity).toMyPlanning(mLayoutType,mLayoutName);
            }
        });
        mLayout1.setOnClickListener(this);
        mLayout2.setOnClickListener(this);
        mLayout3.setOnClickListener(this);
        mLayout4.setOnClickListener(this);
        mLayout5.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_layout_1:
                mLayoutType = 1;
                mLayoutName = mName1.getText().toString();
                break;
            case R.id.img_layout_2:
                mLayoutType = 2;
                mLayoutName = mName2.getText().toString();
                break;
            case R.id.img_layout_3:
                mLayoutType = 3;
                mLayoutName = mName3.getText().toString();
                break;
            case R.id.img_layout_4:
                mLayoutType = 4;
                mLayoutName = mName4.getText().toString();
                break;
            case R.id.img_layout_5:
                mLayoutType = 5;
                mLayoutName = mName5.getText().toString();
        }
        changeSelectedView(v);
    }

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
}
