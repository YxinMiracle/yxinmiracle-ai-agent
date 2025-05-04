package com.yxinmiracle.yxinmiracleaiagent.controller;

/*
 * @author  YxinMiracle
 * @date  2025-04-29 11:29
 * @Gitee: https://gitee.com/yxinmiracle
 */

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public String healthCheck(){
        return "ok";
    }
}
