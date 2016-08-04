package com.wgl.mvp.requestPermission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限类
 * Created by wgl
 */
public class RequestPermissionUtil {

    public RequestPermissionUtil() {
    }

    public static RequestPermissionUtil requestPermissionUtil;


    public static synchronized RequestPermissionUtil getRequestPermissionUtilInstance() {
        if (requestPermissionUtil == null) {
            requestPermissionUtil = new RequestPermissionUtil();
        }
        return requestPermissionUtil;
    }

    /**
     * 电话权限
     */
    public static final int REQUEST_CALL_PHONE_PERMISSIONS = 1;
    /**
     * 照相权限
     */
    public static final int REQUEST_CAMERA_PERMISSIONS = 2;
    /**
     * 读取相册权限
     */
    public static final int REQUEST_GALLERY_PERMISSIONS = 3;
    /**
     * 发短信
     */
    public static final int REQUEST_SEND_SMS= 4;
    /**
     * 读短信
     */
    public static final int REQUEST_READ_SMS= 5;
    /**
     * 录音权限
     */
    public static final int REQUEST_RECORD_AUDIO= 6;
    public static final int REQUEST_MOUNT_UNMOUNT_FILESYSTEMS= 7;

    /**
     * 检测授权
     *
     * @param context
     * @param ManifestPermission
     * @param requestCode        请求吗
     * @return true:已经授权   false没有授权
     */
  public boolean insertDummyContactWrapper(Activity context, String ManifestPermission, int requestCode) {
        int hasWriteContactsPermission = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            hasWriteContactsPermission = context.checkSelfPermission(ManifestPermission);
        }
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED)  {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(new String[]{ManifestPermission},
                        requestCode);
            }
            return false;
        } else {
            return true;
        }

    }


    /**
     * 请求多个权限
     */
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    public void insertDummyContactWrapper(Activity context) {
        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission./*READ_PHONE_STATE*/WRITE_EXTERNAL_STORAGE,context))
            permissionsNeeded.add("READ_PHONE_STATE");
        if (!addPermission(permissionsList, Manifest.permission./*ACCESS_COARSE_LOCATION*/MOUNT_UNMOUNT_FILESYSTEMS,context))
            permissionsNeeded.add("ACCESS_COARSE_LOCATION");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE,context))
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = /*"You need to grant access to " +*/ permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    context. requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                            REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }

                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
            return;
        }
       // MyToastView.showToast("没有拒绝的权限了",context);

    }

    private boolean addPermission(List<String> permissionsList, String permission,Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!context.shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    /**
     * 回调多个权限处理
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults,Context activity) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                  //  insertDummyContact();
                } else {
                    // Permission Denied
                    Toast.makeText(activity, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
          //      new Activity(). super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
