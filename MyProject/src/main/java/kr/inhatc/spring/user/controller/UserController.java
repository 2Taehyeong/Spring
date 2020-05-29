package kr.inhatc.spring.user.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.service.BoardService;
import kr.inhatc.spring.user.entitiy.Users;
import kr.inhatc.spring.user.service.UserService;

@Controller
public class UserController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String hello() {
		return "redirect:/user/userList";
	}
	
	// GET(read), POST(create), PUT(update), DELETE(delete)
	// 사용자 리스트
	@RequestMapping(value = "/user/userList", method=RequestMethod.GET)
	public String userList(Model model) {
		List<Users> list = userService.userList();
		model.addAttribute("list", list);
		return "user/userList"; 
	}
	
	// 사용자 등록
	@RequestMapping(value = "/user/userInsert", method=RequestMethod.GET)
	public String userWrite() {
		return "user/userWrite"; 
	}
	
	// 사용자 등록
	@RequestMapping(value = "/user/userInsert", method=RequestMethod.POST)
	public String userInsert(Users user) {
		userService.saveUsers(user);
		return "redirect:/user/userList"; 
	}
	
	// 사용자 상세보기
	@RequestMapping(value = "/user/userDetail/{id}", method=RequestMethod.GET)
	public String userDetail(@PathVariable("id") String id, Model model) {
		Users user = userService.userDetail(id);
		model.addAttribute("user", user);
		return "user/userDetail"; 
	}
	
	// 사용자 업데이트
	@RequestMapping(value = "/user/userUpdate/{id}", method=RequestMethod.POST)
	public String userUpdate(@PathVariable("id") String id, Users user) {
		// 아이디 설정
		user.setId(id);
		userService.saveUsers(user);
		return "redirect:/user/userList"; 
	}
	
	// 사용자 삭제
	@RequestMapping(value = "/user/userDelete/{id}", method=RequestMethod.GET)
	public String userDelete(@PathVariable("id") String id) {
		userService.userDelete(id);
		return "redirect:/user/userList"; 
	}
}
