package com.example.pdfgenerator.controllers;

import com.example.pdfgenerator.utils.PdfCreator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

@RestController
@RequestMapping("/api/create-pdf")
public class CreatePdfController {

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generate(@RequestParam String id, @RequestParam String filename) {
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        PdfCreator pdfCreator = new PdfCreator();
        ByteArrayOutputStream baos = pdfCreator.createPdfFromString("<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title>Title</title>\n" +
                        "    <style>\n" +
                        "        table, th, td {\n" +
                        "            border: 1px solid;\n" +
                        "        }\n" +
                        "    </style>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<p>[name]</p>\n" +
                        "<p>[email]</p>\n" +
                        "<table>\n" +
                        "    <thead>\n" +
                        "        <tr>\n" +
                        "            <th>phones</th>\n" +
                        "            <th>address</th>\n" +
                        "        </tr>\n" +
                        "    </thead>\n" +
                        "    <tbody>\n" +
                        "    [rows]\n" +
                        "    </tbody>\n" +
                        "</table>\n" +
                        "</body>\n" +
                        "</html>",
                new HashMap<>(){{
                    put("name", "hehe");
                    put("email", "hehe@gmail.com");
                    put("rows", "<tr>" +
                            "<td>08777</>" +
                            "<td>jkl</>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>0877765655</>" +
                            "</tr>" +
                            "<tr>" +
                            "<td>0877765655</>" +
                            "</tr>");
                }});
        return ResponseEntity.ok()
                .headers(header)
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }

}
