package com.example.pdftool.service;

import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.IOException;

public interface IPdfToWordService {

        String  analysisPdf(String filePath) throws IOException, TesseractException;
}
