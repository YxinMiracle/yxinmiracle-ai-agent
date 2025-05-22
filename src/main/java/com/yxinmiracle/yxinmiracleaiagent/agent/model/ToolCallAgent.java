package com.yxinmiracle.yxinmiracleaiagent.agent.model;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallback;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理工具调用的基础代理类，具体实现了think和act方法，可以用作创建实例的父类
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class ToolCallAgent extends ReActAgent {

    private final ToolCallback[] availableTools;

    // 保存工具调用信息的响应结果 （要调用哪些工具）
    private ChatResponse toolCallChatResponse;

    // 工具调用管理者
    private final ToolCallingManager toolCallingManager;

    // 禁用 Sping AI 内置的工具调用机制，自己维护工具调用和上下文
    private final ChatOptions chatOptions;

    public ToolCallAgent(ToolCallback[] availableTools) {
        super();
        this.availableTools = availableTools;
        this.toolCallingManager = ToolCallingManager.builder()
                .build();
        // 禁用 Sping AI 内置的工具调用机制，自己维护工具调用和上下文
        this.chatOptions = DashScopeChatOptions.builder().withProxyToolCalls(true).build();
    }

    /**
     * 处理当前状态并执行当前行动
     * @return
     */
    @Override
    public boolean think() {
        // 校验提示词，拼接用户提示词
        try {
            if (StrUtil.isNotBlank(this.getNextStepPrompt())){
                UserMessage userMessage = new UserMessage(getNextStepPrompt());
                this.getMessageList().add(userMessage);
            }
            List<Message> messageList = getMessageList();
            Prompt prompt = new Prompt(messageList, this.chatOptions);
            // 调用AI大模型，获取工具调用结果
            ChatResponse chatResponse = getChatClient().prompt(prompt).system(getSystemPrompt()).tools(availableTools).call().chatResponse();
            // 记录响应，用于后续 Act
            this.toolCallChatResponse = chatResponse;
            AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
            List<AssistantMessage.ToolCall> toolCallList = assistantMessage.getToolCalls();
            String result = assistantMessage.getText();
            log.info(getName() + "的思考，"+result);
            log.info(getName() + "选择了 " + toolCallList.size() + " 个工具来使用");
            String toolCallInfo = toolCallList.stream()
                    .map(toolCall ->
                            String.format("工具名称：%s, 工具参数：%s", toolCall.name(), toolCall.arguments())
                    ).collect(Collectors.joining("\n"));
            log.info(toolCallInfo);
            if (toolCallList.isEmpty()){
                // 只有不调用工具时，才需要手动记录助手消息
                getMessageList().add(assistantMessage);
                return false;
            }else {
                return true;
            }
        } catch (Exception e) {
            log.error(getName() + "的思考过程遇到的问题：" + e.getMessage());
            getMessageList().add(new AssistantMessage("思考过程遇到了错误："+e.getMessage()));
            return false;
        }
    }

    @Override
    public String act() {
        if (!toolCallChatResponse.hasToolCalls()){
            return "没有工具需要调用";
        }
        Prompt prompt = new Prompt(getMessageList(), this.chatOptions);
        // 调用工具
        ToolExecutionResult toolExecutionResult = toolCallingManager.executeToolCalls(prompt, toolCallChatResponse);
        // 记录消息上下文
        setMessageList(toolExecutionResult.conversationHistory());
        ToolResponseMessage toolResponseMessage = (ToolResponseMessage) CollUtil.getLast(toolExecutionResult.conversationHistory());
        boolean terminateToolCalled = toolResponseMessage.getResponses().stream().anyMatch(response -> response.name().equals("doTerminate"));
        if (terminateToolCalled){
            setState(AgentState.FINISHED);
        }
        String result = toolResponseMessage.getResponses().stream().map(response -> "工具" + response.name() + " 返回的结果是：" + response.responseData()).collect(Collectors.joining("\n"));
        log.info(result);
        return result;
    }
}
