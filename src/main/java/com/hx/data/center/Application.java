package com.hx.data.center;

import com.hx.data.center.util.SimpleUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Date;

/**
 * Copyright: Copyright (c) 2012-2018 chenchen. All rights reserved.
 *
 * @author chen chen
 * @version 1.0 创建时间：2018/2/27 18:59
 */

@MapperScan(value = "com.hx.data.center.mapper")
@SpringBootApplication
@ComponentScan(basePackages = {"com.hx.data.center"})
@EnableBatchProcessing(modular=true)
@Slf4j
public class Application {
    public static void main(String args[]) {
        try {
            ConfigurableApplicationContext applicationContext = SpringApplication.run(Application.class,args);
        Job job = (Job) applicationContext.getBean("generalCustNoJob");
            JobRegistry jobRegistry = applicationContext.getBean(JobRegistry.class);
//            Job  job = jobRegistry.getJob("generalCustNoJob");
            JobLauncher jobLauncher = applicationContext.getBean(JobLauncher.class);
            JobParametersBuilder parametersBuilder = new JobParametersBuilder();
            parametersBuilder.addDate("date",new Date());
            JobParameters jobParameters = parametersBuilder.toJobParameters();

            log.info("start job---"+ SimpleUtil.getDateString(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
            jobLauncher.run(job,jobParameters);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
