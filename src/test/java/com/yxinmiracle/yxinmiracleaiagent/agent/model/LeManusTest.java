package com.yxinmiracle.yxinmiracleaiagent.agent.model;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


/*
 * @author  YxinMiracle
 * @date  2025-05-22 10:04
 * @Gitee: https://gitee.com/yxinmiracle
 */

@SpringBootTest
class LeManusTest {

    @Resource
    private LeManus leManus;

    @Test
    public void run(){
        String userPrompt = """
                我的另一半居住再上海静安区，请帮我找到 5 公里内合适的约会地点，
                并结合一些网络图片，制定一份详细的约会计划。
                并以 PDF 格式输出
                """;
        String answer = leManus.run(userPrompt);
        Assertions.assertNotNull(answer);

    }

}