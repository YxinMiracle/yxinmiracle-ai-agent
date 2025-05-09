package com.yxinmiracle.yxinmiracleaiagent;

import org.springframework.ai.autoconfigure.vectorstore.pgvector.PgVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { PgVectorStoreAutoConfiguration.class })
public class YxinmiracleAiAgentApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxinmiracleAiAgentApplication.class, args);
    }

}
