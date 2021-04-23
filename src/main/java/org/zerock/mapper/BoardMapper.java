package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
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
	public void insertSelectKey(BoardVO board);
	
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
	
	//replyCnt를 업데이트하는 메서드를 추가
	//해당 게시물 번호와 증가나 감소를 의미하는 amount 변수에 파라미터를 받을 수 있도록 처리
	//댓글이 등록되면 1이 증가하고, 댓글이 삭제되면 1이 감소
	//MyBatis의 SQL을 처리하기 위해 기본적으로 하나의 파라미터 타입을 사용하기 때문에 bno, amount를 동시에 전달하려면 @Param 어노테이션을 이용해서 처리
	//댓글이 추가되면 반정규화된 board_ex에 replyCnt컬럼이 업데이트 되어야 하므로 BoardMapper.xml에 updateReplyCnt구문을 추가한다
	public void updateReplyCnt(@Param("bno") int bno, @Param("amount") int amount);
		
}
