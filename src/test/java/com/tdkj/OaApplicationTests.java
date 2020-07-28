package com.tdkj;

import com.tdkj.System.utils.DateUtil;
import com.tdkj.System.utils.EmailUtils;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;

@SpringBootTest
class OaApplicationTests {

	@Test
	void contextLoads()throws Exception {
	 int a=EmailUtils.sandEmail("smtp.qq.com","2331438941@qq.com","kddktudsocxdebdg","houxuyang0801@163.com","","","测试邮件","测试标题");
		System.out.println(a);
	}



	@Test
	void contextLoads222()throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String data1 = DateUtil.getToday()+" 09:06:00";
		System.out.println(DateUtil.isbefore(data1));
	}


	@Test
	public void contextLoadsActiviti() {
		ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
	}














}
