package org.zerock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class ReplyServiceImpl implements ReplyService {
	
	//@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
	//@Override
	public int register(ReplyVO vo) {
		log.info("Reply register... " + vo);
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
	
	public int remove(int rno) {
		log.info("Reply remove.... " + rno);
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
