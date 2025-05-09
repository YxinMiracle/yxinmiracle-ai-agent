package com.yxinmiracle.yxinmiracleaiagent.rag;

/*
 * @author  YxinMiracle
 * @date  2025-05-03 22:42
 * @Gitee: https://gitee.com/yxinmiracle
 */

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.ai.transformer.KeywordMetadataEnricher;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MyKeywordEnricher {

    @Resource
    private ChatModel dashscopeChatModel;


    public List<Document> enrichDocuments(List<Document> documents) {
        // 实现文档关键字增强的逻辑
        KeywordMetadataEnricher keywordMetadataEnricher = new KeywordMetadataEnricher(dashscopeChatModel, 5);
        return keywordMetadataEnricher.apply(documents);
    }

}
