package com.example.pdftool.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
@RequestMapping()
@RestController
public class PdfToWordOcr {
    @GetMapping(value = "hh")
    public void xx(){
//        String pdfFilePath = "C:\\Users\\yjx\\Desktop\\wdsm.pdf";
        String pdfFilePath = "C:\\Users\\yjx\\Desktop\\jl.pdf";
//        String pdfFilePath = "C:\\Users\\yjx\\Desktop\\sfz.jpg";
        String outputWordFilePath = "C:\\Users\\yjx\\Desktop\\wdsm.docx";
//        String tessDataPath = "/Users/huxh/Downloads/tessdata";
        String tessDataPath = "C:\\Tesseract-OCR\\tessdata";
        try {
            // 使用PDFBox库读取PDF文件
            PDDocument document = PDDocument.load(new File(pdfFilePath));
            PDFRenderer renderer = new PDFRenderer(document);

            // 读取PDF文件的第一页作为图片
            BufferedImage image = renderer.renderImageWithDPI(0, 1000, ImageType.RGB);


            // 使用Tesseract库进行OCR识别
            Tesseract tesseract = new Tesseract();
            tesseract.setLanguage("chi_sim");
            tesseract.setDatapath(tessDataPath);
            String text = tesseract.doOCR(image);


            // 使用Apache POI库创建Word文档并写入文字内容
            XWPFDocument doc = new XWPFDocument();
            XWPFParagraph para = doc.createParagraph();
            XWPFRun run = para.createRun();
            run.setText(text);
            doc.write(new FileOutputStream(outputWordFilePath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "jpg")
    public void jpg() {


        try {
            Tesseract tesseract = new Tesseract();
            tesseract.setDatapath("C:\\Tesseract-OCR\\tessdata"); // 设置tessdata路径
//            tesseract.setLanguage("chi_sim");
            String result = tesseract.doOCR(new File("C:\\Users\\yjx\\Desktop\\sfz2.jpg"));
            System.out.println(result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}