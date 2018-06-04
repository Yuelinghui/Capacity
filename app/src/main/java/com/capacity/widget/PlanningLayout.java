package com.capacity.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.capacity.ui.R;
import com.capacity.ui.main.entity.AppInfo;
import com.capacity.ui.main.ui.planing.PlanningImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yuelinghui on 17/9/13.
 */

public class PlanningLayout extends LinearLayout {

    @Bind(R.id.layout_pager)
    ViewGroup mLayout;
    @Bind(R.id.view_pager)
    ViewPager mViewPager;

    private Context mContext;

    private List<PlanningImageView> mImageViewList;

    private int mSelectIndex;

    private OnClickListener mOnClickListener;

    public PlanningLayout(Context context) {
        this(context, null);
    }

    public PlanningLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlanningLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.planning_layout, this, true);
        ButterKnife.bind(this, view);

        mImageViewList = new ArrayList<>();
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnClickListener(null);

        mViewPager.setOnPageChangeListener(mPageChangeListener);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mSelectIndex = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mImageViewList == null ? 0 : mImageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (position == 0 && mImageViewList.size() == 0) {
                container.removeAllViews();
                return;
            }
            if (position > mImageViewList.size() - 1) {
                return;
            }
            container.removeView(mImageViewList.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mImageViewList.get(position);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onClick(PlanningLayout.this);
                    }
                }
            });
            container.addView(view);
            return view;
        }
    };

    public void addImageView(PlanningImageView view) {
        if (view == null) {
            return;
        }
        mImageViewList.add(view);
        mPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(mImageViewList.size() - 1);
    }

    public void removeImageView(PlanningImageView view) {
        if (view == null || !mImageViewList.contains(view)) {
            return;
        }
        mImageViewList.remove(view);
        mPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(mImageViewList.size() - 1);
    }

    public List<AppInfo> getAreaApp() {
        if (mImageViewList == null || mImageViewList.size() == 0) {
            return new ArrayList<>();
        }
        List<AppInfo> result = new ArrayList<>();
        // 先放之前的
        for (int i = 0;i < mImageViewList.size();i++) {
            if (i == mSelectIndex) {
                continue;
            }
            result.add(mImageViewList.get(i).getAppInfo());
        }
        // 再放选中的
        result.add(mImageViewList.get(mSelectIndex).getAppInfo());
        return result;
    }

    public List<String> getAreaIds() {
        if (mImageViewList == null || mImageViewList.size() == 0) {
            return new ArrayList<>();
        }
        List<String> result = new ArrayList<>();
        // 先放之前的
        for (int i = 0;i < mImageViewList.size();i++) {
            if (i == mSelectIndex) {
                continue;
            }
            result.add(mImageViewList.get(i).getAppInfo().getId());
        }
        // 再放选中的
        result.add(mImageViewList.get(mSelectIndex).getAppInfo().getId());
        return result;

    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
        mOnClickListener = l;
    }

    @Override
    public void setSelected(boolean selected) {
        mLayout.setSelected(selected);
    }
}
