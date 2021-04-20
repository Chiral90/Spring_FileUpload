package org.zerock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.SampleVO;
import org.zerock.domain.Ticket;

import lombok.extern.log4j.Log4j;

// 기존의 @Controller는 문자열을 반환하는 경우에 jsp 파일의 이름으로 처리하지만 @RestController는 순수한 데이터의 문자열을 반환한다
@RestController
@RequestMapping("/sample")
@Log4j
public class SampleController {
	
	// produces 속성은 해당 메서드가 생산하는 MIME 타입을 의미. 문자열로 직접 지정할 수도 있고, 메서드 내의 MediaType 클래스를 이용 가능
	@GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
	public String getText() {
		log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
		
		return "Hello";
		
	}
	
	// SampleVO를 리턴하는 메서드
	@GetMapping(value="/getSample", produces= { MediaType.APPLICATION_JSON_UTF8_VALUE, // Spring 5.2부터 APPLICATION_JSON_VALUE로 사용
												MediaType.APPLICATION_XML_VALUE })
	public SampleVO getSample() {
		return new SampleVO(112, "star", "lord");
	
	}
	
	@GetMapping(value="/getList")
	public List<SampleVO> getList() {
		// 내부적으로 1부터 10미만까지의 루프를 처리하면서 SampleVO 객체를 생성해서 List<SampleVO>에 추가
		return IntStream.range(1, 10).mapToObj(i -> new SampleVO(i, i + "First", i + " Last")).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/getMap")
	public Map<String, SampleVO> getMap() {
		Map<String, SampleVO> map = new HashMap<>();
		map.put("First", new SampleVO(111, "그루트", "주니어"));
		
		return map;
	}
	
	@GetMapping(value = "/check", params = { "height", "weight" })
	public ResponseEntity<SampleVO> check(Double height, Double weight) { //
		SampleVO vo = new SampleVO(0, "" + height, "" + weight);
		ResponseEntity<SampleVO> result = null;
		
		if (height < 150) {
			result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo); // height값이 150보다 작으면 502(bad gateway) 상태 코드와 데이터를 전송
		} else {
			result = ResponseEntity.status(HttpStatus.OK).body(vo); // height값이 150보다 크면 200(ok) 코드와 데이터를 전송
		}
		return result;
	}
	
	@GetMapping("/product/{cat}/{pid}")
	public String[] getPath(@PathVariable("cat") String cat,
							@PathVariable("pid") Integer pid) {
				return new String[] { "category : " + cat, "productid : " + pid };
	}
	
	@PostMapping("/ticket")
	public Ticket convert(@RequestBody Ticket ticket) {
		log.info("convert....ticket " + ticket);
		return ticket;
	}
}
