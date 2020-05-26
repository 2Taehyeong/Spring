package kr.inhatc.spring.member.dto;

import lombok.Data;

@Data
public class M_FileDto {
	
	private int idx;
	private String memberId;
	private String originalFileName;
	private String storedFilePath;
	private long fileSize;
}