package com.capacity.ui.main.model;

import android.content.Context;
import android.os.Environment;

import com.capacity.manager.UserInfoManager;
import com.capacity.ui.core.CommandCode;
import com.capacity.ui.main.entity.AppInfo;
import com.capacity.ui.main.entity.AppSortInfo;
import com.capacity.ui.main.protocol.AppSortListService;
import com.capacity.ui.main.protocol.CreateLayoutService;
import com.capacity.ui.main.protocol.DeleteLayoutService;
import com.capacity.ui.main.protocol.IsSelectAppService;
import com.capacity.ui.main.protocol.SceneListService;
import com.capacity.ui.main.protocol.SearchLayoutService;
import com.capacity.ui.main.protocol.SelectAppService;
import com.capacity.ui.main.protocol.SelectedAppService;
import com.capacity.ui.main.protocol.SortListService;
import com.capacity.ui.main.protocol.UnSelectAppService;
import com.capacity.ui.main.protocol.UpdateLayoutService;
import com.capacity.ui.main.protocol.param.LayoutAreasParam;
import com.capacity.ui.main.protocol.param.LayoutParam;
import com.qdaily.frame.core.RxSubscriber;
import com.qdaily.frame.managercenter.MManagerCenter;
import com.qdaily.frame.util.FileCache;
import com.qdaily.frame.util.JsonUtil;
import com.qdaily.network.NetModel;
import com.qdaily.network.manager.NetManager;
import com.qdaily.network.manager.RxManager;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by yuelinghui on 17/9/7.
 */

public class MainModel extends NetModel {

    private static String PATH;

    public MainModel(Context context) {
        super(context);
        PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download" + "/object/";
    }

    /**
     * 获取应用分类
     *
     * @param subscriber
     */
    public void getAppSort(RxSubscriber<List<AppSortInfo>> subscriber) {
        SortListService sortService = NetManager.getInstance().create(SortListService.class);

        RxManager.getInstance().doSubscribe(sortService.getAppSort(CommandCode.SORT), subscriber);
    }

    /**
     * 获取应用场景
     *
     * @param subscriber
     */
    public void getAppScene(RxSubscriber<List<AppSortInfo>> subscriber) {
        SceneListService sortService = NetManager.getInstance().create(SceneListService.class);

        RxManager.getInstance().doSubscribe(sortService.getAppSort(CommandCode.SCENE), subscriber);
    }

    /**
     * 获取分类下应用列表
     * @param feature
     * @param key
     * @param subscriber
     */
    public void getAppSortList(String feature, String key, RxSubscriber<List<AppInfo>> subscriber) {
        AppSortListService appSortListService = NetManager.getInstance().create(AppSortListService.class);

        RxManager.getInstance().doSubscribe(appSortListService.getAppSortList(CommandCode.APP_LIST, feature, key,
                MManagerCenter.getManager(UserInfoManager.class).getUserId()), subscriber);
    }

    public void selectApp(String appId, RxSubscriber<Void> subscriber) {
        SelectAppService chooceAppService = NetManager.getInstance().create(SelectAppService.class);
        RxManager.getInstance().doSubscribe(chooceAppService.selectApp(CommandCode.SELECT_APP
                , MManagerCenter.getManager(UserInfoManager.class).getUserId(), appId), subscriber);
    }

    public void unSelectApp(String appId, RxSubscriber<Void> subscriber) {
        UnSelectAppService unSelectAppService = NetManager.getInstance().create(UnSelectAppService.class);
        RxManager.getInstance().doSubscribe(unSelectAppService.unSelectApp(CommandCode.UNSELECT_APP
                , MManagerCenter.getManager(UserInfoManager.class).getUserId(), appId), subscriber);
    }

    public void getSelectedApp(RxSubscriber<List<AppInfo>> subscriber) {
        SelectedAppService selectedAppService = NetManager.getInstance().create(SelectedAppService.class);
        RxManager.getInstance().doSubscribe(selectedAppService.getSelectedApp(CommandCode.SELECTED_APP,
                MManagerCenter.getManager(UserInfoManager.class).getUserId()), subscriber);
    }

    public void isAppSelect(String appId, RxSubscriber<Boolean> subscriber) {
        IsSelectAppService isSelectAppService = NetManager.getInstance().create(IsSelectAppService.class);
        RxManager.getInstance().doSubscribe(isSelectAppService.isAppSelect(CommandCode.IS_APP_SELECT,
                MManagerCenter.getManager(UserInfoManager.class).getUserId(), appId), subscriber);
    }

    public void createLayout(String layoutName, String layoutType, List<LayoutAreasParam> layoutAreasList, RxSubscriber<Void> subscriber) {
        if (layoutAreasList == null || layoutAreasList.size() == 0) {
            return;
        }

        LayoutParam layoutInfo = new LayoutParam();
        layoutInfo.layoutName = layoutName;
        layoutInfo.layoutType = layoutType;
        layoutInfo.areas = layoutAreasList;
        RequestBody body = createRequestBody(JsonUtil.toJSONString(layoutInfo));

        MManagerCenter.getManager(FileCache.class).setString(System.currentTimeMillis()/1000+"", JsonUtil.toJSONString(layoutInfo));

        CreateLayoutService createLayoutService = NetManager.getInstance().create(CreateLayoutService.class);
        RxManager.getInstance().doSubscribe(createLayoutService.createLayout(CommandCode.CREATE_LAYOUT
                , body), subscriber);

    }

    public void updateLayout(String layoutId, String layoutName, String layoutType, List<LayoutAreasParam> layoutAreasList, RxSubscriber<Void> subscriber) {
        if (layoutAreasList == null || layoutAreasList.size() == 0) {
            return;
        }

        LayoutParam layoutInfo = new LayoutParam();
        layoutInfo.layoutId = layoutId;
        layoutInfo.layoutName = layoutName;
        layoutInfo.layoutType = layoutType;
        layoutInfo.areas = layoutAreasList;
        RequestBody body = createRequestBody(JsonUtil.toJSONString(layoutInfo));

        UpdateLayoutService updateLayoutService = NetManager.getInstance().create(UpdateLayoutService.class);
        RxManager.getInstance().doSubscribe(updateLayoutService.updateLayout(CommandCode.UPDATE_LAYOUT, body), subscriber);
    }

    public void deleteLayout(String layoutId, RxSubscriber<Void> subscriber) {
        DeleteLayoutService deleteLayoutService = NetManager.getInstance().create(DeleteLayoutService.class);
        RxManager.getInstance().doSubscribe(deleteLayoutService.deleteLayout(CommandCode.DELETE_LAYOUT
                , MManagerCenter.getManager(UserInfoManager.class).getUserId(), layoutId), subscriber);
    }

    public void searchLayout(RxSubscriber<LayoutParam> subscriber) {
        SearchLayoutService searchLayoutService = NetManager.getInstance().create(SearchLayoutService.class);
        RxManager.getInstance().doSubscribe(searchLayoutService.searchLayout(CommandCode.SEARCH_LAYOUT
                , MManagerCenter.getManager(UserInfoManager.class).getUserId()), subscriber);
    }

}
