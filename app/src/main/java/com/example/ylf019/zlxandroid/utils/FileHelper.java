package com.example.ylf019.zlxandroid.utils;


import com.example.ylf019.zlxandroid.appconfig.AppContact;

import java.io.File;

/**
 * Author:    guozhangyu
 * Description: 文件操作帮住工具类
 */
public class FileHelper {

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return true:存在 false:不存在
     */
    public static boolean isFileExists(String filePath) {
        return StringHelper.notEmpty(filePath) && new File(filePath).exists();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return true:成功 false:失败
     */
    public static boolean deleteFile(String filePath) {
        if (StringHelper.notEmpty(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
        }

        return false;
    }

    public static String checkAndMkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    public static String getAppPath() {
        return checkAndMkdirs(AppContact.FILE_DATA_ROOT_PATH);
    }

    public static String getAvatarDir() {
        return checkAndMkdirs(AppContact.FILE_DATA_AVATAR_PATH);
    }

    public static String getPicturePath() {
        return checkAndMkdirs(AppContact.FILE_DATA_PICTURE_PATH);
    }
    public static String getAudioPath() {
        return checkAndMkdirs(AppContact.FILE_DATA_AUDIO_PATH);
    }
}
