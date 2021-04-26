package org.zerock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.BoardMapper;
import org.zerock.mapper.ReplyMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
//@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	//tx 처리를 위해 BoardMapper 추가
	@Setter(onMethod_ = @Autowired)
	private BoardMapper boardMapper;
	
	//@Override
	@Transactional
	public int register(ReplyVO vo) {
		log.info("Reply register... " + vo);
		boardMapper.updateReplyCnt(vo.getBno(), 1); //댓글 등록 시 board_ex의 replyCnt 컬럼 업데이트
		return mapper.insert(vo);
	}
	
	public ReplyVO get(int rno) {
		log.info("Reply get.... " + rno);
		return mapper.read(rno);
	}
	
	public int modify(ReplyVO vo) {
		log.info("Reply modify.... " + vo);
		return mapper.update(vo);
	}
	
	@Transactional
	public int remove(int rno) {
		log.info("Reply remove.... " + rno);
		//boardMapper의 파라미터 입력을 위해 ReplyVO 인스턴스 생성
		ReplyVO vo = mapper.read(rno); // rno를 통해 bno를 확인
		boardMapper.updateReplyCnt(vo.getBno(), -1);
		return mapper.delete(rno);
	}
	
	public List<ReplyVO> getList(Criteria cri, int bno) {
		log.info("Reply list.... " + bno);
		return mapper.getListWithPaging(cri, bno);
	}
	
	public ReplyPageDTO getListPage(Criteria cri, int bno) {
		return new ReplyPageDTO(
				mapper.getCountByBno(bno),
				mapper.getListWithPaging(cri, bno)
				);
	}

}
