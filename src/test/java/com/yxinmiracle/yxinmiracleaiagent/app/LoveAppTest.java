package com.yxinmiracle.yxinmiracleaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


/*
 * @author  YxinMiracle
 * @date  2025-05-01 16:14
 * @Gitee: https://gitee.com/yxinmiracle
 */

@SpringBootTest
class LoveAppTest {

    @Resource
    private LoveApp loveApp;

    @Test
    void doChat() {

        String chatId = UUID.randomUUID().toString();

        // 第一轮
        String message = "你好，我是YxinMiracle";
        String answer = loveApp.doChat(message, chatId);
        // 第二轮
        message = "我想让另一半（Yxin）更爱我";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
        // 第三轮
        message = "我的另一半叫什么来着？刚和你说过，帮我回忆一下";
        answer = loveApp.doChat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithReport() {
        String chatId = UUID.randomUUID().toString();

        // 第一轮
        String message = "你好，我是YxinMiracle, 我想让另一半（Yxin）更爱我，但是我不知道怎么做";
        LoveApp.LoveReport loveReport = loveApp.doChatWithReport(message, chatId);
        Assertions.assertNotNull(loveReport);
    }

    @Test
    void doChatWithRag() {
        String chatId = UUID.randomUUID().toString();

        // 第一轮
        String message = "我已经结婚了，婚后关系不太亲密怎么办？";
        String answer = loveApp.doChatWithVectorRag(message, chatId);
        Assertions.assertNotNull(answer);
    }

}