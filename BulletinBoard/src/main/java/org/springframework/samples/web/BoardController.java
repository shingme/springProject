package org.springframework.samples.web;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.web.domain.Board;
import org.springframework.samples.web.domain.BoardPager;
import org.springframework.samples.web.domain.Reply;
import org.springframework.samples.web.service.BulletinFacade;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
public class BoardController {
	
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);

	@Autowired //new안해도 자동 주입된다.
	private BulletinFacade bulletinFacade; // 인스턴스 변수  // static이 붙으면 정적변수 = 클래스변수 
	public void setBulletinFacade(BulletinFacade bulletinFacade){ //세터 주입
		this.bulletinFacade =bulletinFacade;
	}
	
	
	//게시판 리스트
	@RequestMapping(value="/board") //여기는 GET!
	public String boardList(BoardPager boardPager, Model model, // BoardPager를 참조하고 있다(의존관계)
						@RequestParam("pageBlock")int pageBlock) throws Exception{
		
		int count = bulletinFacade.boardCount(); //지역변수 
		List<Board> list = bulletinFacade.boardList(pageBlock, count, boardPager);//bulletinFacade는 메소드 밖에서 부터 생성했기 때문에 
		
		//뷰로 데이터를 전달한다.
		model.addAttribute("boardPager", boardPager);
		model.addAttribute("boardList", list);
		
		return "boardList";
	}
		
	//글쓰기 폼 
	@RequestMapping(value = "/b_write", method=RequestMethod.GET)
	public String boardWriteForm(@ModelAttribute("boardInfo") Board board, Model model){
		model.addAttribute("get", "get");
		return "board_write";
	}
	
	//글쓰기
	@RequestMapping(value = "/b_write", method=RequestMethod.POST)
	public String createBoard(@ModelAttribute("boardInfo") Board board){
		log.debug("글쓰기 board {}", board);
		bulletinFacade.create(board); //bulletinFacade는 Aggregation일 수 있다(Aggregation는 Association의 종류)

		return "redirect:/board?pageBlock=1";
	}
	//제목 클릭 했을 때 -> 게시판 상세보기 했을 때 댓글도 같이 가져와야한다.
	@RequestMapping("/boardComment")
	public String boardComment(
						@RequestParam("b_no") int no,
						Model model
					) throws Exception {
			
		Board board = bulletinFacade.findByNo(no);
		log.debug("{}", board);
		List<Reply> list = bulletinFacade.replyList(no);
		log.debug("{}", list);
		model.addAttribute("boardInfo", board);
		model.addAttribute("replyList", list);
		return "board_write";
		
	}
	
	//댓글입력 
	@RequestMapping(value = "/replyInsert", method=RequestMethod.POST)
	@ResponseBody // 객체 자체를 리턴해준다.
	public Reply insertReply(@RequestBody Reply reply){ //Http요청의 본문 body와 reply 타입을 보고 자동 매핑
		log.debug("reply {}", reply);
		bulletinFacade.insert(reply);
		reply = bulletinFacade.replyOne(reply.getBoardNo());
		log.debug("reply22 {}", reply);
		return reply;
	}
	//댓글 삭제
	@RequestMapping(value = "/replyDelete", method=RequestMethod.POST)
	public void deleteReply(@RequestBody Reply reply, HttpServletResponse response){ //Jackson 라이브러리의 ObjectMapper를 사용하여 객체를 Json 형식의 문자열로 만든다.
		
		ObjectMapper mapper = new ObjectMapper();
		bulletinFacade.replyDelete(reply.getNo());
		
		 try {
			response.getWriter().print(mapper.writeValueAsString("OK"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	@ResponseBody
	public ModelAndView replyList(@RequestParam("boardNo")int boardNo){
		System.out.println("ㅋㅋㅋ" + boardNo);
		
		ModelAndView mav = new ModelAndView();
		List<Reply> list = bulletinFacade.replyList(boardNo);
		mav.addObject("replyList", list);
		mav.setViewName("reply");
		log.debug("list {}", list);
		return mav;
		
	}
	*/
	@RequestMapping("/replyList")
	@ResponseBody
	public List<Reply> replyList(@RequestParam("boardNo") int boardNo){
		List<Reply> list = bulletinFacade.replyList(boardNo);
		log.debug("list {}", list);
		
		return list; //메시지 컨버터를 통해 바로 HTTP 응답 메시지 본문으로 변환
	}
	
	//게시판 수정
	@RequestMapping(value = "/b_write", method=RequestMethod.PUT)
	public String updateBoard(@ModelAttribute("boardInfo") Board board, @RequestParam("b_no") int no){
		log.debug("zzz{}", board); //Board와 bulletinFacade를 참조한다.  bulletinFacade는 Association
		board.setB_no(no);
		bulletinFacade.update(board);
		return "redirect:/board?pageBlock=1";
		
	}
	//게시판 삭제
	@RequestMapping("/delete") //여기에 DELETE 메소드 사용해보기!
	public String deleteBoard(@ModelAttribute("boardInfo") Board board, @RequestParam("b_no") int no){
		board.setB_no(no);
		bulletinFacade.delete(board);
		
	return "redirect:/board?pageBlock=1";
	}
}
