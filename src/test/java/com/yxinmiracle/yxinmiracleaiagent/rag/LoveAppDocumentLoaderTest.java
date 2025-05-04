package com.yxinmiracle.yxinmiracleaiagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


/*
 * @author  YxinMiracle
 * @date  2025-05-03 22:58
 * @Gitee: https://gitee.com/yxinmiracle
 */

@SpringBootTest
class LoveAppDocumentLoaderTest {

    @Resource
    private LoveAppDocumentLoader loveAppDocumentLoader;

    @Test
    void loadMarkdowns() {
        loveAppDocumentLoader.loadMarkdowns();
    }
}