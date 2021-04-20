package org.zerock.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {
		this(1, 20);
	}
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	//검색 조건을 처리하기 위해 검색 조건(type)과 검색에 사용하는 키워드가 필요, Criteria 확장할 필요가 있음 -> 상속 or 수정
	//BoardMapper.xml의 getListWithPaging에서 해당 배열을 동적 sql으로 처리
	public String[] getTypeArr() { // 검색 조건이 각 글자('T', 'C', 'W')로 구성. typeArr()를 이용해서 MyBatis의 동적 태그를 활용
		return type == null? new String[] {} : type.split("");
	}
	
	//UriComponentsBuilder : 웹페이지에서 매번 파라미터를 유지하는 일이 번거로울 때 사용하는 클래스. 여러 개의 파라미터들을 연결해서 url의 형태로 생성
	//url을 만들어주면 리다이렉트를 하거나, form 태그를 사용하는 상황을 줄여준다.
	//UriComponentsBuilder로 생성된 url은 화면에서도 유용하게 사용. 주로 js를 사용할 수 없는 상황에서 링크를 처리해야 하는 상황에서 사용
	public String getListLink() {
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
										.queryParam("pageNum", this.pageNum)
										.queryParam("amount", this.getAmount())
										.queryParam("type", this.getType())
										.queryParam("keyword", this.getKeyword());
		
		//get방식에 적합한 url 인코딩된 결과로 생성 (한글 처리에 신경 쓰지 않아도 된다)
		return builder.toUriString();
	}

}
