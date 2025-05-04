package com.yxinmiracle.yxinmiracleaiagent.demo.invoke;

/*
 * @author  YxinMiracle
 * @date  2025-05-01 9:45
 * @Gitee: https://gitee.com/yxinmiracle
 */

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SpringAiAiInvoke implements CommandLineRunner {

    @Resource
    private ChatModel dashscopeChatModel;


    @Override
    public void run(String... args) throws Exception {
        AssistantMessage assistantMessage = dashscopeChatModel.call(new Prompt("你好，我是YxinMiracle")).getResult().getOutput();
        System.out.println(assistantMessage.getText());
    }
}
