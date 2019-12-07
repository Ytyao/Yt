package com.yt.test;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yt.bean.User;
import com.yt.utils.StringUtils;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="classpath:applicationContext-redis.xml")
public class WeekTest {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Test
	public void weekDate() {
		ArrayList<User> list = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			User user = new User();
			user.setId(i+1);
			
			//随机中文名字
			String randomChinese = StringUtils.getRandomChinese(3);
			user.setName(randomChinese);
			
			//随机性别
			Random random = new Random();
			String sex = random.nextBoolean()?"男":"女";
			user.setSex(sex);
			
			//随机手机号
			String phone ="13"+StringUtils.getRandomNumber(9);
			user.setPhone(phone);
			
			//随机邮箱
			int shuzi =(int)(Math.random()*20);
			int len = shuzi<3?shuzi+3:shuzi;
			String randomStr = StringUtils.getRandomStr(len);
			String randomEmailSuffex = StringUtils.getRandomEmailSuffex();
			user.setEmail(randomStr+randomEmailSuffex);
			
			//随机生日 18-70
			String randomBirthday = StringUtils.randomBirthday();
			user.setBirthday(randomBirthday);
			
			list.add(user);
		}
		//JDK的序列化方式
/*		System.out.println("JDK的序列化方式");
		long start = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("jdk");
		boundListOps.leftPush(list);
		long end = System.currentTimeMillis();
		System.out.println("耗时"+(end - start)+"毫秒");*/
		
/*		System.out.println("JSON的序列化方式");
		long start = System.currentTimeMillis();
		BoundListOperations<String, Object> boundListOps = redisTemplate.boundListOps("json");
		boundListOps.leftPush(list);
		long end = System.currentTimeMillis();
		System.out.println("耗时"+(end - start)+"毫秒");*/
		
		System.out.println("hosh的序列化方式");
		long start = System.currentTimeMillis();
		BoundHashOperations<String, Object, Object> boundHashOps = redisTemplate.boundHashOps("hash");
		boundHashOps.put("hash", "list");
		long end = System.currentTimeMillis();
		System.out.println("耗时"+(end - start)+"毫秒");
	}
}
