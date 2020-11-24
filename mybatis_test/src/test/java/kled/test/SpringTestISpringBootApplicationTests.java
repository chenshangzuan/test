/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kled.test;

import com.github.pagehelper.PageHelper;
import kled.test.common.dal.dataobject.UserDO;
import kled.test.common.dal.dataobject.UserDOExample;
import kled.test.common.dal.mapper.auto.UserDOMapper;
import net.bytebuddy.asm.Advice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

/**
 * Tests for {@link ISpringBootApplication}.
 * 
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringTestISpringBootApplicationTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private UserDOMapper userDOMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Test
	public void testInsert() throws Exception {
		UserDO userDO = new UserDO();
		userDO.setName("kled");
		userDO.setAge(1);
		userDOMapper.insertSelective(userDO);
	}

	@Test
	public void testUpdate() throws Exception {
		UserDO userDO = new UserDO();
		userDO.setId(1);
		userDO.setName("kled");
		userDO.setAge(10);
		userDOMapper.updateByPrimaryKey(userDO);
	}

	@Test
	public void testPage() throws Exception {
		PageHelper.startPage(1,5);
		List<UserDO> userDO = userDOMapper.selectByExample(new UserDOExample());
		System.out.println(userDO);
	}

	@Test
	public void testTransaction() throws Exception {
		transactionTemplate.execute(new TransactionCallback<Void>() {
			@Override
			public Void doInTransaction(TransactionStatus transactionStatus) {
				UserDO userDO1 = new UserDO();
				userDO1.setName("kled");
				userDO1.setAge(2);
				userDOMapper.insertSelective(userDO1);

				UserDO userDO2 = new UserDO();
				userDO2.setName("kled");
				userDO2.setAge(3);
				userDOMapper.insertSelective(userDO2);
				throw new NullPointerException();
			}
		});
	}
}
