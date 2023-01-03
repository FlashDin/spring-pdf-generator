package com.example.pdfgenerator.utils;

import com.itextpdf.html2pdf.HtmlConverter;
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

    @SneakyThrows
    public void createPdf(String source, String dest, Map<String, Object> data) {
        File file = ResourceUtils.getFile("classpath:templates/" + source);
        String str = Files.readString(file.toPath());
        Pattern pattern = Pattern.compile("\\[(.*)\\]");
        // under [] "\\[([^\\[.]+?)\\]"
        // under html tag tr "(?s)<tr\\b.*?>(.*?)<\\/tr>"
        Matcher matcher = pattern.matcher(str);
        StringBuilder sb = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        while (matcher.find()) {
            String target = matcher.group(1);
            if (target.indexOf("@foreach") != -1) {
                Pattern pattern1 = Pattern.compile("\\((.*)\\)");
                Matcher matcher1 = pattern1.matcher(target);
                while (matcher1.find()) {
                    String[] target1 = matcher1.group(1).split(",");
                    List<Map<String, Object>> targetValue1 = (List<Map<String, Object>>) data.get(target1[0]);
                    if (!ObjectUtils.isEmpty(targetValue1) && !targetValue1.isEmpty()) {
                        Pattern pattern2 = Pattern.compile("\\[([^\\[.]+?)\\]");
                        Matcher matcher2 = pattern2.matcher(target1[1]);
                        while (matcher2.find()) {
                            // targetValue1.forEach(t -> {
                            //     sb1.append(matcher2.replaceFirst((String) t.get(matcher2.group(1))));
                            // });
                            // System.out.println("HEHE");
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
    public void createPdfFromString(String html, String dest, Map<String, Object> data) {
        String str = html;
        Pattern pattern = Pattern.compile("\\[(.*)\\]");
        Matcher matcher = pattern.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String target = matcher.group(1);
            Object targetValue = data.get(target);
            if (!ObjectUtils.isEmpty(targetValue)) {
                matcher.appendReplacement(sb, (String) targetValue);
            }
        }
        matcher.appendTail(sb);
        HtmlConverter.convertToPdf(sb.toString(), new FileOutputStream(dest));
    }

}
