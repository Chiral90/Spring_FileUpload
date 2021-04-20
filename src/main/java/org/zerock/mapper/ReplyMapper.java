package org.zerock.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyMapper {
	
	public int insert(ReplyVO vo);
	
	public ReplyVO read(int rno);
	
	public int delete(int rno);
	
	public int update(ReplyVO vo);
	
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			//bno는 mapper.xml의 #{bno}와 매치
			@Param("bno") int bno);
	
	public int getCountByBno (int bno);
}
