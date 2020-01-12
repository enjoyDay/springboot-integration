package com.springbootIntegration.demo.init;

import com.springbootIntegration.demo.quartz.common.JobStatus;
import com.springbootIntegration.demo.quartz.entity.QuartzJobEntity;
import com.springbootIntegration.demo.quartz.mapper.JobMapper;
import com.springbootIntegration.demo.quartz.service.JobService;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 在加载完所有资源后执行的方法
 */
@Component
public class ApplicationInit implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInit.class);

    @Resource
    private JobMapper jobMapper;
    @Autowired
    private JobService jobService;
    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        loadJobToQuartz();
    }

    private void loadJobToQuartz() throws Exception {
        LOGGER.info("quartz job load...");
        List<QuartzJobEntity> jobs = jobMapper.listJob("");
        for(QuartzJobEntity job : jobs) {
            jobService.schedulerJob(job);
            if (JobStatus.PAUSED.equals(job.getTriggerState())) {
                scheduler.pauseJob(new JobKey(job.getJobName(), job.getJobGroup()));
            }
        }
    }
}
