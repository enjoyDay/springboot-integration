package com.springbootIntegration.demo.quartz.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springbootIntegration.demo.common.RestResponse;
import com.springbootIntegration.demo.common.RestResult;
import com.springbootIntegration.demo.quartz.common.JobStatus;
import com.springbootIntegration.demo.quartz.entity.QuartzJobEntity;
import com.springbootIntegration.demo.quartz.mapper.JobMapper;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JobService{

    private static final String TRIGGER_IDENTITY = "trigger";

    @Autowired
    private Scheduler scheduler;
    @Resource
    private JobMapper jobMapper;

    public PageInfo listQuartzJob(String jobName, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<QuartzJobEntity> jobList = jobMapper.listJob(jobName);
        PageInfo pageInfo = new PageInfo(jobList);
        return pageInfo;
    }

    public RestResult saveJob(QuartzJobEntity quartz){
        try {
            schedulerJob(quartz);

            quartz.setTriggerState(JobStatus.RUNNING.getStatus());
            quartz.setOldJobGroup(quartz.getJobGroup());
            quartz.setOldJobName(quartz.getJobName());
            jobMapper.saveJob(quartz);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.responseError();
        }
        return RestResponse.responseOK();
    }

    public RestResult triggerJob(String jobName, String jobGroup) {
        JobKey key = new JobKey(jobName,jobGroup);
        try {
            scheduler.triggerJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            return RestResponse.responseError();
        }
        return RestResponse.responseOK();
    }

    public RestResult pauseJob(String jobName, String jobGroup) {
        JobKey key = new JobKey(jobName,jobGroup);
        try {
            scheduler.pauseJob(key);
            jobMapper.updateJobStatus(jobName, jobGroup, JobStatus.PAUSED.getStatus());
        } catch (SchedulerException e) {
            e.printStackTrace();
            return RestResponse.responseError();
        }
        return RestResponse.responseOK();
    }

    public RestResult resumeJob(String jobName, String jobGroup) {
        JobKey key = new JobKey(jobName,jobGroup);
        try {
            scheduler.resumeJob(key);
            jobMapper.updateJobStatus(jobName,jobGroup, JobStatus.RUNNING.getStatus());
        } catch (SchedulerException e) {
            e.printStackTrace();
            return RestResponse.responseError();
        }
        return RestResponse.responseOK();
    }

    public RestResult removeJob(String jobName, String jobGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_IDENTITY + jobName, jobGroup);
            scheduler.pauseTrigger(triggerKey);                                 // 停止触发器
            scheduler.unscheduleJob(triggerKey);                                // 移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));              // 删除任务
            jobMapper.removeQuartzJob(jobName, jobGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.responseError();
        }
        return RestResponse.responseOK();
    }

    public QuartzJobEntity getJob(String jobName, String jobGroup) {
        QuartzJobEntity job = jobMapper.getJob(jobName, jobGroup);
        return job;
    }

    public RestResult updateJob(QuartzJobEntity quartz) {
        try {

            scheduler.deleteJob(new JobKey(quartz.getOldJobName(),quartz.getOldJobGroup()));
            schedulerJob(quartz);

            quartz.setOldJobGroup(quartz.getJobGroup());
            quartz.setOldJobName(quartz.getJobName());
            jobMapper.updateJob(quartz);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.responseError();
        }
        return RestResponse.responseOK();
    }

    /**
     * 将job放放进日程表中
     * @param job
     * @throws Exception
     */
    public void schedulerJob(QuartzJobEntity job) throws Exception {
        //构建job信息
        Class cls = Class.forName(job.getJobClassName()) ;
        // cls.newInstance(); // 检验类是否存在
        JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(job.getJobName(),job.getJobGroup())
                .withDescription(job.getDescription()).build();

        // 触发时间点
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression().trim());
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(TRIGGER_IDENTITY + job.getJobName(), job.getJobGroup())
                .startNow().withSchedule(cronScheduleBuilder).build();
        //交由Scheduler安排触发
        scheduler.scheduleJob(jobDetail, trigger);
    }
}