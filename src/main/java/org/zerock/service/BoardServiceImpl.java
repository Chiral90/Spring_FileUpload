package org.zerock.service;
//BoardService 인터페이스를 구현하는 구현체
//고객 요구사항 구현(logic부)하는 Business 영역

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
//비즈니스 계층의 인터페이스와 구현 클래스가 작성되었다면, 이를 스프링의 빈으로 인식하기 위해서 root-context.xml에
//@Service 어노테이션이 있는 org.zerock.service 패키지를 스캔(조사)하도록 추가해야한다.
//root-context.xml namespace에 context 부분 추가 후 <context:component-scan base-package="org.zerock.service"> 작성
@Log4j
//계층 구조상 비즈니스 영역을 담당하는 객체임을 표시. 패키지를 읽어 들이는 동안 처리된다.
@Service // root-context.xml에서 service인식하게 해주는 표식
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {
	//spring 4.3 이상에서 자동 처리 - 단일 파라미터를 받는 생성자의 경우에는 필요한 파라미터를 자동으로 주입 가능
	//@AllArgsConstructor는 모든 파라미터를 이용하는 생성자를 만들기 때문에 실제 코드는 BoardMapper를 주입받는 생성자가 만들어진다
	
	// BoardServiceImpl이 정상적으로 동작하기 위해서는 BoardMapper 객체가 필요 - @Autowired와 같이 직접 설정하거나 @Setter를 이용해서 처리 가능
	@Setter(onMethod_ = @Autowired) // BoardService객체가 BoardMapper를 주입 받는 코드
	private BoardMapper mapper;
	
	@Override
	public void insert(BoardVO board) {
		//등록 작업의 구현과 테스트 (p204)
		//log.info("register........." + board);
		
		//mapper.insertSelectKey(board);
		log.info("insert......." + board);
		mapper.insert(board);
	}
	
	public int lastCnt() {
		log.info("get the newest number....");
		return mapper.lastCnt();
	}
	
	public List<BoardVO> select(int no) {
		log.info(no);
		log.info("select......" + no);
		return mapper.select(no);
	}
	
	//삭제/수정 구현과 테스트 : 엄격하게 처리하기 위해 Boolean 타입으로 처리
	//정상적인 수정과 삭제가 이루어지면 1이 반환, '==' 연산자를 이용해 true/false 처리
	public boolean update(BoardVO board) {
		log.info("update...." + board);
		
		return mapper.update(board) == 1;
	}
	
	public boolean delete(int no) {
		log.info("remove...." + no);
		return mapper.delete(no) == 1;
	}
	
	//페이징 적용 시 getList가 Criteria를 인수로 받아와야 함
	public List<BoardVO> getList(Criteria cri) {
		log.info("getList with criteria (paging....) : " + cri);
		return mapper.getListWithPaging(cri);
	}
	/*
	public List<BoardVO> getList() {
		log.info("getList.....");
		return mapper.getList();
	}
	*/
	
	// 목록과 전체 데이터 개수는 항상 같이 동작하는 경우가 많으므로 Criteria를 파라미터로 지정
	public int totalCnt(Criteria cri) {
		log.info("totalCnt : " + cri);
		return mapper.totalCnt(cri);
	}
	
}
