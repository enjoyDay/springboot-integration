package com.springbootIntegration.demo.quartz.myjob;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 自定义任务，实现QuartzJobBean
 */
public class MyJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyJob.class);

    // private boolean httpRequest = false;                        // 布尔类型的变量，不要加is前缀

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String url = "http://localhost:9001/quartz/rest/getEntity";

//        TestEntity1 entity = RestTemplateUtil.getRequest(url, TestEntity1.class);
//        LOGGER.info("entity = {}", entity);
        System.out.println("MyJob正在执行...");
    }
}
