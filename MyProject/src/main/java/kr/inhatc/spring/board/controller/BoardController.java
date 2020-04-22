package kr.inhatc.spring.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	// 컨트롤러를 불러올 때 서비스를 불러옴
	
	@RequestMapping("/")
	public String hello() {
		return "index";
	}
	
	/*
	@RequestMapping("/board/boardList.do")
	public ModelAndView boardList() {
		ModelAndView mv = new ModelAndView("board/boardList");
		List<BoardDto> list = boardService.boardList();
		mv.addObject("list", list);
		return mv;
	}
	*/
	
	@RequestMapping("/board/boardList.do")
	public String boardList(Model model) {
		List<BoardDto> list = boardService.boardList();
		System.out.println("=========>" + list.size());
		model.addAttribute("list", list);
		return "board/boardList";
	}
}
