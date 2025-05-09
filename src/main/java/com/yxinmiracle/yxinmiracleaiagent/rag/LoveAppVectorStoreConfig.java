package com.yxinmiracle.yxinmiracleaiagent.rag;

/*
 * @author  YxinMiracle
 * @date  2025-05-03 23:05
 * @Gitee: https://gitee.com/yxinmiracle
 */

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoveAppVectorStoreConfig {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Resource
    private MyTokenTextSplitter myTokenTextSplitter;

    @Resource
    private MyKeywordEnricher  myKeywordEnricher;

    @Bean
    VectorStore loveAppVectorStore(EmbeddingModel dashscopeEmbeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(dashscopeEmbeddingModel).build();
        // 加载文档
        List<Document> documentList = loveAppDocumentLoader.loadMarkdowns();
        // 自主切分
//        List<Document> splitDocuments = myTokenTextSplitter.splitDocuments(documentList);
        List<Document> enrichDocuments = myKeywordEnricher.enrichDocuments(documentList);
        simpleVectorStore.add(enrichDocuments);
        return simpleVectorStore;
    };

}
