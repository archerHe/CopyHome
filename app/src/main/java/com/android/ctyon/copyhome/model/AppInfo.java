package com.android.ctyon.copyhome.model;

import android.content.Intent;

public class AppInfo {
    private String label;
    private String appIcon;
    private Intent launchIntent;
    private String pkgName;

    public AppInfo(){

    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(String appIcon) {
        this.appIcon = appIcon;
    }

    public Intent getLaunchIntent() {
        return launchIntent;
    }

    public void setLaunchIntent(Intent launchIntent) {
        this.launchIntent = launchIntent;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
}
