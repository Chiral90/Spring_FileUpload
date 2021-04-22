package org.zerock.domain;

import java.util.List;

import lombok.Data;

@Data
public class BoardVO {
	private int no;
	private String title;
	private String content;
	private String writer;
	//private Date regdate;
	//private Date updateDate;
	private String regdate;
	private String updateDate;
	
	//댓글의 개수를 의미하는 인스턴스 변수
	private int replyCnt;
	
	//등록 시 한 번에 BoardAttachVO를 처리할 수 있도록 List<BoardAttachVO>를 추가
	private List<BoardAttachVO> attachList;

}
