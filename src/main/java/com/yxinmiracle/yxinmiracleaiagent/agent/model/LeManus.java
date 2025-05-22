package com.yxinmiracle.yxinmiracleaiagent.agent.model;

import com.yxinmiracle.yxinmiracleaiagent.advisor.MyLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Component;

@Component
public class LeManus extends ToolCallAgent{


    public LeManus(ToolCallback[] allTools, ChatModel dashScopeChatModel) {
        super(allTools);
        this.setName("leManus");
        String SYSTEM_PROMPT = """  
                You are Le-Manus, an all-capable AI assistant, aimed at solving any task presented by the user.  
                You have various tools at your disposal that you can call upon to efficiently complete complex requests.  
                """;
        this.setSystemPrompt(SYSTEM_PROMPT);
        String NEXT_STEP_PROMPT = """  
                Based on user needs, proactively select the most appropriate tool or combination of tools.  
                For complex tasks, you can break down the problem and use different tools step by step to solve it.  
                After using each tool, clearly explain the execution results and suggest the next steps.  
                If you want to stop the interaction at any point, use the `terminate` tool/function call.  
                """;
        this.setNextStepPrompt(NEXT_STEP_PROMPT);
        this.setMaxStep(20);

        ChatClient chatClient = ChatClient.builder(dashScopeChatModel)
                .defaultAdvisors(new MyLoggerAdvisor()).build();
        this.setChatClient(chatClient);

    }
}
