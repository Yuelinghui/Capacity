package com.capacity.ui.main.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.capacity.manager.ImageManager;
import com.capacity.ui.R;
import com.capacity.ui.main.entity.AppItemData;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yuelinghui on 17/10/31.
 */

public class SelectedAppGridAdapter extends BaseAdapter {



    private List<AppItemData> mAppItemList;
    private Context mContext;

    public SelectedAppGridAdapter(Context context,List<AppItemData> list) {
        mContext = context;
        mAppItemList = list;
    }

    @Override
    public int getCount() {
        return mAppItemList == null ? 0 : mAppItemList.size();
    }

    @Override
    public AppItemData getItem(int position) {
        return mAppItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AppViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.home_app_item_layout, parent, false);
            viewHolder = new AppViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AppViewHolder) convertView.getTag();
        }

        AppItemData appItemData = getItem(position);
        if (appItemData.hasSelected) {
            viewHolder.appLayout.setBackgroundResource(R.color.bg_titlebar_line_day);
            viewHolder.appNameTxt.setBackgroundResource(R.color.bg_titlebar_line_day);
            viewHolder.appNameTxt.setTextColor(mContext.getResources().getColor(R.color.black));
        } else {
            viewHolder.appLayout.setBackgroundResource(R.color.black);
            viewHolder.appNameTxt.setBackgroundResource(R.color.black);
            viewHolder.appNameTxt.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        viewHolder.appNameTxt.setText(appItemData.appName);
        ImageManager.displayImage(mContext, appItemData.appIcon, viewHolder.appIconImg);
        return convertView;
    }

    class AppViewHolder {

        @Bind(R.id.img_app_icon)
        ImageView appIconImg;
        @Bind(R.id.txt_app_name)
        TextView appNameTxt;
        @Bind(R.id.layout_app_item)
        ViewGroup appLayout;

        AppViewHolder(View item) {
            ButterKnife.bind(this, item);
        }
    }
}
