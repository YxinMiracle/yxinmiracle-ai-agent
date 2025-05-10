package com.yxinmiracle.yxinmiracleaiagent.tools;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
public class PDFGenerationToolTest {

    @Test
    public void testGeneratePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "yxinmiracle.pdf";
        String content = "YxinMiracle https://blog.yxinmiracle.com";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}
