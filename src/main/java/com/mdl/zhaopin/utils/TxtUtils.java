package com.mdl.zhaopin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TxtUtils {

    public static ArrayList<String> getFile(String pathname) {
        File file = new File(pathname);
        File[] arr = file.listFiles();
        ArrayList<String> list = new ArrayList<>();

        for (File f : arr) {
            list.add(f.toString());
        }
        return list;
    }

    public static String read(String filePath) {
        String result = null;
        try {
            String encoding = "utf-8";
            File file = new File(filePath);
            // 判断文件是否存在
            if (file.isFile() && file.exists()) {
                // 考虑到编码格式
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                result = "";
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    result += lineTxt;
                }
                read.close();

            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return result;
    }

}
