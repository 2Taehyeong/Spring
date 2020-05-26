package kr.inhatc.spring.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.member.dto.M_FileDto;
import kr.inhatc.spring.member.dto.MemberDto;
import kr.inhatc.spring.member.mapper.MemberMapper;
import kr.inhatc.spring.utils.M_FileUtils;

@Service
public class MemberServiceImpl implements MemberService {

	// 메모리 자동 등록
	@Autowired
	private MemberMapper memberMapper;
	
	// 파일
	@Autowired
	private M_FileUtils fileUtils;
	//private M_FileUtils fileUtils = new M_FileUtils();
	
	// 멤버 리스트
	@Override
	public List<MemberDto> memberList() {
		return memberMapper.memberList();
	}
	
	// 멤버 추가
	@Override
	public void memberInsert(MemberDto member, MultipartHttpServletRequest multipartHttpServletRequest) {
		memberMapper.memberInsert(member);
		
		// 파일 저장
		List<M_FileDto> list = fileUtils.parseFileInfo(member.getMemberId(), multipartHttpServletRequest);

		// DB에 파일 저장
		if(CollectionUtils.isEmpty(list) == false) {
			memberMapper.memberFileInsert(list);
		}
		
	}
	
	// 멤버 상세정보
	@Override
	public MemberDto memberDetail(String memberId) {
		MemberDto member = memberMapper.memberDetail(memberId);
		
		// 파일 정보
		List<M_FileDto> fileList = memberMapper.selectMemberFileList(memberId);
		member.setFileList(fileList);
		
		return member;
	}

	// 멤버 수정
	@Override
	public void memberUpdate(MemberDto member) {
		memberMapper.memberUpdate(member);
	}
	
	// 멤버 삭제
	@Override
	public void memberDelete(String memberId) {
		memberMapper.memberDelete(memberId);
	}

	// 멤버 프로필사진 다운로드
	@Override
	public M_FileDto selectFileInfo(int idx, String memberId) {
		M_FileDto memberFile = memberMapper.selectFileInfo(idx, memberId);
		return memberFile;
	}

}