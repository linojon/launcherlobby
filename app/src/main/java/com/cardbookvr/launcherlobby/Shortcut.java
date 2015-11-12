package com.cardbookvr.launcherlobby;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

public class Shortcut {
    public String name;
    public Drawable icon;
    ActivityInfo info;

    public Shortcut(ResolveInfo info, PackageManager packageManager){
        name = info.loadLabel(packageManager).toString();
        icon = info.loadIcon(packageManager);
        this.info = info.activityInfo;
    }
}
