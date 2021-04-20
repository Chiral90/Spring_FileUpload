package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Log4j
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class SampleServiceTests {
	
	@Setter(onMethod_ = @Autowired)
	private SampleService service;
	
	// Proxy 객체가 정상적으로 만들어져 있는지 테스트하는 코드
	//@Test
	public void testClass() {
		log.info(service);
		log.info(service.getClass().getName());
	}
	
	//SampleServiceImpl의 doAdd() 테스트
	@Test
	public void testAdd() throws Exception {
		log.info(service.doAdd("123", "456"));
	}
	
	//@Test
	public void testAddError() throws Exception {
		log.info(service.doAdd("123", "ABC")); //doAdd()는 숫자로 변환이 가능한 문자열을 파라미터로 지정해야 함 - 고의적으로 발생시킨 에러
		/* 실행 결과 로그
		INFO : org.zerock.aop.LogAdvice - str1 : 123
		INFO : org.zerock.aop.LogAdvice - str2 : ABC
		INFO : org.zerock.aop.LogAdvice - Exception....
		INFO : org.zerock.aop.LogAdvice - exception : java.lang.NumberFormatException: For input string: "ABC"*/
	}

}
