package com.tech.chatapp.model;

import android.content.Context;
import android.os.Build;
import android.provider.Settings.Secure;

import com.tech.chatapp.BuildConfig;
import com.tech.chatapp.R;

import java.io.Serializable;


public class DeviceInfo implements Serializable {
    public String deviceUDID;
    public String deviceToken;
    public String appName;
    public String appVersion;
    public String deviceSystemVersion;
    public String deviceModel;
    public String deviceName;
    public String platformType;



    public DeviceInfo(Context _context) {
        deviceUDID = Secure.getString(_context.getContentResolver(), Secure.ANDROID_ID);
        deviceToken =_context.getResources().getString(R.string.token_txt);
        appName = _context.getResources().getString(R.string.app_name);
        appVersion = String.valueOf(BuildConfig.VERSION_NAME);
        deviceSystemVersion = "Android " + Build.VERSION.RELEASE;
        deviceModel = Build.MODEL;
        deviceName = Build.MANUFACTURER;
        platformType = _context.getResources().getString(R.string.platform_type);


    }


    public String getDeviceUDID() {
        return deviceUDID;
    }

    public void setDeviceUDID(String deviceUDID) {
        this.deviceUDID = deviceUDID;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getDeviceSystemVersion() {
        return deviceSystemVersion;
    }

    public void setDeviceSystemVersion(String deviceSystemVersion) {
        this.deviceSystemVersion = deviceSystemVersion;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }
}
