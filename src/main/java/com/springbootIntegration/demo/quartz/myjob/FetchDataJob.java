package com.springbootIntegration.demo.quartz.myjob;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Random;
import java.util.stream.IntStream;

public class FetchDataJob extends QuartzJobBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetchDataJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        // TODO 业务处理
        //这里定时可以从数据库取数据，保存数据等
        Random random = new Random();
        IntStream intStream = random.ints(18, 100);
        int first = intStream.limit(1).findFirst().getAsInt();
        System.out.println("FetchDataJob向数据库中保存了数据..." + first);

//        int count = userMapper.saveUser(new User("zhangsan" + first, first));
//        if (count == 0) {
//            LOGGER.error("用户保存失败！");
//            return;
//        }
//        LOGGER.info("用户保存成功");
    }
}