package kr.inhatc.spring.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	
	@Autowired
	private PasswordEncoder encoder;
	
	@RequestMapping("/")
	public String hello() {
		return "index";
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
	public String userWrite(Model model) {
		List<String> enabledList = new ArrayList<>();
		enabledList.add("가능");
		enabledList.add("불가능");
		
		List<String> authorityList = new ArrayList<>();
		authorityList.add("ROLE_GUEST");
		authorityList.add("ROLE_MEMBER");
		authorityList.add("ROLE_ADMIN");
		
		Map<String, List<String>> map = new HashMap<>();
		map.put("enabledList",  enabledList);
		map.put("authorityList",  authorityList);
		
		model.addAttribute("map", map);
		
		return "user/userWrite"; 
	}
	
	// 사용자 등록
	@RequestMapping(value = "/user/userInsert", method=RequestMethod.POST)
	public String userInsert(Users user) {
		if(user != null) {
			System.out.println("변경 전 : " + user.getPw());
			String pw = encoder.encode(user.getPw());
			System.out.println("변경 후 : " + pw);
			user.setPw(pw);
			userService.saveUsers(user);
		}

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
