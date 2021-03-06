package com.ebgsplatform.badouloanapp.utils.permission;

/**
 * 申请权限回调接口
 * Created by XuanQinjian on 2017/2/8.
 */
public interface RequestPermissionCallback {
    /**
     * 权限已被授予
     * */
    void onGranted();
    /**
     * 用户拒绝权限申请
     * */
    void onDenied();

}
