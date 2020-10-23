package kled.test.service.impl;

import kled.test.entity.Customer;
import kled.test.mapper.CustomerMapper;
import kled.test.service.CustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kled
 * @since 2020-10-18
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
