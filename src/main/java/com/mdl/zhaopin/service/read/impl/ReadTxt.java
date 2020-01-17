package com.mdl.zhaopin.service.read.impl;

import com.mdl.zhaopin.service.read.ReadFile;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

@Slf4j
public class ReadTxt implements ReadFile {

    @Override
    public String read(String filePath) {
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
                log.info("找不到指定的文件,{}", filePath);
            }
        } catch (Exception e) {
            log.info("读取文件内容出错,{}", filePath);
        }

        return result;
    }

}
