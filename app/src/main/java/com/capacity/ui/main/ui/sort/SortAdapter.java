package com.capacity.ui.main.ui.sort;

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
 * Created by yuelinghui on 17/8/31.
 */

public class SortAdapter extends BaseAdapter {

    private Context mContext;

    private List<AppItemData> mAppItemList;

    public SortAdapter(Context context, List<AppItemData> list) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.app_sort_item_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AppItemData appItemData = getItem(position);
        viewHolder.appNameTxt.setText(appItemData.appName);
        ImageManager.displayImage(mContext, appItemData.appIcon, viewHolder.appIconImg);
        return convertView;
    }

    class ViewHolder {

        @Bind(R.id.img_app_icon)
        ImageView appIconImg;
        @Bind(R.id.txt_app_name)
        TextView appNameTxt;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
