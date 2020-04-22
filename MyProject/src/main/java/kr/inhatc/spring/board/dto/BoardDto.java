package kr.inhatc.spring.board.dto;

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
	 
}
