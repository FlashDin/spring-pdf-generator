package com.example.pdfgenerator.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class PdfCreatorTest {

    private PdfCreator pdfCreatorUnderTest;

    @BeforeEach
    void setUp() {
        pdfCreatorUnderTest = new PdfCreator();
    }

    @Test
    void testCreatePdf() {
        // Setup
        // Run the test
        pdfCreatorUnderTest.createPdf("pdf0.html", "pdf/test0.pdf", new HashMap<>(){{
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

        // Verify the results
    }

    @Test
    void testCreatePdfWithForeach() {
        // Setup
        // Run the test
        pdfCreatorUnderTest.createPdf("pdf1.html", "pdf/test1.pdf", new HashMap<>(){{
            put("name", "hehe");
            put("email", "hehe@gmail.com");
            List<Map<String, Object>> ls = new ArrayList<>();
            ls.add(new HashMap<>(){{
                put("phone", "0857776");
                put("address", "pkl");
            }});
            ls.add(new HashMap<>(){{
                put("phone", "0857777");
                put("address", "mkl");
            }});
            ls.add(new HashMap<>(){{
                put("phone", "0857778");
                put("address", "jkl");
            }});
            put("rows", ls);
        }});

        // Verify the results
    }

    @Test
    void createPdfFromString() {
        // Setup
        // Run the test
        pdfCreatorUnderTest.createPdfFromString("<!DOCTYPE html>\n" +
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
                "pdf/test2.pdf",
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

        // Verify the results
    }
}
