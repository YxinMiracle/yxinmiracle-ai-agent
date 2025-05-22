package com.yxinmiracle.yxinmiracleaiagent.agent.model;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.SettingUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态管理,用于管理代理的状态和执行的流程
 * 事件循环
 */

@Data
@Slf4j
public abstract class BaseAgent {

    // 核心属性
    private String name;

    // 提示词
    private String systemPrompt;

    private String nextStepPrompt;

    // 代理状态
    private AgentState state = AgentState.IDLE;

    // 执行步骤控制
    private int currentStep = 0;
    private int maxStep = 10;


    // LLM大模型
    private ChatClient chatClient;

    // Memory 记忆 （需要自主维护会话上下文）
    private List<Message> messageList = new ArrayList<>();

    /**
     * 运行智能体
     * @return
     */
    public String run(String userPrompt){
        if (this.state != AgentState.IDLE) {
            throw new RuntimeException("Cannot run agent from state " + this.state);
        }
        if (StrUtil.isBlank(userPrompt)) {
            throw new RuntimeException("user prompt is empty");
        }
        // 进入执行
        this.systemPrompt = userPrompt;
        // 记录消息上下文
        messageList.add(new UserMessage(userPrompt));
        // 保存结果列表
        List<String> resultList = new ArrayList<>();
        try {
            for (int i = 0; i < maxStep && this.state != AgentState.FINISHED; i++) {
                int stepNumber = i+1;
                currentStep = stepNumber;
                log.info("Executing step{}/{}", stepNumber, maxStep);

                String stepResult = step();
                String result = "step " + stepNumber + ": " + stepResult;
                resultList.add(result);
            }

            if (currentStep >= maxStep){
                state = AgentState.FINISHED;
                resultList.add("Terminated: Reached max steps (" + maxStep + ")");
            }
            return String.join("\n", resultList);
        }catch (Exception e){
            state = AgentState.ERROR;
            log.error("run agent error", e);
            return "执行错误" + e.getMessage();
        }finally {
            // 清理
            cleanup();
        }

    }

    /**
     * 定义单个步骤，交给子类实现
     * @return
     */
    public abstract String step();

    protected void cleanup(){
        // 子类可以重写子方法进行清理
    }


}
