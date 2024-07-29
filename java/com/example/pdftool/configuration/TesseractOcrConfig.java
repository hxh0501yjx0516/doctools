package com.example.pdftool.configuration;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Jenson
 * @version 1.0
 */
@Configuration
public class TesseractOcrConfig {
    @Value("${tess4j.datapath}")
    private String dataPath;

    @Bean
    public Tesseract tesseract() {
        Tesseract tesseract = new Tesseract();
        // 设置训练数据文件夹路径
        tesseract.setDatapath(dataPath);
        // 设置为 中文简体 和 英文, +代表使用多语言识别
        tesseract.setLanguage("eng+chi_sim");
        return tesseract;
    }

    public  String analysisPdf(String filePath) throws IOException, TesseractException {
        PDDocument doc = PDDocument.load(new File(filePath));
        // 获取PDF文档中的文本内容
        String text = new PDFTextStripper().getText(doc);
        if (!StringUtils.hasText(text)) {
            // 可能是纯图pdf
//            log.info("----> 作为纯图pdf解析");
            StringBuilder stringBuilder = new StringBuilder();
            int pageNum = doc.getNumberOfPages();
            for (int i = 0; i < pageNum; i++) {
                PDPage page = doc.getPage(i);
                PDResources resources = page.getResources();
                Iterable<COSName> cosNameIterable = resources.getXObjectNames();
                for (COSName cosName : cosNameIterable) {
                    if (resources.isImageXObject(cosName)) {
                        PDImageXObject pdxObject = (PDImageXObject) resources.getXObject(cosName);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        ImageIO.write(pdxObject.getImage(), "PNG", byteArrayOutputStream);
                        // ocr识别文本
                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
                        // 对图片进行文字识别
                        String imageText = tesseract().doOCR(bufferedImage);
                        stringBuilder.append(imageText);

                        byteArrayOutputStream.close();
                        byteArrayInputStream.close();
                    }
                }
            }
            text = stringBuilder.toString();
        }

        // 输出文本内容
        return text;

    }


}
