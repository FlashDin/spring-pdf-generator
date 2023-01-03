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
        while (matcher.find()) {
            String target = matcher.group(1);
            if (target.indexOf("@foreach") != -1) {
                // Pattern pattern1 = Pattern.compile("\\((.*)\\)");
                // Matcher matcher1 = pattern1.matcher(target);
                // StringBuilder sb1 = new StringBuilder();
                // while (matcher1.find()) {
                //     String target1 = matcher1.group(1);
                //     String[] target1s = target1.split(",");
                //     List<Map<String, Object>> targetValue1 = (List<Map<String, Object>>) data.get(target1s[0]);
                //     System.out.println(target1s[0] + target1s[1]);
                //     targetValue1.forEach(t -> {
                //         if (!ObjectUtils.isEmpty(targetValue1)) {
                //             matcher1.appendReplacement(sb1, (String) t.get(target1s[1]));
                //         }
                //     });
                // }
                // System.out.println(sb1.toString());
                // System.out.println(target);
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

}
