package com.android.ctyon.copyhome.utils;


import android.content.Context;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件路径的获取和拼接
 *
 */
public class FilePathUtil {

    /**
     * 生成存储文件的路径，如果有sd卡则获取sd卡路径，否则获取应用缓存区路径。
     *
     * @param context    应用Context
     * @param folderPath 文件夹路径
     * @param fileName   文件名
     * @return 生成的文件路径
     */
    private static final String TAG = "FilePathUtil";

    public static String makeFilePath(Context context, String folderPath, String fileName) {
        File file = null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            file = new File(android.os.Environment.getExternalStorageDirectory(),
                    folderPath);
        } else {
            file = context.getApplicationContext().getCacheDir();
        }
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
        StringBuilder absoluteFolderPath = new StringBuilder(file.getAbsolutePath());
        if (!absoluteFolderPath.toString().endsWith("/")) {
            absoluteFolderPath.append("/");
        }
        if (fileName != null) {
            absoluteFolderPath.append(fileName);
        }
        return absoluteFolderPath.toString();
    }


    /**
     * 得到文件夹目录
     *
     * @param context
     * @param folderPath
     * @return
     */
    public static String getFolderDir(Context context, String folderPath) {
        File file = null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            file = new File(android.os.Environment.getExternalStorageDirectory(),
                    folderPath);
        } else {
            file = context.getApplicationContext().getCacheDir();
        }
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }

        return file.getPath();
    }

    /**
     * 清空某一路径下的文件
     *
     * @param context
     * @param filePath
     */
    public static void clearFilePath(Context context, File filePath) {
        if (!filePath.exists()) {
            return;
        }
        if (filePath.isFile()) {
            filePath.delete();
            return;
        }
        if (filePath.isDirectory()) {
            File[] folders = filePath.listFiles();
            for (int i = 0; i < folders.length; i++) {
                clearFilePath(context, folders[i]);
            }
        }
    }


    private static List<String> musicNames = new ArrayList<>();
    public static List<String> scanFileList(File parentFile) {
        Log.d(TAG, "scanFileList: " + parentFile.getPath());
        File[] listFile = parentFile.listFiles();
        if(listFile != null){


            int length = listFile.length;

            if (listFile != null) {
                for (int i = 0; i < length; i++) {
                    File file = listFile[i];
                    if (file.isDirectory()) {
                        scanFileList(file);
                    } else {
                        //file://music/p/xx.mp3 >wma>aac wav>flac ogg>ape
                        if (file.getName().endsWith(".mp3") || file.getName().endsWith(".wav") || file.getName().endsWith(".ogg")) {
                            if(file.length() > 0){
                                Log.d(TAG, "scanFileList path: " + file.getAbsolutePath());
                                musicNames.add(file.getAbsolutePath());
                            }
                        }
                    }
                }
            }
        }
        return musicNames;
    }


    public static String getStoragePath(Context mContext, boolean is_removale) {

        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (is_removale == removable) {
                    return path;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

