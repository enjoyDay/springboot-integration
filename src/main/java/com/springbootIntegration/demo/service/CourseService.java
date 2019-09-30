package com.springbootIntegration.demo.service;

import com.springbootIntegration.demo.entity.Course;
import com.springbootIntegration.demo.mapper.CourseMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liukun
 * @description
 * @date 2019/9/25
 */
@Service
public class CourseService {
    @Resource
    private CourseMapper courseMapper;

    public List<Course> getCourseList(){
        return courseMapper.selectList(null);
    }
}
