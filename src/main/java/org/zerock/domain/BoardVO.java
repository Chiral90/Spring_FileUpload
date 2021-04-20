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

}
