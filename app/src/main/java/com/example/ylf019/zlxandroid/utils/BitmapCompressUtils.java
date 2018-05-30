package com.example.ylf019.zlxandroid.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import com.example.ylf019.zlxandroid.appconfig.AppContact;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by guozhangyu on 16/4/14.
 * changeDate at 16/4/14
 */
public class BitmapCompressUtils {
    private static BitmapCompressUtils mInstance;
    private static String mSavePath;
    /**
     * 默认地址
     **/
    public static String path = AppContact.FILE_DATA_PICTURE_PATH;

    public static BitmapCompressUtils getInstant(String savePath) {
        if (mInstance == null) {
            if (TextUtils.isEmpty(savePath)) {
                savePath = path;
            }
            mInstance = new BitmapCompressUtils(savePath);
        }
        return mInstance;
    }

    private BitmapCompressUtils(String savaPath) {
        mSavePath = savaPath;
    }
    /**
     * 保存压缩图片
     */
    public String compressBitmap(String currentPhotoPath, String fileName) {
        String savePath = null;
        if (currentPhotoPath != null) {
            try {
                Bitmap bm = getSmallBitmap(currentPhotoPath, fileName);
                // 重新修改图片角度
                int angle = getBitmapDegree(currentPhotoPath);
                if (bm != null) {
                    bm = rotateBitmapByDegree(bm, angle);
                    savePath = save(bm, fileName);
                    if (bm != null && !bm.isRecycled()) {
                        bm.recycle();
                        bm = null;
                    }
                    System.gc();
                    return savePath;
                }
            } catch (Exception e) {

            }

        }
        return savePath;
    }

    /**
     * 图片保存到本地
     */
    public String save(Bitmap bitmap, String fileName) {
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
            byte[] byteArray = baos.toByteArray();
            File dir = new File(mSavePath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 文件名为空则使用时间搓做文件名
            if (fileName == null || fileName.length() == 0) {
                fileName = System.currentTimeMillis() + ".jpg";
            }
            File file = new File(mSavePath + "/" + fileName);
            file.delete();
            if (!file.exists()) {
                Log.e("path_img", mSavePath + "/" + fileName);
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(byteArray);
            return file.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                    baos = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                    bos = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
                bitmap = null;
                System.gc();
            }
        }
        return "";
    }

    /**
     * 计算采样值
     */
    public int calculateInSampleSize(BitmapFactory.Options options,
                                     int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * @param filePath
     * @param fileName
     * @return
     */
    public Bitmap getSmallBitmap(String filePath, String fileName) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        final int height = options.outHeight;
        final int width = options.outWidth;

        Bitmap bitmap = null;
        // 计算长度与宽图，处理
        if (height / width >= 10 || width / height >= 10) {
            options.inSampleSize = calculateInSampleSize(options, width, height);
        } else {
            if (height >= 5500) {
                options.inSampleSize = calculateInSampleSize(options, 520, 420);
            } else if (width >= 5500) {
                options.inSampleSize = calculateInSampleSize(options, 420, 520);
            } else if (height >= 4880 && width <= 5500) {
                options.inSampleSize = calculateInSampleSize(options, 600, 480);
            } else if (width >= 4880 && height <= 5500) {
                options.inSampleSize = calculateInSampleSize(options, 480, 600);
            } else if (height >= 3840 && width <= 4880) {
                options.inSampleSize = calculateInSampleSize(options, 640, 520);
            } else if (width >= 3840 && height <= 4880) {
                options.inSampleSize = calculateInSampleSize(options, 520, 640);
            } else if (height >= 2880 && width <= 3840) {
                options.inSampleSize = calculateInSampleSize(options, 670, 560);
            } else if (width >= 2880 && height <= 3840) {
                options.inSampleSize = calculateInSampleSize(options, 560, 670);
            } else if (height >= 1920 && width <= 2880) {
                options.inSampleSize = calculateInSampleSize(options, 700, 600);
            } else if (width >= 1920 && height <= 2880) {
                options.inSampleSize = calculateInSampleSize(options, 600, 700);
            } else if (height >= 1080 && width <= 1920) {
                options.inSampleSize = calculateInSampleSize(options, 720, 620);
            } else if (width >= 1080 && height <= 1920) {
                options.inSampleSize = calculateInSampleSize(options, 620, 720);
            }
        }
        options.inJustDecodeBounds = false;
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(new File(filePath));
            bis = new BufferedInputStream(fis);
            if (bis != null) {
                try {
                    bitmap = BitmapFactory.decodeStream(bis, null, options);
                } catch (OutOfMemoryError error) {
                    if (bitmap != null && !bitmap.isRecycled()) {
                        bitmap.recycle();
                        bitmap = null;
                    }
                    System.gc();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                    bis = null;
                }
                if (fis != null) {
                    fis.close();
                    fis = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
     * 读取图片的旋转的角度
     */
    @SuppressLint("NewApi")
    public int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     */
    public Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
            bm = null;
            System.gc();
        }
        return returnBm;
    }


}
