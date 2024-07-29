package com.example.pdftool.service.impl;

import com.example.pdftool.configuration.TesseractOcrConfig;
import com.example.pdftool.service.IPdfToWordService;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
@Service
public class PdfToWordService implements IPdfToWordService {

    @Autowired
    TesseractOcrConfig config;
    @Override
    public String analysisPdf(String filePath) throws IOException, TesseractException {


       return  config.analysisPdf(filePath);
    }
}
