package com.mdl.zhaopin.service.read.impl;

import com.mdl.zhaopin.service.read.ReadFile;
import com.mdl.zhaopin.utils.RegexUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Slf4j
public class ReadWord implements ReadFile {

    @Override
    public String read(String Path) {

        String buffer = "";
        try {
            if (Path.endsWith(".doc")) {
                InputStream is = new FileInputStream(new File(Path));
                WordExtractor extractor = new WordExtractor(is);
                buffer = extractor.getText();
                extractor.close();
            } else if (Path.endsWith("docx")) {
                InputStream is = new FileInputStream(Path);
                XWPFDocument doc = new XWPFDocument(is);
                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
                buffer = extractor.getText();
                extractor.close();
            } else {
                log.info("此文件不是word文件！", Path);
            }

        } catch (Exception e) {
            log.error("此文件不是word文件！", e);
        }

        return RegexUtils.replaceBlank(buffer);
    }


}
