package org.zerock.aop;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect // 해당 클래스의 객체가 Aspect를 구현했다는 표식
@Log4j
@Component // 스프링에서 빈으로 인식하기 위한 표식
public class LogAdvice {
	// @Before 어노테이션 적용
	// BeforeAdvice를 구현한 메서드에 추가
	// Advice 관련 어노테이션들은 내부적으로 Pointcut 지정 -> 별도로 @Pointcut 지정해서 사용 가능
	// 내부의 문자열은 AspectJ의 표현식. 'execution'의 경우 접근제한자와 특정 클래스의 메서드를 지정 가능
	// 맨 앞의 '*'은 접근제한자를 의미하고, 맨 뒤의 '*'은 클래스의 이름과 메서드 이름을 의미
	
	@Before( "execution(* org.zerock.service.SampleService*.*(..))" )
	public void logBefore() {
		log.info("==================");
	}
	
	// 'execution'으로 시작하는 Pointcut 설정에 doAdd() 메서드를 명시하고, 파라미터의 타입을 지정. '&& args (...' 부분에서는 변수명을 지정, 이 2종류의 정보를 이용해서 해당 메서드의 파라미터를 설정
	// '&& args'를 이용하는 설정은 파라미터가 다른 여러 종류의 메서드에 적용하는 데에는 간단하지 않다는 단점이 있다 -> @Around와 ProceedingJoinPoint를 이용해서 해결
	@Before( "execution(* org.zerock.service.SampleService*.doAdd(String, String)) && args(str1, str2)" )
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1 : " + str1);
		log.info("str2 : " + str2);
	}
	
	// @AfterThrowing은 'pointcut'과 'throwing' 속성을 지정하고 변수 이름을 'exception'으로 지정. 테스트 코드에서는 고의적으로 예외를 발생시켜서 확인
	@AfterThrowing(pointcut = "execution(* org.zerock.service.SampleService*.*(..))", throwing="exception")
	public void logException(Exception exception) {
		log.info("Exception....");
		log.info("exception : " + exception);
	}
	
	// @Around가 적용되는 메서드의 경우에는 리턴 타입이 void가 아닌 타입으로 설정하고, 메서드의 실행 결과 역시 직접 리턴하는 형태로 작성
	@Around("execution(* org.zerock.service.SampleService*.*(..))") // logTime()의 Pointcut 설정
	public Object logTime( ProceedingJoinPoint pjp ) { // ProceedingJoinPoint : AOP의 대상이 되는 Target이나 파라미터 등을 파악할 뿐 아니라, 직접 실행에 대한 결정도 가능
		long start = System.currentTimeMillis();
		
		log.info("Target : " + pjp.getTarget());
		log.info("Param : " + Arrays.toString(pjp.getArgs()));
		
		//invoke method
		Object result = null;
		
		try {
			result = pjp.proceed(); // ProceedingJoinPoint로 Pointcut에 연결된 메서드를 직접 실행
		} catch (Throwable e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		
		log.info("TIME : " + (end - start));
		
		return result;
	}

}
