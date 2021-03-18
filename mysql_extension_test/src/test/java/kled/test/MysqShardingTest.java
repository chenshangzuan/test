package kled.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import kled.test.entity.*;
import kled.test.mapper.CustomerAddrMapper;
import kled.test.mapper.CustomerMapper;
import kled.test.mapper.TravelrecordMapper;
import kled.test.service.CompanyService;
import kled.test.service.TravelrecordService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Kled
 * @version: MysqlMasterSlaveTest.java, v0.1 2020-10-04 20:22 Kled
 */
public class MysqShardingTest extends SpringTestISpringBootApplicationTests {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TravelrecordMapper travelrecordMapper;

    @Autowired
    private TravelrecordService travelrecordService;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerAddrMapper customerAddrMapper;

    @Test
    public void testShardingTable() {
        Travelrecord travelrecord = new Travelrecord();
        travelrecord.setName("travelrecord11");
        travelrecordMapper.insert(travelrecord);
        System.out.println(travelrecord.getId());
        //travelrecordService.save(travelrecord);
    }

    @Test
    public void testShardingChildTable() {
        //child table会和parent table 在同一个节点
        Customer customer = new Customer();
        customer.setName("customer");
        customerMapper.insert(customer);
        System.out.println(customer.getId());

        CustomerAddr customerAddr = new CustomerAddr();
        customerAddr.setName("customerAddr");
        customerAddr.setCustomerId(customer.getId());
        customerAddrMapper.insert(customerAddr);
        System.out.println(customerAddr.getId());
    }

    @Test
    public void queryShardingTable() {
        System.out.println(travelrecordMapper.selectList(Wrappers.<Travelrecord>lambdaQuery().eq(Travelrecord::getName, "travelrecord11")));
    }

    @Test
    public void pageTest(){
        Page<Travelrecord> page = new Page<>(1, 10);
        page.setOrders(Lists.newArrayList(OrderItem.desc("id")));
        QueryWrapper<Travelrecord> queryWrapper = new QueryWrapper<>();

        IPage<Travelrecord> travelrecordIPage = travelrecordMapper.selectPage(page, queryWrapper);
        System.out.println(travelrecordIPage.getRecords());
    }

    @Test
    public void testGlobalTable() {
        Company company = new Company();
        company.setName("company");
        companyService.saveOrUpdate(company);
    }
}
