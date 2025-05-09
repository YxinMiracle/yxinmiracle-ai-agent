package com.yxinmiracle.yxinmiracleaiagent.demo.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.rag.Query;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MultiQueryExpanderDemoTest {

    @Resource
    private MultiQueryExpanderDemo queryExpander;

    @Test
    void expand() {
        List<Query> expand = queryExpander.expand("谁是YxinMiracle啊哈哈哈哈哈，请回答我吼吼吼吼吼哦吼？");
        Assertions.assertNotNull(expand);
    }
}