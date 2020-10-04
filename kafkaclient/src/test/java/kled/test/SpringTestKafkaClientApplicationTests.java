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

import kled.test.service.producer.MyKafkaSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests for {@link KafkaClientApplication}.
 * 
 * @author Dave Syer
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringTestKafkaClientApplicationTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private MyKafkaSender myKafkaSender;

	@Test
	public void test() throws Exception {
		myKafkaSender.sendMessage("test", "hello kafka");

		Thread.sleep(100000);
	}

}
