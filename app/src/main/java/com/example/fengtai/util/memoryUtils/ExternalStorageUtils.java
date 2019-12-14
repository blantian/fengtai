package com.example.fengtai.util.memoryUtils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/*
=========================
      外部存储工具类
=========================
 */
public class ExternalStorageUtils {

    private static final String TAG="Case";
    /**
     * 判断外部存储的空间是否可用
     * @return 空间是否可用
     */
    public static boolean isExternalStorageUse(){
        boolean bl=false;
        //Environment 描述当前设备是否可用的类
        //根据Environment中的静态方法getExternalStorageState获取外部存储状态与Environment中的可读写的常量状态
        //比较  如果一致 说明外部存储的状态是可用
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            bl=true;
        }
        return bl;
    }
    /**
     * 根据文件路径、名称与数据内容 写入到外部存储的指定的目录中
     * @param type  表示当前存储的外部文件夹
     * @param fileName  表示外部存储的文件名称
     * @param content  表示数据内容的字节数组   文字 图片 音视频等...
     * @return  是否存储成功
     */
    public static boolean writeExternalStoragePublic(String type,String fileName,byte[] content){
        boolean bl=false;
        if(isExternalStorageUse()){//判断sdcard是否能够使用
            if(hasExternalStoragePublicDri(type)){//判断外部存储的pictures是否存在
                try {
                    File parentFile=Environment.getExternalStoragePublicDirectory(type);
                    File file=new File(parentFile, fileName);//构建外部存储的file对象
                    FileOutputStream outputStream=new FileOutputStream(file);
                    outputStream.write(content);
                    bl=true;
                    outputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Log.i(TAG, "当前文件夹不存在!");
            }
        }else{
            Log.i(TAG, "外部存储设备不可使用");
        }
        return bl;
    }
    /**
     * 根据文件夹的类型判断外部存储的公共文件夹是否存在
     * @param type 文件夹的类型
     * @return  是否存在
     */
    public static boolean hasExternalStoragePublicDri(String type){
        boolean bl=false;
        File file=Environment.getExternalStoragePublicDirectory(type);
        //判断外部存储的公共路径下的dowloads文件夹是否存在
        if(file!=null && file.exists()){
            bl=true;
        }
        return bl;
    }
    /**
     * 根据指定的文件夹、名称读取指定文件夹下的文件
     * @param type  文件夹
     * @param FileName  文件名称
     * @return  读取的字节数组
     */
    public static byte[] readExternalStoragePublic(String type,String FileName){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        if(isExternalStorageUse()){//外部存储路径是否可用
            if(hasExternalStoragePublicDri(type)){//读取的文件夹是否存在
                try {
                    File parentFile=Environment.getExternalStoragePublicDirectory(type);
                    File file=new File(parentFile, FileName);
                    FileInputStream inputStream=new FileInputStream(file);
                    int temp=0;
                    byte[] buff=new byte[1024];
                    while((temp=inputStream.read(buff))!=-1){
                        outputStream.write(buff, 0, temp);
                        outputStream.flush();
                    }
                    //Log.i(TAG, outputStream.toString());
                    inputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Log.i(TAG, "当前文件夹不存在!");
            }
        }else{
            Log.i(type, "外部存储设备不可用!");
        }
        return outputStream.toByteArray();
    }
    /**
     * 根据指定的文件夹、文件名称、数据内容写入外部存储的私有部分
     * @param type   文件夹名称
     * @param context  上下文
     * @param fileName  文件名
     * @param content  内容字节数组
     * @return  是否存储成功
     *
     * getExternalFilesDir的type为null时 存储到 sdcard/Android/data/应用程序包名/files/文件名
     * 如果type为指定的文件夹   sdcard/Android/data/应用程序包名/files/文件夹名称/文件名
     */
    public static boolean writeExternalStoragePrivate(String type, Context context, String fileName, byte[] content){
        boolean bl=false;
        if(isExternalStorageUse()){
            try {
                //创建外部存储的私有文件对象
                File parentFile=context.getExternalFilesDir(type);
                File file=new File(parentFile, fileName);
                //字节流输出
                FileOutputStream outputStream=new FileOutputStream(file);
                outputStream.write(content);
                outputStream.close();
                bl=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(TAG, "外部存储设备不可用");
        }
        return bl;
    }

    /**
     * 根据指定的文件夹、文件名称读取外部存储的私有文件
     * @param type  文件夹
     * @param fileName  文件名称
     * @param context  上下文
     * @return  读取的字节
     */
    public static byte[] readExternalStoragePrivate(String type,String fileName,Context context){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        if(isExternalStorageUse()){
            try {
                File file=new File(context.getExternalFilesDir(type), fileName);
                FileInputStream inputStream=new FileInputStream(file);
                int temp=0;
                byte[] buff=new byte[1024];
                while((temp=inputStream.read(buff))!=-1){
                    outputStream.write(buff, 0, temp);
                    outputStream.flush();
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(TAG, "外部存储设备不可用!");
        }
        return outputStream.toByteArray();
    }

    /**
     * 向外部存储的根目录sdcrad下写入文件
     * @param fileName 文件名
     * @param content  内容
     * @return  是否写入成功
     */
    public static boolean writeExternalStorageSdcardRoot(String fileName,byte[] content){
        boolean bl=false;
        if(isExternalStorageUse()){
            try {
                //构建外部存储sdacrd的文件对象
                File file=new File(Environment.getExternalStorageDirectory(), fileName);
                FileOutputStream outputStream=new FileOutputStream(file);
                outputStream.write(content);
                outputStream.close();
                bl=true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(TAG, "外部存储不可用!");
        }
        return bl;
    }
    /**
     * 从sdcard的根目录读取文件
     * @param fileName  文件名
     * @return  字节数组
     */
    public static byte[] readExternalStorageSdcardRoot(String fileName){
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        if(isExternalStorageUse()){
            try {
                File file=new File(Environment.getExternalStorageDirectory(), fileName);
                FileInputStream inputStream=new FileInputStream(file);
                int temp=0;
                byte[] buff=new byte[1024];
                while((temp=inputStream.read(buff))!=-1){
                    outputStream.write(buff, 0, temp);
                    outputStream.flush();
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(TAG, "外部存储不可用!");
        }
        return outputStream.toByteArray();
    }

    /**
     * 根据指定的file删除指定的文件
     * @param file 删除的file对象
     * @return  删除是否成功
     */
    public static boolean deleteExternalStorageFile(File file){
        boolean bl=false;
        if(file!=null && file.exists()){
            bl=file.delete();
        }
        return bl;
    }
    /**
     * 获取sdcard的总空间
     * @return sdcard的总空间
     *
     * android.os.StatFs;  该对象主要用来描述存储设备的状态 状态就包括当前参数指定的路径的设备的
     * 总空间以及可用空间  并且内部存储的空间也可以使用这个对象获取
     *
     * byte 字节   1byte=8位   bit 字位
     * 1B=8b   1byte=8bit
     *   kb 千字节
     * 1KB=1024B
     * 1MB=1024KB  MB 兆字节
     * 1GB=1024MB  GB 吉字节
     */
    public static long getTotalSpace(File file){
        long size=0;
        if(isExternalStorageUse()){
            //创建测量设备空间的对象
            StatFs statFs=new StatFs(file.getAbsolutePath());
            if(Build.VERSION.SDK_INT>=18){
                size=file.getTotalSpace();
                //api >18 size=statFs.getTotalSpace()
            }else{
                size=statFs.getBlockCount()*statFs.getBlockSize();//计算当前的路径下存在的字节数*每个字节所占的空间
            }
        }else{
            size=0;
            Log.i(TAG, "设备不可用!");
        }
        return size/1024/1024;
    }
    /**
     * 获取指定file的可用空间
     * @param file 需要获取可用空间的file
     * @return  可用空间的大小
     */
    public static long getFreeSpace(File file){
        long size=0;
        if(isExternalStorageUse()){
            StatFs statFs=new StatFs(file.getAbsolutePath());
            if(Build.VERSION.SDK_INT>=18){
                size=file.getTotalSpace();
                //statFs.getAvailableBytes();
            }else{
                //获得可用空间的块*可用空间的块数  statFs.getAvailableBlocks()获取手机的文件操作系统中的有效可用的块
                size=statFs.getFreeBlocks()*statFs.getBlockSize();
            }
        }else{
            size=0;
        }
        return size/1024/1024;
    }

}
