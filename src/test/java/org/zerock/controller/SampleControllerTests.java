package org.zerock.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration( {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
						"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class SampleControllerTests {
	
	@Setter(onMethod_ = { @Autowired })
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	/*
	@Test
	public void testConvert() throws Exception { // SampleController에 작성해 둔 convert() 메서드를 테스트
		
		Ticket ticket = new Ticket();
		ticket.setTno(123);
		ticket.setOwner("Admin");
		ticket.setGrade("AAA");
		
		String jsonStr = new Gson().toJson(ticket); // Java의 객체를 json 문자열로 변환하기 위해 사용
		
		log.info(jsonStr);
		
		mockMvc.perform(post("/sample/ticket")
				.contentType(MediaType.APPLICATION_JSON) // 전달하는 데이터가 무엇인지 명시
				.content(jsonStr))
				.andExpect(status().is(200));
	}
	*/
}
