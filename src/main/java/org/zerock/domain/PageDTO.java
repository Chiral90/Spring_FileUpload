package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int total;
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		
		this.cri = cri;
		this.total = total;
		
		// 10페이지 당 마지막페이지 (10, 20, ...) 20페이지씩으로 하려면 cri.getPageNum() / 10.0에 +1
		this.endPage = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10; 
		this.startPage = this.endPage - 9;
		
		int realEnd = (int) (Math.ceil((total * 1.0) / cri.getAmount()));
		
		if (realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1; // startPage가 1보다 크면 true (이전 페이지로 갈 수 있음)
		this.next = this.endPage < realEnd; // realEnd보다 endpage가 작으면 true (다음 페이지로 갈 수 있음)
	}

}
