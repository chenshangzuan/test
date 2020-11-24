package kled.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kled.test.entity.User;
import kled.test.mapper.UserMapper;
import kled.test.service.UserService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Kled
 * @version: MpTest.java, v0.1 2020-10-04 17:53 Kled
 */
public class MpTest extends SpringTestISpringBootApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Test
    public void insertTest(){
        User user = new User();
        user.setName("amanda");
        userMapper.insert(user);
    }

    @Test
    public void saveTest(){
        User user1 = new User();
        user1.setName("lisa");
        userService.saveOrUpdate(user1);
    }

    @Test
    public void saveBatchTest(){
        User user1 = new User();
        user1.setName("lina");

        User user2 = new User();
        user2.setName("tommy");
        userService.saveBatch(Lists.newArrayList(user1, user2));
    }

    @Test
    public void updateTest(){
        User user = new User();
        user.setId(1);
        user.setAge(29);
        userMapper.updateById(user);
    }

    @Test
    public void updateNullTest(){
        LambdaUpdateWrapper<User> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.set(User::getAge, null);
        lambdaUpdateWrapper.eq(User::getId, 1);

        userMapper.update(null, Wrappers.<User>lambdaUpdate().set(User::getAge, null).eq(User::getId, 1));
    }

    @Test
    public void queryTest(){
        System.out.println(userMapper.selectById(1));
    }

    @Test
    public void queryWrapperTest(){
        System.out.println(userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getName, "tommy")));
    }

    @Test
    public void pageTest(){
        Page<User> page = new Page<>(1, 5);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        System.out.println(userIPage.toString());
    }
}
