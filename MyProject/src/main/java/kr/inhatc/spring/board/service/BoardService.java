package kr.inhatc.spring.board.service;

import java.util.List;

import kr.inhatc.spring.board.dto.BoardDto;

public interface BoardService {

	List<BoardDto> boardList();

	void boardInsert(BoardDto board);

	BoardDto boardDetail(int boardIdx);

	void boardUpdate(BoardDto board);

	void boardDelete(int boardIdx);



}
