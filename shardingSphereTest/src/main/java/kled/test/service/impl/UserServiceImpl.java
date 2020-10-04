package kled.test.service.impl;

import kled.test.entity.User;
import kled.test.mapper.UserMapper;
import kled.test.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kled
 * @since 2020-10-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
