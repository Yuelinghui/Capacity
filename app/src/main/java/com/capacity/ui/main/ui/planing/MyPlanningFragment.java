package com.capacity.ui.main.ui.planing;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.capacity.ui.R;
import com.capacity.ui.core.BaseFragment;
import com.capacity.ui.main.entity.AppInfo;
import com.capacity.ui.main.entity.AppItemData;
import com.capacity.ui.main.model.MainModel;
import com.capacity.ui.main.protocol.param.LayoutAreasParam;
import com.capacity.ui.main.ui.home.SelectedAppGridAdapter;
import com.capacity.widget.CustomToast;
import com.capacity.widget.PlanningLayout;
import com.capacity.widget.TitleBar;
import com.qdaily.frame.core.RxSubscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by yuelinghui on 17/8/31.
 */

public class MyPlanningFragment extends BaseFragment implements View.OnClickListener {

    private static final String TYPE_LAYOUT = "TYPE_LAYOUT";
    private static final String LAYOUT_NAME = "LAYOUT_NAME";

    @Bind(R.id.title_bar)
    TitleBar mTitleBar;
    @Bind(R.id.grid_app)
    GridView mGridView;
    @Bind(R.id.layout_1)
    ViewStub mLayoutStub1;
    @Bind(R.id.layout_2)
    ViewStub mLayoutStub2;
    @Bind(R.id.layout_3)
    ViewStub mLayoutStub3;
    @Bind(R.id.layout_4)
    ViewStub mLayoutStub4;
    @Bind(R.id.layout_5)
    ViewStub mLayoutStub5;
    @Bind(R.id.layout_6)
    ViewStub mLayoutStub6;
    @Bind(R.id.layout_7)
    ViewStub mLayoutStub7;
    @Bind(R.id.layout_8)
    ViewStub mLayoutStub8;
    @Bind(R.id.layout_9)
    ViewStub mLayoutStub9;


    @Bind(R.id.edit_name)
    EditText mNameEdit;

    private PlanningLayout mArea1;
    private PlanningLayout mArea2;
    private PlanningLayout mArea3;
    private PlanningLayout mArea4;
    private PlanningLayout mArea5;
    private PlanningLayout mArea6;
    private PlanningLayout mArea7;

    private int mType;
    private String mName;
    private List<AppItemData> mAppItemList;
    private SelectedAppGridAdapter mAppAdapter;
    private View mCheckLayout;

    private int mCheckViewPosition = 0;

    private MainModel mMainModel;


    public static MyPlanningFragment newInstance(int type, String name) {

        Bundle args = new Bundle();
        args.putInt(TYPE_LAYOUT, type);
        args.putString(LAYOUT_NAME, name);
        MyPlanningFragment fragment = new MyPlanningFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.my_planning_fragment_layout;
    }

