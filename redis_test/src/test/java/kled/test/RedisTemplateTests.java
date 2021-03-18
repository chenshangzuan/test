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

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;

/**
 * Tests for {@link RedisSpringBootApplication}.
 * 
 * @author Dave Syer
 */
public class RedisTemplateTests extends SpringTestRedisSpringBootApplicationTests {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	@Test
	public void testValueOperations(){
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		valueOperations.append("test2", "hello redis");
//		System.out.println("test2=" + valueOperations.get("test2"));

		User user = new User();
		user.setName("11");
		valueOperations.set("user1", user);
		System.out.println("user1=" + valueOperations.get("user1"));
	}

	@Test
	public void testHashOperations(){
		HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
		User userVo = new User();
		userVo.setName("chen");
		hashOperations.put("hash:user",userVo.hashCode()+"",userVo);
		hashOperations.put("hash:user","123",456);
	}

	@Test
	public void testListOperations(){
		ListOperations<String, Object> listOperations = redisTemplate.opsForList();
		listOperations.leftPush("list", "value0");
		listOperations.leftPush("list", "value1");
		System.out.println(listOperations.rightPop("list"));
	}

	@Test
	public void testSetOperations(){
		SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
		setOperations.add("set", "valueX");
		setOperations.add("set", "valueX");
		setOperations.add("set", "valueY");
		System.out.println(setOperations.size("set"));
		System.out.println(setOperations.pop("set"));
	}

	@Test
	public void testZSetOperations(){
		ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
		zSetOperations.add("zset", "valueX", 1);
		zSetOperations.add("zset", "valueY", 2);
		zSetOperations.add("zset", "valueZ", 3);
		System.out.println(zSetOperations.rangeByScore("zset", 1,2));
	}

	@Test
	public void testTemplate(){
		redisTemplate.delete("test");
		System.out.println(redisTemplate.getExpire("test2"));
	}

	@Test
	public void testPubSub() throws InterruptedException {
		redisTemplate.convertAndSend("topic1", "hello redis pub/sub");

		Thread.sleep(5000);
	}

	public static class User{
		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "User{" +
					"name='" + name + '\'' +
					'}';
		}
	}

}
