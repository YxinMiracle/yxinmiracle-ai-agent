package com.yxinmiracle.yxinmiracleaiagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
class FileOperationToolTest {

    @Test
    void readFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "yxinmiracle.txt";
        String fileContent = fileOperationTool.readFile(fileName);
        System.out.println(fileContent);
        Assertions.assertNotNull(fileContent);
    }

    @Test
    void writeFile() {
        FileOperationTool fileOperationTool = new FileOperationTool();
        String fileName = "yxinmiracle.txt";
        String fileContent = "hahhahahahahaha";
        String retContent = fileOperationTool.writeFile(fileName, fileContent);
        System.out.println(retContent);
    }
}