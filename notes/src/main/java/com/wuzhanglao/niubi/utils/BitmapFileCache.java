package com.wuzhanglao.niubi.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

public class BitmapFileCache {

    public static final String PATH = Environment.getExternalStorageDirectory() + "/images";
    private File rootDir = new File(PATH);

    private BitmapFileCache() {
        // 如果Bitmap缓存文件夹不存在的话，就创建这个文件夹
        if (!rootDir.exists()) {
            rootDir.mkdir();
        }
    }

    private static final class SingletonHolder {
        private static final BitmapFileCache instance = new BitmapFileCache();
    }

    public static BitmapFileCache getInstance() {
        return SingletonHolder.instance;
    }

    public void put(int hashCode, Bitmap bitmap) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(rootDir, hashCode + ".jpg"));
            bitmap.compress(CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件名获取Bitmap
     **/
    public Bitmap get(int hashCode, int defaultImgId) {
        String[] files = rootDir.list(new MyFilenameFilter(hashCode));
        if (files == null || files.length == 0) {
            return null;
        } else {
            final Bitmap bitmap = BitmapFactory.decodeFile(PATH + "/" + files[0]);
            if (bitmap != null) {
                return bitmap;
            } else {
                return BitmapFactory.decodeResource(MyApplication.getInstance().getResources(), defaultImgId);
            }
        }
    }

    public Bitmap get(int hashCode) {
        String[] files = rootDir.list(new MyFilenameFilter(hashCode));
        if (files == null || files.length == 0) {
            return null;
        } else {
            final Bitmap bitmap = BitmapFactory.decodeFile(PATH + "/" + files[0]);
            if (bitmap != null) {
                return bitmap;
            } else {
                return null;
            }
        }
    }

    /**
     * 文件名过滤器，把想要的文件过滤出来
     **/
    private class MyFilenameFilter implements FilenameFilter {

        private int hashCode;

        public MyFilenameFilter(int hashCode) {
            this.hashCode = hashCode;
        }

        @Override
        public boolean accept(File dir, String filename) {
            return filename.equals(hashCode + ".jpg");
        }

    }
}
