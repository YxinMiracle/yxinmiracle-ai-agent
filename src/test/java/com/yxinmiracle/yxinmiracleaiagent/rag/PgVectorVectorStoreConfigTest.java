package com.yxinmiracle.yxinmiracleaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PgVectorVectorStoreConfigTest {

    @Resource
    private VectorStore pgVectorVectorStore;

    @Test
    void pgVectorVectorStore() {

        List<Document> documents = List.of(
                new Document("YxinMiracle 做的是威胁情报信息抽取与利用的方向", Map.of("meta1", "meta1")),
                new Document("YxinMiracle 现在是研究生二年级", Map.of("meta2", "meta2")),
                new Document("YxinMiracle 这小伙子比较帅气", Map.of("meta2", "meta2")));
        // 添加文档
        pgVectorVectorStore.add(documents);
        // 相似度查询
        List<Document> results = pgVectorVectorStore.similaritySearch(SearchRequest.builder().query("YxinMiracle是做什么方向的").topK(5).build());
        Assertions.assertNotNull(results);
    }
}