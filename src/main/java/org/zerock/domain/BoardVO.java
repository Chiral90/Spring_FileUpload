package org.zerock.domain;

import java.sql.Date;

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

}
