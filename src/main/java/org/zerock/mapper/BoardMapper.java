package org.zerock.mapper;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
//controller, service에서 mapper interface의 메서드를 호출. java에서 xml을 직접 호출할 수 없음. controller, service와 xml을 연결
public interface BoardMapper {
	//@Select 사용 : xml 필요 x
	//@Select("select * from board_ex where no > 0 order by no desc")
	public List<BoardVO> getList();
	
	//p189 영속 영역의 CRUD 구현
	//insert
	public void insert(BoardVO board);
	
	public int lastCnt();
	
	//select(read)
	public List<BoardVO> select(int no);
	//public BoardVO select(int no);
	
	//페이징 - Criteria 타입을 파라미터로 사용하는 메서드
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	//delete
	public int delete(int no);
	
	//update
	public int update(BoardVO board);
	
	//total count
	//Criteria는 검색에서 필요
	public int totalCnt(Criteria cri);
		
}
