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

package test.kled;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * Tests for {@link ISpringBootApplication}.
 * 
 * @author Dave Syer
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpringTestISpringBootApplicationTests extends AbstractJUnit4SpringContextTests {

	@Autowired
	private RabbitTemplate directReplyRabbitTemplate;

	@Test
	public void testContextLoads() throws Exception {
//		Object reply = directReplyRabbitTemplate.convertSendAndReceive("test3.direct", "test3_direct_q", "test direct reply" + 11);
//		System.out.println(reply);
		CountDownLatch countDownLatch = new CountDownLatch(1);
		for (int i = 0; i < 20; i++) {
			Integer param = i;
			new Thread(() -> {
				try {
					countDownLatch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(param % 2 == 0){
					Object reply = directReplyRabbitTemplate.convertSendAndReceive("test3.direct", "test3_direct_q", "test direct reply" + 1);
					System.out.println("send:" + 1 + ", and reply =" + reply);
				}else {
					Object reply = directReplyRabbitTemplate.convertSendAndReceive("test3.direct", "test3_direct_q", "test direct reply" + 2);
					System.out.println("send:" + 2 + ", and reply =" + reply);
				}
			}).start();
		}
		countDownLatch.countDown();
		Thread.sleep(5000);
	}

}
