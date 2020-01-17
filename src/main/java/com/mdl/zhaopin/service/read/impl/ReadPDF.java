package com.mdl.zhaopin.service.read.impl;

import com.mdl.zhaopin.service.read.ReadFile;
import com.mdl.zhaopin.utils.RegexUtils;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadPDF implements ReadFile {

    @Override
    public String read(String Path) {
        String buffer = getTextFromPdf(Path);
        return RegexUtils.replaceBlank(buffer);
    }

    /**
     * @param filePath
     * @Title: getTextFromPdf
     * @Description: 读取pdf文件内容
     * @return: 读出的pdf的内容
     */
    public static String getTextFromPdf(String filePath) {
        String result = null;
        FileInputStream is = null;
        PDDocument document = null;
        try {
            is = new FileInputStream(filePath);
            PDFParser parser = new PDFParser(is);
            parser.parse();
            document = parser.getPDDocument();
            PDFTextStripper stripper = new PDFTextStripper();
            result = stripper.getText(document);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (document != null) {
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
