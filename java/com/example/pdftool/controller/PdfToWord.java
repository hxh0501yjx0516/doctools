package com.example.pdftool.controller;

import java.io.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class PdfToWord {

    public static void main(String[] args) {
        try {
            String pdfFileString = "C:\\Users\\yjx\\Desktop\\jl.pdf";
            String docxFilePath = "C:\\Users\\yjx\\Desktop\\output.docx";
            PDDocument document = PDDocument.load(new FileInputStream(pdfFileString));

            // 创建Word文档
            XWPFDocument doc = new XWPFDocument();

            // 提取PDF文本内容
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            // 创建段落并添加文本内容
            XWPFParagraph paragraph = doc.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(text);

            // 保存Word文档
            FileOutputStream out = new FileOutputStream(docxFilePath);
            doc.write(out);
            out.close();

            // 关闭文档
            document.close();
            doc.close();

            System.out.println("PDF转Word成功！");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
