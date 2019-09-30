package com.springbootIntegration.demo.quartz.mapper;

import com.springbootIntegration.demo.quartz.entity.QuartzJobEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface JobMapper {

    List<QuartzJobEntity> listJob(@Param("jobName") String jobName);

    QuartzJobEntity getJob(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup);

    int saveJob(QuartzJobEntity job);

    int updateJobStatus(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup, @Param("status") String status);

    int removeQuartzJob(@Param("jobName") String jobName, @Param("jobGroup") String jobGroup);

    int updateJob(QuartzJobEntity quartz);
}
