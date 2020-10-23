package kled.test.service.impl;

import kled.test.entity.CustomerAddr;
import kled.test.mapper.CustomerAddrMapper;
import kled.test.service.CustomerAddrService;
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
public class CustomerAddrServiceImpl extends ServiceImpl<CustomerAddrMapper, CustomerAddr> implements CustomerAddrService {

}
