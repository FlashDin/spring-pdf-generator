package com.example.pdfgenerator.utils;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import lombok.SneakyThrows;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfCreator {

    private final String FIRST_LAST_SQUAREBRACKET_PATTERN = "\\[(.*)\\]";
    private final String SQUAREBRACKET_PATTERN = "\\[([^\\[.]+?)\\]";
    private final String FIRST_LAST_ROUNDBRACKET_PATTERN = "\\((.*)\\)";

    @SneakyThrows
    public void createPdf(String source, String dest, Map<String, Object> data) {
        // File file = ResourceUtils.getFile("classpath:templates/" + source);
        // String str = Files.readString(file.toPath());
        // this.createPdfFromString(str, dest, data);
    }

    @SneakyThrows
    public void createPdfFromString(String html, String dest, Map<String, Object> data) {
        String str = html;
        Pattern pattern = Pattern.compile(this.FIRST_LAST_SQUAREBRACKET_PATTERN);
        // under [] "\\[([^\\[.]+?)\\]"
        // under html tag tr "(?s)<tr\\b.*?>(.*?)<\\/tr>"
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        while (matcher.find()) {
            String target = matcher.group(1);
            if (target.indexOf("@foreach") != -1) {
                Pattern pattern1 = Pattern.compile(FIRST_LAST_ROUNDBRACKET_PATTERN);
                Matcher matcher1 = pattern1.matcher(target);
                while (matcher1.find()) {
                    String[] target1 = matcher1.group(1).split(",");
                    List<Map<String, Object>> targetValue1 = (List<Map<String, Object>>) data.get(target1[0]);
                    if (!ObjectUtils.isEmpty(targetValue1) && !targetValue1.isEmpty()) {
                        for (Map<String, Object> t : targetValue1) {
                            String res = target1[1];
                            Pattern pattern2 = Pattern.compile(SQUAREBRACKET_PATTERN);
                            Matcher matcher2 = pattern2.matcher(res);
                            while (matcher2.find()) {
                                res = res.replaceAll("\\[(" + matcher2.group(1) + ")\\]", (String) t.get(matcher2.group(1)));
                            }
                            sb1.append(res);
                        }
                        matcher.appendReplacement(sb, sb1.toString());
                    }
                }
            } else {
                Object targetValue = data.get(target);
                if (!ObjectUtils.isEmpty(targetValue)) {
                    matcher.appendReplacement(sb, (String) targetValue);
                }
            }
        }
        matcher.appendTail(sb);
        HtmlConverter.convertToPdf(sb.toString(), new FileOutputStream(dest));
    }

    @SneakyThrows
    public ByteArrayOutputStream createPdfFromString(String html, Map<String, Object> data) {
        String str = html;
        Pattern pattern = Pattern.compile(this.FIRST_LAST_SQUAREBRACKET_PATTERN);
        // under [] "\\[([^\\[.]+?)\\]"
        // under html tag tr "(?s)<tr\\b.*?>(.*?)<\\/tr>"
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        StringBuffer sb1 = new StringBuffer();
        while (matcher.find()) {
            String target = matcher.group(1);
            if (target.indexOf("@foreach") != -1) {
                Pattern pattern1 = Pattern.compile(FIRST_LAST_ROUNDBRACKET_PATTERN);
                Matcher matcher1 = pattern1.matcher(target);
                while (matcher1.find()) {
                    String[] target1 = matcher1.group(1).split(",");
                    List<Map<String, Object>> targetValue1 = (List<Map<String, Object>>) data.get(target1[0]);
                    if (!ObjectUtils.isEmpty(targetValue1) && !targetValue1.isEmpty()) {
                        for (Map<String, Object> t : targetValue1) {
                            String res = target1[1];
                            Pattern pattern2 = Pattern.compile(SQUAREBRACKET_PATTERN);
                            Matcher matcher2 = pattern2.matcher(res);
                            while (matcher2.find()) {
                                res = res.replaceAll("\\[(" + matcher2.group(1) + ")\\]", (String) t.get(matcher2.group(1)));
                            }
                            sb1.append(res);
                        }
                        matcher.appendReplacement(sb, sb1.toString());
                    }
                }
            } else {
                Object targetValue = data.get(target);
                if (!ObjectUtils.isEmpty(targetValue)) {
                    matcher.appendReplacement(sb, (String) targetValue);
                }
            }
        }
        matcher.appendTail(sb);
        // HtmlConverter.convertToPdf(sb.toString(), new FileOutputStream(dest));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        ConverterProperties properties = new ConverterProperties();
        properties.setBaseUri("");
        HtmlConverter.convertToPdf(sb.toString(), pdfDoc, properties);
        return baos;
    }

}
