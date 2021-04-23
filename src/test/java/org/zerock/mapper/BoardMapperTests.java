package org.zerock.mapper;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardMapperTests {
	@Setter(onMethod_ = @Autowired)
	//BoardMapper 인터페이스의 구현체를 주입받아서 동작
	private BoardMapper mapper;
	
	//@Test
	public void testGetList() {
		HashMap<String, Object> map = (HashMap) mapper.getList();
		System.out.println(map);
		//mapper.getList().forEach(board -> log.info(board));
	}
	
	//@Test
	public void testLastCnt() {
		int lastCnt = mapper.lastCnt();
		System.out.println(lastCnt);
	}
	//@Test
	public void testInsert() {
		BoardVO board = new BoardVO();
		board.setTitle("new title1");
		board.setContent("new content1");
		board.setWriter("newbie");
		//board.setRegdate("curdate()");
		//board.setUpdateDate("curdate()");
		
		mapper.insert(board);
		//Lombok이 만들어주는 toString()을 이용해서 no 멤버 변수의 값을 보기 위해
		log.info(board);
	}
	
	//@Test
	public void testRead() {
		//존재하는 게시물 번호로 테스트
		HashMap<String, Object> board = (HashMap) mapper.select(1);
		System.out.println(board);
		//BoardVO board = mapper.select(1);
		
		//log.info(board);
	}
	
	//@Test
	public void testDelete() {
		log.info("delete count : " + mapper.delete(2));
	}
	
	//@Test
	public void testUpdate() {
		
		BoardVO board = new BoardVO();
		// 실행 전 존재하는 번호인지 확인할 것
		board.setNo(2);
		board.setTitle("updated title");
		board.setContent("updated content");
		board.setWriter("updater");
		int bno = mapper.update(board);
		log.info("update count : " + bno);
	}
	
	//@Test
	public void testPaging() {
		Criteria cri = new Criteria();
		//10개씩 3페이지 테스트
		cri.setPageNum(3);
		cri.setAmount(10);
		HashMap<String, Object> map = (HashMap) mapper.getListWithPaging(cri);
		System.out.println(map);
		//List<BoardVO> list = mapper.getListWithPaging(cri);
		//list.forEach(board -> log.info(board));
	}
	
	//@Test
	public void testSearch() { // Criteria객체의 type과 keyword를 넣어서 원하는 sql이 생성되는지 확인하는 메서드. 실행 시 만들어지는 sql확인 필수
		
		Criteria cri = new Criteria();
		cri.setKeyword("service");
		cri.setType("TC");
		
		List<BoardVO> list = mapper.getListWithPaging(cri);
		list.forEach(board -> log.info(board));
	}
	
	//@Test
	public void testTotal() {
		Criteria cri = new Criteria();
		cri.setKeyword("service");
		cri.setType("TC");
		System.out.println("total count : " + mapper.totalCnt(cri));
	}
	
	@Test
	public void testInsertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("new title by insert select key");
		board.setContent("new content by insert select key");
		board.setWriter("tester");
		mapper.insertSelectKey(board);
		log.info("새로 등록될 글의 번호 : " + board.getNo());
	}

}
