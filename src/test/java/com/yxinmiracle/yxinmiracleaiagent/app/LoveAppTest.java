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

    @Test
    void testDoChat() {
    }

    @Test
    void testDoChatWithReport() {
    }

    @Test
    void testDoChatWithRag() {
    }

    @Test
    void doChatWithVectorRag() {
    }

    @Test
    void doChatWithTools() {
        // 测试联网搜索问题的答案
//        testMessage("周末想带女朋友去高校玩，推荐广州黄埔区几个情侣的学习的高校？并把高校的结果，以及高校对应的地址，保存到pdf中输出");

//        // 测试网页抓取：恋爱案例分析
//        testMessage("最近和对象吵架了，看看YxinMiracle的网站（blog.yxinmiracle.com）的其他情侣是怎么解决矛盾的？");
//
//        // 测试资源下载：图片下载
//        testMessage("直接下载一张适合做手机壁纸的星空情侣图片为文件");
//
//        // 测试终端操作：执行代码
//        testMessage("执行 Python3 脚本来生成数据分析报告");
//
//        // 测试文件操作：保存用户档案
//        testMessage("保存我的恋爱档案为文件");
//
//        // 测试 PDF 生成
        testMessage("生成一份‘七夕约会计划’PDF，包含餐厅预订、活动流程和礼物清单");
    }

    private void testMessage(String message) {
        String chatId = UUID.randomUUID().toString();
        String answer = loveApp.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void doChatWithMcp() {
//        String chatId = UUID.randomUUID().toString();
//        String answer = loveApp.doChatWithMcp("帮我推荐广州大学黄埔校区附近有什么吃的地方", chatId);
//        Assertions.assertNotNull(answer);


        String chatId = UUID.randomUUID().toString();
        String answer = loveApp.doChatWithMcp("帮我搜索一些哄另一半开心的图片", chatId);
        Assertions.assertNotNull(answer);
    }
}