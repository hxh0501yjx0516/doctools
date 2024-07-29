package com.example.pdftool.controller;

import com.example.pdftool.service.IPdfToWordService;
import com.example.pdftool.util.PdtToWordUtil;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static java.awt.SystemColor.text;

@RestController
@RequestMapping(value = "")
public class PdfToWordController {
    @Autowired
    IPdfToWordService pdfToWordService;

    @GetMapping(value = "go")
    public String go() {
        String str = "/Users/huxh/Desktop/文档扫描.pdf";
        String text;
        try {
            text = pdfToWordService.analysisPdf(str);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TesseractException e) {
            throw new RuntimeException(e);
        }
        return text;

    }
}
