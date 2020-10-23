package kled.test.service.impl;

import kled.test.entity.Company;
import kled.test.mapper.CompanyMapper;
import kled.test.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kled
 * @since 2020-10-17
 */
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

}
