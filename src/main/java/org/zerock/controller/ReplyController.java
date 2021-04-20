package org.zerock.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyPageDTO;
import org.zerock.domain.ReplyVO;
import org.zerock.service.ReplyService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/replies/")
@RestController
@Log4j
@AllArgsConstructor
public class ReplyController {
	
	private ReplyService service;
	
	// POST 방식으로만 동작, consumes : json 방식의 데이터만 처리, produces : 문자열을 반환
	@PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	public ResponseEntity<String> create(@RequestBody ReplyVO vo) { // create()의 파라미터는 @RequestBody를 통해 json 데이터를 ReplyVO 타입으로 변환
		
		log.info("ReplyVO : " + vo);
		
		int insertCount = service.register(vo); //ServiceImpl 호출 -> register() 호출 -> 댓글이 추가된 숫자를 확인해서 브라우저에 '200 OK' 혹은 '500 Internal Server Error'를 리턴 // 실제 인서트 되는 데이터
		// Java에서는 정상적으로 처리하면 1이 저장
		log.info("Reply INSERT COUNT : " + insertCount);
		
		//insert, update 등은 실행 결과를 1, 0으로 리턴
		return insertCount == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // success는 reply.js의 success function의 result과 매칭
		
	}
	
	@GetMapping(value = "/pages/{bno}/{page}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	// getList()는 Criteria를 이용해서 파라미터를 수집. "/pages/{bno}/{page}"의 "page"값은 Criteria를 생성해서 직접 처리해야 한다. "/pages/{bno}/{page}"의 bno는 @PathVariable을 이용해서 파라미터로 처리한다
	//전체 댓글 불러오기 - 페이징x
	/*
	public ResponseEntity<List<ReplyVO>> getList(@PathVariable("page") int page, @PathVariable("bno") int bno) {
		log.info("getList... ");
		Criteria cri = new Criteria(page, 10);
		log.info(cri);
		
		return new ResponseEntity<>(service.getList(cri, bno), HttpStatus.OK);
	}
	*/
	//게시글 댓글 조회 - 페이징 고려
	public ResponseEntity<ReplyPageDTO> getList(@PathVariable("page") int page, @PathVariable("bno") int bno) {
		
		Criteria cri = new Criteria(page, 10);
		log.info("get reply list bno : " + bno);
		log.info(cri);
		
		return new ResponseEntity<>(service.getListPage(cri, bno), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{rno}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<ReplyVO> get(@PathVariable("rno") int rno) {
		log.info("get : " + rno);
		return new ResponseEntity<>(service.get(rno), HttpStatus.OK); //HttpStatus.OK가 들어 올 때 결과를 success에 전달
	}
	
	@DeleteMapping(value="/{rno}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> remove(@PathVariable("rno") int rno) {
		log.info("remove : " + rno);
		return service.remove(rno) == 1 ? new ResponseEntity<>("success", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	//댓글 수정은 'PUT'방식이나 'PATCH' 방식을 이용해서 처리
	@RequestMapping(method = { RequestMethod.PUT, RequestMethod.PATCH }, value = "/{rno}", consumes = "application/json", produces = { MediaType.TEXT_PLAIN_VALUE })
	// 실제 수정되는 데이터는 json 포맷이므로 @RequestBody를 이용해서 처리. @RequestBody로 처리되는 데이터는 일반 파라미터나 @PathVariable 파라미터를 처리할 수 없기 때문에 직접 처리해주는 부분을 주의
	public ResponseEntity<String> modify(@RequestBody ReplyVO vo, @PathVariable("rno") int rno) {
		vo.setRno(rno);
		log.info("rno : " + rno);
		log.info("modify : " + vo);
		return service.modify(vo) == 1 ? new ResponseEntity<>("sucess", HttpStatus.OK) : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	

}
