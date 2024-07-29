package com.example.pdftool.util;

import com.example.pdftool.configuration.TesseractOcrConfig;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
@Configuration
public class PdtToWordUtil {


    @Autowired
    TesseractOcrConfig config;

    //解析pdf
   public   String  analysisPdf(String filePath) throws IOException, TesseractException {

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
                        String imageText = config.tesseract().doOCR(bufferedImage);
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