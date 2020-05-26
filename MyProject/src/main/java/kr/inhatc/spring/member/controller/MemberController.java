package kr.inhatc.spring.member.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.member.dto.MemberDto;
import kr.inhatc.spring.member.dto.M_FileDto;
import kr.inhatc.spring.member.service.MemberService;

@Controller
public class MemberController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private MemberService memberService;
	
	// 멤버 리스트
	@RequestMapping("/member/memberList")
	public String memberList(Model model){
		List<MemberDto> list = memberService.memberList();
		model.addAttribute("list", list);
		return "member/memberList";
	}
	
	@RequestMapping("/member/memberWrite")
	public String memberWrite(){
		return "member/memberWrite";
	}
	
	@RequestMapping("/member/memberInsert")
	public String memberInsert(MemberDto member, MultipartHttpServletRequest multipartHttpServletRequest){
		memberService.memberInsert(member, multipartHttpServletRequest);
		return "redirect:/member/memberList";
	}
	
	//멤버 상세정보
	@RequestMapping("/member/memberDetail")
	public String memberDetail(@RequestParam String memberId, Model model){
		//멤버 번호를 매개변수로, 서비스 호출 후 결과 저장
		MemberDto member = memberService.memberDetail(memberId);
		//모델을 통해 데이터 전달
		model.addAttribute("member", member);
		return "member/memberDetail";
	}
	
	//멤버 수정
	@RequestMapping("/member/memberUpdate")
	public String memberUpdate(MemberDto member){
		memberService.memberUpdate(member);
		return "redirect:/member/memberList";
	}
	
	//멤버 삭제
	@RequestMapping("/member/memberDelete")
	public String memberDelete(@RequestParam("memberId") String memberId){
		memberService.memberDelete(memberId);
		return "redirect:/member/memberList";
	}
	
	//파일 다운로드
	@RequestMapping("/member/downloadMemberFile")
	public void downloadmemberFile(
			@RequestParam("idx") int idx,
			@RequestParam("memberIdx") String memberId,
			HttpServletResponse response) throws Exception{

		M_FileDto memberFile = memberService.selectFileInfo(idx, memberId);
		
		if(ObjectUtils.isEmpty(memberFile) == false){
			String fileName = memberFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(memberFile.getStoredFilePath()));
			
			// response 헤더에 설정
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition",
					"attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binart");
			
			//파일 작성
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}

}