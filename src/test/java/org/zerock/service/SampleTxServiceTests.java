package org.zerock.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@Log4j
public class SampleTxServiceTests {
	
	@Setter(onMethod_ = { @Autowired })
	private SampleTxService service;
	
	@Test
	public void testLong() { // 50bytes가 넘고 500bytes를 넘지 않는 어떤 문자열을 이용해서 tx_sample1, tx_sample2 테이블에 insert를 시도
		String str = "하얗게 피어난\r\n"
				+ "작은 꽃 하나가\r\n"
				+ "달가운 바람에\r\n"
				+ "얼굴을 내밀어"
				+ "아무말 못했던"
				+ "이름도 몰랐던"
				+ "지나간 날들에"
				+ "눈물이 흘러";
		log.info(str.getBytes().length);
		service.addData(str);
		//결과 : com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Data too long for column 'col2' at row 1
		//sample1에서는 정상적으로 insert, sample2에서는 너무 길어서 insert 실패
		//만약 트랜잭션 처리가 되었다면, 두 테이블 모두 인서트 실패
		//따라서 트랜잭션 처리를 할 수 있도록 Service의 addData()에 @Transactional 추가
		
		//@Transactional 추가 후에 동일 코드를 테스트하면 sample1에서도 인서트 실패
	}

}
