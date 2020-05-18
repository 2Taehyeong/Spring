package kr.inhatc.spring.board.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;

// DTO : Data Transfer Object 데이터를 전달해주는 객체

@Data
@Getter
@ToString
public class BoardDto {
	
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId; 
	private String createDatetime;
	
	// 파일 관리를 위한 리스트 추가
	private List<FileDto> fileList;
}