    @Override
    protected void initData() {
        mMainModel = new MainModel(mActivity);
        mType = getArguments().getInt(TYPE_LAYOUT);
        mName = getArguments().getString(LAYOUT_NAME);

        mTitleBar.setOnLeftClickListener(new TitleBar.OnLeftClickListener() {
            @Override
            public void onLeftClick() {
                mActivity.onBackPressed();
            }
        });

        mTitleBar.setRightTxt(getString(R.string.planning_save));
        mTitleBar.setOnRightClickListener(new TitleBar.OnRightClickListener() {
            @Override
            public void onRightClick() {

                List<LayoutAreasParam> areas = new ArrayList<LayoutAreasParam>();
                if (mType == 1) {
                    areas = initAreasLayout1();
                } else if (mType == 2) {
                    areas = initAreasLayout2();
                } else if (mType == 3) {
                    areas = initAreasLayout3();
                } else if (mType == 4) {
                    areas = initAreasLayout4();
                } else if (mType == 5) {
                    areas = initAreasLayout5();
                } else if (mType == 6) {
                    areas = initAreasLayout6();
                } else if (mType == 7) {
                    areas = initAreasLayout7();
                } else if (mType == 8) {
                    areas = initAreasLayout8();
                } else if (mType == 9) {
                    areas = initAreasLayout9();
                }


                mMainModel.createLayout(mNameEdit.getText().toString(), String.valueOf(mType), areas, new RxSubscriber<Void>() {
                    @Override
                    protected void onNetStart() {
                        mActivity.showLoadingView();
                    }

                    @Override
                    protected void onSuccess(Void aVoid) {
                        CustomToast.makeText("已经保存成功").show();
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
        });

        mAppItemList = new ArrayList<>();

        mMainModel.getSelectedApp(new RxSubscriber<List<AppInfo>>() {
            @Override
            protected void onNetStart() {

            }

            @Override
            protected void onSuccess(List<AppInfo> appInfos) {
                if (appInfos == null || appInfos.size() == 0) {
                    onFail("没有选定应用，请先选定应用");
                    return;
                }
                mAppItemList = convert(appInfos);
                mAppAdapter = new SelectedAppGridAdapter(mActivity, mAppItemList);
                mGridView.setAdapter(mAppAdapter);
            }

            @Override
            protected void onFail(String msg) {
                CustomToast.makeText(msg).show();
            }

            @Override
            protected void onFinish() {

            }
        });
    }

    @Override
    protected void initView() {
        View view = null;
        if (mType == 1) {
            view = mLayoutStub1.inflate();
            initLayout1(view);
        } else if (mType == 2) {
            view = mLayoutStub2.inflate();
            initLayout2(view);
        } else if (mType == 3) {
            view = mLayoutStub3.inflate();
            initLayout3(view);
        } else if (mType == 4) {
            view = mLayoutStub4.inflate();
            initLayout4(view);
        } else if (mType == 5) {
            view = mLayoutStub5.inflate();
            initLayout5(view);
        } else if (mType == 6) {
            view = mLayoutStub6.inflate();
            initLayout6(view);
        } else if (mType == 7) {
            view = mLayoutStub7.inflate();
            initLayout7(view);
        } else if (mType == 8) {
            view = mLayoutStub8.inflate();
            initLayout8(view);
        } else if (mType == 9) {
            view = mLayoutStub9.inflate();
            initLayout9(view);

        }

        mNameEdit.setText(mName);
        mGridView.setOnItemClickListener(mItemClickListener);
    }

    private void initLayout9(View view) {
        mArea1 = (PlanningLayout) view.findViewById(R.id.layout_pager_1);
        mArea2 = (PlanningLayout) view.findViewById(R.id.layout_pager_2);
        mArea3 = (PlanningLayout) view.findViewById(R.id.layout_pager_3);

        mArea1.setOnClickListener(this);
        mArea2.setOnClickListener(this);
        mArea3.setOnClickListener(this);
    }

    private void initLayout8(View view) {
        mArea1 = (PlanningLayout) view.findViewById(R.id.layout_pager_1);
        mArea2 = (PlanningLayout) view.findViewById(R.id.layout_pager_2);
        mArea3 = (PlanningLayout) view.findViewById(R.id.layout_pager_3);
        mArea4 = (PlanningLayout) view.findViewById(R.id.layout_pager_4);

        mArea1.setOnClickListener(this);
        mArea2.setOnClickListener(this);
        mArea3.setOnClickListener(this);
        mArea4.setOnClickListener(this);
    }

    private void initLayout7(View view) {
        mArea1 = (PlanningLayout) view.findViewById(R.id.layout_pager_1);
        mArea2 = (PlanningLayout) view.findViewById(R.id.layout_pager_2);
        mArea3 = (PlanningLayout) view.findViewById(R.id.layout_pager_3);
        mArea4 = (PlanningLayout) view.findViewById(R.id.layout_pager_4);

        mArea1.setOnClickListener(this);
        mArea2.setOnClickListener(this);
        mArea3.setOnClickListener(this);
        mArea4.setOnClickListener(this);
    }

    private void initLayout6(View view) {
        mArea1 = (PlanningLayout) view.findViewById(R.id.layout_pager_1);
        mArea2 = (PlanningLayout) view.findViewById(R.id.layout_pager_2);
        mArea3 = (PlanningLayout) view.findViewById(R.id.layout_pager_3);
        mArea4 = (PlanningLayout) view.findViewById(R.id.layout_pager_4);
        mArea5 = (PlanningLayout) view.findViewById(R.id.layout_pager_5);

        mArea1.setOnClickListener(this);
        mArea2.setOnClickListener(this);
        mArea3.setOnClickListener(this);
        mArea4.setOnClickListener(this);
        mArea5.setOnClickListener(this);
    }

    private void initLayout5(View view) {
        mArea1 = (PlanningLayout) view.findViewById(R.id.layout_pager_1);
        mArea2 = (PlanningLayout) view.findViewById(R.id.layout_pager_2);
        mArea3 = (PlanningLayout) view.findViewById(R.id.layout_pager_3);
        mArea4 = (PlanningLayout) view.findViewById(R.id.layout_pager_4);
        mArea5 = (PlanningLayout) view.findViewById(R.id.layout_pager_5);

        mArea1.setOnClickListener(this);
        mArea2.setOnClickListener(this);
        mArea3.setOnClickListener(this);
        mArea4.setOnClickListener(this);
        mArea5.setOnClickListener(this);
    }

    private void initLayout4(View view) {
        mArea1 = (PlanningLayout) view.findViewById(R.id.layout_pager_1);
        mArea2 = (PlanningLayout) view.findViewById(R.id.layout_pager_2);
        mArea3 = (PlanningLayout) view.findViewById(R.id.layout_pager_3);
        mArea4 = (PlanningLayout) view.findViewById(R.id.layout_pager_4);
        mArea5 = (PlanningLayout) view.findViewById(R.id.layout_pager_5);
        mArea6 = (PlanningLayout) view.findViewById(R.id.layout_pager_6);
        mArea7 = (PlanningLayout) view.findViewById(R.id.layout_pager_7);

        mArea1.setOnClickListener(this);
        mArea2.setOnClickListener(this);
        mArea3.setOnClickListener(this);
        mArea4.setOnClickListener(this);
        mArea5.setOnClickListener(this);
        mArea6.setOnClickListener(this);
        mArea7.setOnClickListener(this);
    }

    private void initLayout3(View view) {
        mArea1 = (PlanningLayout) view.findViewById(R.id.layout_pager_1);
        mArea2 = (PlanningLayout) view.findViewById(R.id.layout_pager_2);
        mArea3 = (PlanningLayout) view.findViewById(R.id.layout_pager_3);
        mArea4 = (PlanningLayout) view.findViewById(R.id.layout_pager_4);
        mArea5 = (PlanningLayout) view.findViewById(R.id.layout_pager_5);

        mArea1.setOnClickListener(this);
        mArea2.setOnClickListener(this);
        mArea3.setOnClickListener(this);
        mArea4.setOnClickListener(this);
        mArea5.setOnClickListener(this);
    }

    private void initLayout2(View view) {
        mArea1 = (PlanningLayout) view.findViewById(R.id.layout_pager_1);
        mArea2 = (PlanningLayout) view.findViewById(R.id.layout_pager_2);
        mArea3 = (PlanningLayout) view.findViewById(R.id.layout_pager_3);
        mArea4 = (PlanningLayout) view.findViewById(R.id.layout_pager_4);
        mArea5 = (PlanningLayout) view.findViewById(R.id.layout_pager_5);
        mArea6 = (PlanningLayout) view.findViewById(R.id.layout_pager_6);
        mArea7 = (PlanningLayout) view.findViewById(R.id.layout_pager_7);

        mArea1.setOnClickListener(this);
        mArea2.setOnClickListener(this);
        mArea3.setOnClickListener(this);
        mArea4.setOnClickListener(this);
        mArea5.setOnClickListener(this);
        mArea6.setOnClickListener(this);
        mArea7.setOnClickListener(this);
    }

    private void initLayout1(View view) {
        mArea1 = (PlanningLayout) view.findViewById(R.id.layout_pager_1);
        mArea2 = (PlanningLayout) view.findViewById(R.id.layout_pager_2);
        mArea3 = (PlanningLayout) view.findViewById(R.id.layout_pager_3);
        mArea4 = (PlanningLayout) view.findViewById(R.id.layout_pager_4);
        mArea5 = (PlanningLayout) view.findViewById(R.id.layout_pager_5);
        mArea6 = (PlanningLayout) view.findViewById(R.id.layout_pager_6);

        mArea1.setOnClickListener(this);
        mArea2.setOnClickListener(this);
        mArea3.setOnClickListener(this);
        mArea4.setOnClickListener(this);
        mArea5.setOnClickListener(this);
        mArea6.setOnClickListener(this);
    }


    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            AppItemData itemData = mAppItemList.get(position);
            if (itemData.hasSelected) {
                CustomToast.makeText(R.string.planning_choosed).show();
            } else {
                if (mCheckViewPosition == 0) {
                    CustomToast.makeText(R.string.planning_choose_view).show();
                    return;
                }
                int viewType = getViewType();
                if (!itemData.appInfo.canAddPlanningView(viewType)) {
                    CustomToast.makeText(R.string.planning_error).show();
                    return;
                }

                itemData.hasSelected = true;
                PlanningImageView imageView = new PlanningImageView(mActivity);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setOnDismissListener(mDismissListener);
                imageView.setOnClickListener(MyPlanningFragment.this);

                if (mCheckViewPosition == 1) {
                    mArea1.addImageView(imageView);
                } else if (mCheckViewPosition == 2) {
                    mArea2.addImageView(imageView);
                } else if (mCheckViewPosition == 3) {
                    mArea3.addImageView(imageView);
                } else if (mCheckViewPosition == 4) {
                    mArea4.addImageView(imageView);
                } else if (mCheckViewPosition == 5) {
                    mArea5.addImageView(imageView);
                } else if (mCheckViewPosition == 6) {
                    mArea6.addImageView(imageView);
                } else if (mCheckViewPosition == 7) {
                    mArea7.addImageView(imageView);
                }
                imageView.setData(itemData, viewType);
                mAppAdapter.notifyDataSetChanged();
            }
        }
    };

    private int getViewType() {
        String typeValue = "0";
        if (mCheckViewPosition == 1) {
            typeValue = (String) mArea1.getTag();
        } else if (mCheckViewPosition == 2) {
            typeValue = (String) mArea2.getTag();
        } else if (mCheckViewPosition == 3) {
            typeValue = (String) mArea3.getTag();
        } else if (mCheckViewPosition == 4) {
            typeValue = (String) mArea4.getTag();
        } else if (mCheckViewPosition == 5) {
            typeValue = (String) mArea5.getTag();
        } else if (mCheckViewPosition == 6) {
            typeValue = (String) mArea6.getTag();
        } else if (mCheckViewPosition == 7) {
            typeValue = (String) mArea7.getTag();
        }
        return Integer.valueOf(typeValue);
    }

    private PlanningImageView.OnDismissListener mDismissListener = new PlanningImageView.OnDismissListener() {
        @Override
        public void onDismiss(PlanningImageView view, String url) {

            if (mCheckViewPosition == 1) {
                mArea1.removeImageView(view);
            } else if (mCheckViewPosition == 2) {
                mArea2.removeImageView(view);
            } else if (mCheckViewPosition == 3) {
                mArea3.removeImageView(view);
            } else if (mCheckViewPosition == 4) {
                mArea4.removeImageView(view);
            } else if (mCheckViewPosition == 5) {
                mArea5.removeImageView(view);
            } else if (mCheckViewPosition == 6) {
                mArea6.removeImageView(view);
            } else if (mCheckViewPosition == 7) {
                mArea7.removeImageView(view);
            }

            for (AppItemData itemData : mAppItemList) {
                if (itemData != null && itemData.appIcon.equals(url)) {
                    itemData.hasSelected = false;
                    break;
                }
            }
            mAppAdapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.layout_pager_1:
                changeCheck(mArea1);
                mCheckViewPosition = 1;
                break;
            case R.id.layout_pager_2:
                changeCheck(mArea2);
                mCheckViewPosition = 2;
                break;
            case R.id.layout_pager_3:
                changeCheck(mArea3);
                mCheckViewPosition = 3;
                break;
            case R.id.layout_pager_4:
                changeCheck(mArea4);
                mCheckViewPosition = 4;
                break;
            case R.id.layout_pager_5:
                changeCheck(mArea5);
                mCheckViewPosition = 5;
                break;
            case R.id.layout_pager_6:
                changeCheck(mArea6);
                mCheckViewPosition = 6;
                break;
            case R.id.layout_pager_7:
                changeCheck(mArea7);
                mCheckViewPosition = 7;
                break;
        }
    }

    private void changeCheck(View view) {
        if (mCheckLayout == null) {
            mCheckLayout = view;
            mCheckLayout.setSelected(true);
        } else {
            mCheckLayout.setSelected(false);
            mCheckLayout = view;
            mCheckLayout.setSelected(true);
        }
    }

    private List<LayoutAreasParam> initAreasLayout1() {
        List<LayoutAreasParam> result = new ArrayList<LayoutAreasParam>();

        LayoutAreasParam area1 = new LayoutAreasParam();
        area1.setPosition(1);
        area1.setAppIds(mArea1.getAreaIds());
        result.add(area1);

        LayoutAreasParam area2 = new LayoutAreasParam();
        area2.setPosition(2);
        area2.setAppIds(mArea2.getAreaIds());
        result.add(area2);

        LayoutAreasParam area3 = new LayoutAreasParam();
        area3.setPosition(3);
        area3.setAppIds(mArea3.getAreaIds());
        result.add(area3);

        LayoutAreasParam area4 = new LayoutAreasParam();
        area4.setPosition(4);
        area4.setAppIds(mArea4.getAreaIds());
        result.add(area4);

        LayoutAreasParam area5 = new LayoutAreasParam();
        area5.setPosition(5);
        area5.setAppIds(mArea5.getAreaIds());
        result.add(area5);

        LayoutAreasParam area6 = new LayoutAreasParam();
        area6.setPosition(6);
        area6.setAppIds(mArea6.getAreaIds());
        result.add(area6);

        return result;
    }

    private List<LayoutAreasParam> initAreasLayout2() {
        List<LayoutAreasParam> result = new ArrayList<LayoutAreasParam>();

        LayoutAreasParam area1 = new LayoutAreasParam();
        area1.setPosition(1);
        area1.setAppIds(mArea1.getAreaIds());
        result.add(area1);

        LayoutAreasParam area2 = new LayoutAreasParam();
        area2.setPosition(2);
        area2.setAppIds(mArea2.getAreaIds());
        result.add(area2);

        LayoutAreasParam area3 = new LayoutAreasParam();
        area3.setPosition(3);
        area3.setAppIds(mArea3.getAreaIds());
        result.add(area3);

        LayoutAreasParam area4 = new LayoutAreasParam();
        area4.setPosition(4);
        area4.setAppIds(mArea4.getAreaIds());
        result.add(area4);

        LayoutAreasParam area5 = new LayoutAreasParam();
        area5.setPosition(5);
        area5.setAppIds(mArea5.getAreaIds());
        result.add(area5);

        LayoutAreasParam area6 = new LayoutAreasParam();
        area6.setPosition(6);
        area6.setAppIds(mArea6.getAreaIds());
        result.add(area6);

        LayoutAreasParam area7 = new LayoutAreasParam();
        area7.setPosition(7);
        area7.setAppIds(mArea7.getAreaIds());
        result.add(area7);

        return result;
    }


    private List<LayoutAreasParam> initAreasLayout3() {
        List<LayoutAreasParam> result = new ArrayList<LayoutAreasParam>();

        LayoutAreasParam area1 = new LayoutAreasParam();
        area1.setPosition(1);
        area1.setAppIds(mArea1.getAreaIds());
        result.add(area1);

        LayoutAreasParam area2 = new LayoutAreasParam();
        area2.setPosition(2);
        area2.setAppIds(mArea2.getAreaIds());
        result.add(area2);

        LayoutAreasParam area3 = new LayoutAreasParam();
        area3.setPosition(3);
        area3.setAppIds(mArea3.getAreaIds());
        result.add(area3);

        LayoutAreasParam area4 = new LayoutAreasParam();
        area4.setPosition(4);
        area4.setAppIds(mArea5.getAreaIds());
        result.add(area4);

        LayoutAreasParam area5 = new LayoutAreasParam();
        area5.setPosition(5);
        area5.setAppIds(mArea5.getAreaIds());
        result.add(area5);

        return result;
    }

    private List<LayoutAreasParam> initAreasLayout4() {
        List<LayoutAreasParam> result = new ArrayList<LayoutAreasParam>();

        LayoutAreasParam area1 = new LayoutAreasParam();
        area1.setPosition(1);
        area1.setAppIds(mArea1.getAreaIds());
        result.add(area1);

        LayoutAreasParam area2 = new LayoutAreasParam();
        area2.setPosition(2);
        area2.setAppIds(mArea2.getAreaIds());
        result.add(area2);

        LayoutAreasParam area3 = new LayoutAreasParam();
        area3.setPosition(3);
        area3.setAppIds(mArea3.getAreaIds());
        result.add(area3);

        LayoutAreasParam area4 = new LayoutAreasParam();
        area4.setPosition(4);
        area4.setAppIds(mArea4.getAreaIds());
        result.add(area4);

        LayoutAreasParam area5 = new LayoutAreasParam();
        area5.setPosition(5);
        area5.setAppIds(mArea5.getAreaIds());
        result.add(area5);

        LayoutAreasParam area6 = new LayoutAreasParam();
        area6.setPosition(6);
        area6.setAppIds(mArea6.getAreaIds());
        result.add(area6);

        LayoutAreasParam area7 = new LayoutAreasParam();
        area7.setPosition(7);
        area7.setAppIds(mArea7.getAreaIds());
        result.add(area7);

        return result;
    }


    private List<LayoutAreasParam> initAreasLayout5() {
        List<LayoutAreasParam> result = new ArrayList<LayoutAreasParam>();

        LayoutAreasParam area1 = new LayoutAreasParam();
        area1.setPosition(1);
        area1.setAppIds(mArea1.getAreaIds());
        result.add(area1);

        LayoutAreasParam area2 = new LayoutAreasParam();
        area2.setPosition(2);
        area2.setAppIds(mArea2.getAreaIds());
        result.add(area2);

        LayoutAreasParam area3 = new LayoutAreasParam();
        area3.setPosition(3);
        area3.setAppIds(mArea3.getAreaIds());
        result.add(area3);

        LayoutAreasParam area4 = new LayoutAreasParam();
        area4.setPosition(4);
        area4.setAppIds(mArea4.getAreaIds());
        result.add(area4);

        LayoutAreasParam area5 = new LayoutAreasParam();
        area5.setPosition(5);
        area5.setAppIds(mArea5.getAreaIds());
        result.add(area5);

        return result;
    }

    private List<LayoutAreasParam> initAreasLayout6() {
        List<LayoutAreasParam> result = new ArrayList<LayoutAreasParam>();

        LayoutAreasParam area1 = new LayoutAreasParam();
        area1.setPosition(1);
        area1.setAppIds(mArea1.getAreaIds());
        result.add(area1);

        LayoutAreasParam area2 = new LayoutAreasParam();
        area2.setPosition(2);
        area2.setAppIds(mArea2.getAreaIds());
        result.add(area2);

        LayoutAreasParam area3 = new LayoutAreasParam();
        area3.setPosition(3);
        area3.setAppIds(mArea3.getAreaIds());
        result.add(area3);

        return result;
    }

    private List<LayoutAreasParam> initAreasLayout7() {
        List<LayoutAreasParam> result = new ArrayList<LayoutAreasParam>();

//        LayoutAreasParam area1 = new LayoutAreasParam();
//        area1.setPosition(1);
//        area1.setAppIds(mArea1.getAreaIds());
//        result.add(area1);
//
//        LayoutAreasParam area2 = new LayoutAreasParam();
//        area2.setPosition(2);
//        area2.setAppIds(mArea2.getAreaIds());
//        result.add(area2);

        LayoutAreasParam area3 = new LayoutAreasParam();
        area3.setPosition(1);
        area3.setAppIds(mArea3.getAreaIds());
        result.add(area3);

        LayoutAreasParam area4 = new LayoutAreasParam();
        area4.setPosition(2);
        area4.setAppIds(mArea4.getAreaIds());
        result.add(area4);

        return result;
    }

    private List<LayoutAreasParam> initAreasLayout8() {
        List<LayoutAreasParam> result = new ArrayList<LayoutAreasParam>();

//        LayoutAreasParam area1 = new LayoutAreasParam();
//        area1.setPosition(1);
//        area1.setAppIds(mArea1.getAreaIds());
//        result.add(area1);
//
//        LayoutAreasParam area2 = new LayoutAreasParam();
//        area2.setPosition(2);
//        area2.setAppIds(mArea2.getAreaIds());
//        result.add(area2);

        LayoutAreasParam area3 = new LayoutAreasParam();
        area3.setPosition(1);
        area3.setAppIds(mArea3.getAreaIds());
        result.add(area3);

        LayoutAreasParam area4 = new LayoutAreasParam();
        area4.setPosition(2);
        area4.setAppIds(mArea4.getAreaIds());
        result.add(area4);

        return result;
    }

    private List<LayoutAreasParam> initAreasLayout9() {
        List<LayoutAreasParam> result = new ArrayList<LayoutAreasParam>();

//        LayoutAreasParam area1 = new LayoutAreasParam();
//        area1.setPosition(1);
//        area1.setAppIds(mArea3.getAreaIds());
//        result.add(area1);
//
//        LayoutAreasParam area2 = new LayoutAreasParam();
//        area2.setPosition(2);
//        area2.setAppIds(mArea2.getAreaIds());
//        result.add(area2);

        LayoutAreasParam area3 = new LayoutAreasParam();
        area3.setPosition(1);
        area3.setAppIds(mArea3.getAreaIds());
        result.add(area3);

        return result;
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
                itemData.appId = appInfo.getId();
                itemData.appInfo = appInfo;
                result.add(itemData);
            }
        }
        return result;
    }

}
