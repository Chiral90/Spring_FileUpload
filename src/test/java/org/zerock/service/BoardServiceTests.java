package org.zerock.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTests {
	
	@Setter(onMethod_ = { @Autowired })
	private BoardService service;
	
	//BoardService 객체가 제대로 주입이 가능한지 확인하는 테스트 소스
	//@Test
	public void testExist() {
		log.info(service);
		assertNotNull(service);
	}
	//insert test code
	//@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("service insert");
		board.setContent("service content");
		board.setWriter("service");
		
		service.insert(board);
		log.info("새로 생성된 게시물의 번호 : " + service.lastCnt());
	}
	
	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("service insert");
		board.setContent("service content");
		board.setWriter("service");
		service.insertSelectKey(board);
		BoardAttachVO vo = new BoardAttachVO();
		vo.setUuid("test uuid");
		vo.setFileName("test filename");
		vo.setUploadPath("test path");
		vo.setFileType(false);
		vo.setBno(board.getNo());
		//service.insertSelectKey(board);
		log.info("새로 생성된 게시물의 번호 : " + board.getNo());
		log.info("첨부파일에 주입된 글 번호 : " + vo.getBno());
		log.info("첨부파일 정보 : " + vo);
		//log.info("lastCnt로 받은 최신 글 번호 : " + service.lastCnt());
	}
	
	//select * test code
	//@Test
	public void testGetList() {
		//service.getList().forEach(board -> log.info(board));
		service.getList(new Criteria(3, 10)).forEach(board -> log.info(board));
	}
	//select * where no=?
	//@Test
	public void testSelect() {
		
		log.info(service.select(1));
	}
	
	//삭제/수정 구현과 테스트 : 엄격하게 처리하기 위해 Boolean 타입으로 처리
	//update where no=?
	//@Test
	public void testUpdate() {
		BoardVO board = service.select(1).get(0); //특정한 게시물을 먼저 조회
		if (board == null) { // 게시물 없으면 패스
			return;
		}
		board.setTitle("updated title by BoardServiceTests");
		board.setContent("updated content by BoardServiceTests");
		log.info("UPDATE RESULT : " + service.update(board));
	}
	//@Test
	public void testDelete() {
		//게시물 번호의 존재 여부를 확인하고 테스트 할 것
		log.info("DELETE RESULT : " + service.delete(1));
	}
	

}
/**
BoardService 객체가 제대로 주입이 가능한지 확인하는 작업
정상적으로 BoardService 객체가 생성되고 BoardMapper가 주입되었을 때의 BoardService 객체와 데이터베이스 관련 로그
INFO : org.zerock.service.BoardServiceTests - org.zerock.service.BoardServiceImpl@44040454
INFO : org.springframework.context.support.GenericApplicationContext - Closing org.springframework.context.support.GenericApplicationContext@6ddf90b0: startup date [Fri Apr 09 14:09:25 KST 2021]; root of context hierarchy
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
*/
