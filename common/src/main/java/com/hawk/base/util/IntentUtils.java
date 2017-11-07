package com.hawk.base.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.hawk.base.R;

import java.io.File;

/**
 * Created by lan on 2017-09-27.
 */

public class IntentUtils {
    /**
     * 获取系统软件包版本号
     */
    public static String getVersionName(Context context){
        PackageManager pkgManager = context.getPackageManager();

        try{
            PackageInfo info = pkgManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);

            return info.versionName;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 获取拍照Uri
     */
    public static Uri getImageUri(Context context, File file) {
        String imgAuthority = context.getString(R.string.provider_image);
        Uri fileUri = FileProvider.getUriForFile(context, imgAuthority, file);

        return fileUri;
    }
    /**
     * 获取拍照
     */
    public static Intent getImageCapture(Uri fileUri) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        return openCameraIntent;
    }
}
