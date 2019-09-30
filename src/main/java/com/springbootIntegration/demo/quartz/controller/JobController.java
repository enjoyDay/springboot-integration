package com.springbootIntegration.demo.quartz.controller;

import com.github.pagehelper.PageInfo;
import com.springbootIntegration.demo.common.RestResult;
import com.springbootIntegration.demo.quartz.entity.QuartzJobEntity;
import com.springbootIntegration.demo.quartz.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 关于任务的增删改查，暂停开始等
 */
@RestController
@RequestMapping("/job")
public class JobController {
	private final static Logger LOGGER = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/add")
	public RestResult save(QuartzJobEntity quartz){
		LOGGER.info("新增任务");
		RestResult result = jobService.saveJob(quartz);
		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/edit")
	public RestResult edit(QuartzJobEntity quartz){
		LOGGER.info("编辑任务");
		RestResult result = jobService.updateJob(quartz);
		return result;
	}
	@PostMapping("/list")
	public PageInfo list(String jobName, Integer pageNo, Integer pageSize){
		LOGGER.info("任务列表");
		PageInfo pageInfo = jobService.listQuartzJob(jobName, pageNo, pageSize);
		return pageInfo;
	}

	@PostMapping("/trigger")
	public  RestResult trigger(String jobName, String jobGroup) {
		LOGGER.info("触发任务");
		RestResult result = jobService.triggerJob(jobName, jobGroup);
		return result;
	}

	@PostMapping("/pause")
	public  RestResult pause(String jobName, String jobGroup) {
		LOGGER.info("停止任务");
		RestResult result = jobService.pauseJob(jobName, jobGroup);
		return result;
	}

	@PostMapping("/resume")
	public  RestResult resume(String jobName, String jobGroup) {
		LOGGER.info("恢复任务");
		RestResult result = jobService.resumeJob(jobName, jobGroup);
		return result;
	}

	@PostMapping("/remove")
	public  RestResult remove(String jobName, String jobGroup) {
		LOGGER.info("移除任务");
		RestResult result = jobService.removeJob(jobName, jobGroup);
        return result;
	}
}
