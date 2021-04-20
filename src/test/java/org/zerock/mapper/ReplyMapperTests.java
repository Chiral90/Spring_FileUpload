package org.zerock.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	//@Test
	public void testMapper() {
		log.info(mapper);
	}
	
	private int[] bnoArr = { 917484, 917483, 917482, 917481, 917480, 917479, 917478, 917477, 917476, 917475 };
	
	//@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 10).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			
			//게시물 번호
			vo.setBno(bnoArr[ i % 5 ]);
			vo.setReply("reply test " + i );
			vo.setReplyer("replyer " + i);
			
			mapper.insert(vo);
		});
	}
	
	//@Test
	public void testRead() {
		int targetRno = 4;
		
		ReplyVO vo = mapper.read(targetRno);
		
		log.info(vo);
	}
	
	//@Test
	public void testDelete() {
		int targetRno = 1;
		
		int result = mapper.delete(targetRno);
		
		log.info(result);
	}
	
	//@Test
	public void testUpdate() {
		int targetRno = 2;
		
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("reply update test");
		
		int count = mapper.update(vo);
		
		log.info("Update Count : " + count);
	}
	
	//@Test
	public void testReadReply() {
		
		Criteria cri = new Criteria();
		
		//917484번 게시물
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		
		replies.forEach(reply -> log.info(reply));
		
	}
	
	@Test
	public void testList2() {
		Criteria cri = new Criteria(1, 10);
		//917482번 게시물
		List<ReplyVO> replies = mapper.getListWithPaging(cri, 917482);
		
		replies.forEach(reply -> log.info(reply));
	}

}
