package org.zerock.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

//웹을 개발할 때 매번 url을 테스트하기 위해 tomcat과 같은 was를 실행하는 불편한 단계를 생략하기 위해 기존과 다르게 진행 - tomcat 실행 없이 테스트
@RunWith(SpringJUnit4ClassRunner.class)

//Test for Controller
@WebAppConfiguration // Servlet의 ServletContext를 이용하기 위해 사용하는 어노테이션. spring에서 WebApplicationContext를 이용하기 위한 목적.

@ContextConfiguration({
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@Log4j
public class BoardControllerTests {
	@Setter(onMethod_ = {@Autowired})
	private WebApplicationContext ctx;
	
	private MockMvc mockMvc;
	
	@Before //JUnit의 @Before 사용. @Before가 적용된 메서드는 모든 테스트 전에 매번 실행되는 메서드로 지정
	public void setup() {
		//MockMvc는 '가짜 mvc'. 임의의 url, parameter를 브라우저에서 사용하는 것처럼 만들어 Controller 실행.
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}
	
	//@Test
	public void testListPaging() throws Exception {
		//System.out.println("ControllerTest Start");
		log.info(
				//MockMvcRequestBuilders가 GET방식의 호출. Controller의 list()에서 반환된 결과로 Model에 담겨진 데이터 확인
				mockMvc.perform(MockMvcRequestBuilders.get("/board/list")
						.param("pageNum", "3")
						.param("amount", "10"))
				.andReturn().getModelAndView().getModelMap()
				);
		//System.out.println("ControllerTest End");
	}
	
	//@Test
	public void testList() throws Exception {
		log.info(
				//MockMvcRequestBuilders가 GET방식의 호출. Controller의 list()에서 반환된 결과로 Model에 담겨진 데이터 확인
				mockMvc.perform(MockMvcRequestBuilders.get("/board/list")).andReturn().getModelAndView().getModelMap()
				);
	}
	
	//@Test
	public void testGetNewestNo() throws Exception {
		
	}
	@Test
	public void testInsert() throws Exception {
		//MockMvcRequestBuilders.post를 이용해서 POST 방식으로 데이터 전달, param()을 이용해서 전달하는 파라미터 지정 (input tag와 유사 기능)
		String resultPage = mockMvc.perform(MockMvcRequestBuilders
				.post("/board/insert")
				.param("title", "inserted by Controller")
				.param("content", "inserted by Controller")
				.param("writer", "Controller")
				/*
				.param("uuid", "test uuid")
				.param("uploadPath", "test path")
				.param("fileName", "test filename")
				.param("fileType", "false")
				*/
				).andReturn().getModelAndView().getViewName();
		
		log.info(resultPage);
	}
	//result : INFO : org.zerock.controller.BoardControllerTests - redirect:/board/list : Controller에서 리턴까지 정상적으로 완료
	
	//@Test
	public void testSelect() throws Exception {
		log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/select")
				.param("no", "1"))
				.andReturn()
				.getModelAndView().getModelMap());
	}
	//@Test
	public void testUpdate() throws Exception {
		String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/update")
				.param("no", "3")
				.param("title", "updated by Controller")
				.param("content", "updated by Controller")
				.param("writer", "Controller"))
				.andReturn()
				.getModelAndView().getViewName();
		log.info(resultPage);
	}
	//@Test
		public void testDelete() throws Exception {
		//삭제 전 db에 게시물 번호 확인할 것
			String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/delete")
					.param("no", "4"))
					.andReturn()
					.getModelAndView().getViewName();
			log.info(resultPage);
	}

}
