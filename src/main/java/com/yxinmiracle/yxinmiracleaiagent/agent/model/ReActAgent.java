package com.yxinmiracle.yxinmiracleaiagent.agent.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * 实现了思考-行动的循环模式
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public abstract class ReActAgent extends BaseAgent {

    /**
     * 处理长钱状态并决定下一步的行动
     * @return
     */
    public abstract boolean think();

    /**
     * 执行think中需要调用的工具
     */
    public abstract String act();

    /**
     * 执行单个步骤，思考和行动
     * @return
     */
    @Override
    public String step() {
        try {
            boolean shouldAct = think();
            if (!shouldAct) {
                return "思考完成 - 无需行动";
            }
            return act();
        }catch (Exception e){
            e.printStackTrace();
            return "执行行动时出错" + e.getMessage();
        }
    }
}
