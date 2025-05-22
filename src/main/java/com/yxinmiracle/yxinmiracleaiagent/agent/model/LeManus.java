package com.yxinmiracle.yxinmiracleaiagent.agent.model;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

@Component
public class LeManus extends ToolCallAgent{




    public LeManus(ToolCallback[] allTools, ChatModel dashScopeChatModel) {
        super(allTools);
    }
}
