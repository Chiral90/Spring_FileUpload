package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller // 스프링의 빈으로 인식하게 하는 어노테이션. controller package는 servlet-context.xml에 기본으로 설정되어있어 별도 설정 필요x
@Log4j
@RequestMapping("/board/*") // '/board'로 시작하는 모든 처리를 BoardController가 하도록 지정
@AllArgsConstructor //Controller는 BoardService에 대해 의존적이므로 @AllArgsConstructor로 생성자를 만들고 자동으로 주입.
//생성자 만들지 않을 경우 @Setter(onMethod_ = { @Autowired })로 처리
public class BoardController {
	private BoardService service;
	
	@GetMapping("/list") // url지정, list()에 리턴이 없으므로 list.jsp로 이동 board 폴더 없이 views에 모든 jsp 있으면 return "/list"
	public void list(Criteria cri, Model model) { // 순서 상관있는지? // model은 .jsp로 데이터 전송
		log.info("list : " + cri);
		
		model.addAttribute("list", service.getList(cri));
		
		int total = service.totalCnt(cri);
		
		log.info("total : " + total);
		
		//model.addAttribute("pageMaker", new PageDTO(cri, 100)); // total은 임의의 전체 데이터 수 - 나중에 db에서 연산할 듯
		model.addAttribute("pageMaker", new PageDTO(cri, total));
	}
	
	//list는 나중에 게시물의 목록을 전달해야 하므로 Model을 파라미터로 지정하고 BoardServiceImpl 객체의 getList() 결과를 addAttribute로 담아 전달
	/*
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list", service.getList());
	}
	*/
	
	// 화면 이동 용 register
	@GetMapping("/register")
	public void register() {
		
	}
	// 글 등록 용 register : 원래는 /register, 그럼 register.jsp form action="/board/register"
	@PostMapping("/insert")
	public String insert(BoardVO board, RedirectAttributes rttr) { // list화면으로의 redirect를 위해 return을 String으로 설정
		log.info("insert : " + board);
		service.insert(board);
		int lastCnt = service.lastCnt();
		//board.setNo(newestNo);
		rttr.addFlashAttribute("result", lastCnt); // RedirectAttributes의 addFlashAttribute()는 일회성으로만 데이터를 전달
		
		return "redirect:/board/list"; // spring MVC가 내부적으로 response.sendRedirect()를 처리. controller to controller로 간주
	}
	
	//@GetMapping("/select")
	@GetMapping({"/select", "/update"}) // @GetMapping이나 @PostMapping은 URL을 배열로 처리할 수 있어서 하나의 메서드로 여러 URL 처리 가능, update는 화면 이동 용 get
	public void select(@RequestParam("no") int no, @ModelAttribute("cri") Criteria cri, Model model) {
		//log.info(no);
		log.info("/select or update");
		model.addAttribute("board", service.select(no));
	}
	
	//페이지 관련 파라미터들을 처리하기 위한 변경
	@PostMapping("/update")
	public String update(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("update : " + board);
		if (service.update(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
		//UriComponentsBuilder 클래스를 사용하면서 필요 없어지는 부분
		/*
		//원래 페이지로 이동하기 위한 pageNum, amount
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		//수정 및 삭제는 redirect 방식이므로 type과 keyword조건을 리다이렉트 시 포함시켜야한다
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/board/list";
		*/
		
		return "redirect:/board/list" + cri.getListLink();
	}
	
	/*
	//Spring이 input의 name을 VO의 변수들과 매치시켜서 board에 set
	@PostMapping("/update")
	public String update(BoardVO board, RedirectAttributes rttr) {
		log.info("update : " + board);
		if (service.update(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	*/
	
	@PostMapping("/delete")
	public String delete(@RequestParam("no") int no, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove : " + no);
		if (service.delete(no)) {
			rttr.addFlashAttribute("result", "success");
		}
		//UriComponentsBuilder 클래스를 사용하면서 필요 없어지는 부분
		/*
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		//수정 및 삭제는 redirect 방식이므로 type과 keyword조건을 리다이렉트 시 포함시켜야한다
		rttr.addAttribute("type", cri.getType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/list";
		*/
		return "redirect:/board/list" + cri.getListLink();
	}
	/*
	@PostMapping("/delete")
	public String delete(@RequestParam("no") int no, RedirectAttributes rttr) {
		log.info("remove : " + no);
		if (service.delete(no)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	*/

}
