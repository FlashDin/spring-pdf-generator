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
            // List<Map<String, Object>> ls = new ArrayList<>();
            // ls.add(new HashMap<>(){{
            //     put("phone", "0857776");
            // }});
            // ls.add(new HashMap<>(){{
            //     put("phone", "0857777");
            // }});
            // ls.add(new HashMap<>(){{
            //     put("phone", "0857778");
            // }});
            // put("phones", ls);
        }});

        // Verify the results
    }
}
