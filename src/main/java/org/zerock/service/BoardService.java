package org.zerock.service;
// 각 계층 간의 연결
// 인터페이스를 이용한 연결 : 느슨한(loose) 연결(결합)

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

public interface BoardService {
	// 메서드 설계 시 메서드 이름은 현실적인 로직의 이름을 붙여준다

	public void insert(BoardVO board); // 추상 메서드
	
	public int lastCnt(); //
	
	public List<BoardVO> select(int no); // 추상 메서드
	//public BoardVO select(int no);
	
	public boolean update(BoardVO board); // 추상 메서드
	
	public boolean delete(int no); // 추상 메서드
	
	//페이징 적용 시 getList가 Criteria를 인수로 받아와야 함
	//public List<BoardVO> getList();
	public List<BoardVO> getList(Criteria cri);
	
	//total count
	public int totalCnt(Criteria cri);

}
