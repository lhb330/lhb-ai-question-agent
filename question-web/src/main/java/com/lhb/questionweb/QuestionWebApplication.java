package com.lhb.questionweb;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@Slf4j
@EnableAsync
@SpringBootApplication(scanBasePackages = "com.lhb")
@MapperScan("com.lhb.mapper")
public class QuestionWebApplication implements CommandLineRunner {

    @Value("${server.port}")
    private String port;

    public static void main(String[] args) {
        SpringApplication.run(QuestionWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("服务已启动，健康检查：http://localhost:{}/api/health",port);
    }
}

