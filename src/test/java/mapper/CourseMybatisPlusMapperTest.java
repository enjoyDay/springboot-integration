package mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springbootIntegration.demo.Application;
import com.springbootIntegration.demo.entity.Course;
import com.springbootIntegration.demo.entity.User;
import com.springbootIntegration.demo.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liukun
 * @description
 * @date 2019/9/11
 */
@RunWith(SpringRunner.class)
//@SpringBootTest
@SpringBootTest(classes = Application.class)
public class CourseMybatisPlusMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    public void testSelect(){
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testUpdate(){
        System.out.println(("----- testUpdate method test ------"));
        User user = userMapper.selectById(1);
        user.setName("haha");
        UpdateWrapper<User> wrapper = new UpdateWrapper();
        wrapper.eq("id","1");
        int i = userMapper.update(user,wrapper);
        System.out.println("更新成功数量："+i);
    }

    @Test
    public void testSelectPage(){
        IPage<User> page = new Page<>();
        page.setCurrent(1);
        page.setSize(2);

        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("name","weiwei");
        IPage iPage = userMapper.selectPage(page, wrapper);
        //getRecorder是获取的总的对象数组
        System.out.println(iPage.getRecords());
    }
}

