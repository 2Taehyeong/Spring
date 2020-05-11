package kr.inhatc.spring.board.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.dto.FileDto;
import kr.inhatc.spring.board.mapper.BoardMapper;
import kr.inhatc.spring.utils.FileUtils;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDto> boardList() {
		return boardMapper.boardList();
	}

	@Override
	public void boardInsert(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) {
		boardMapper.boardInsert(board);
		
		// 임시확인
//		if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
//			Iterator<String> iter = multipartHttpServletRequest.getFileNames();
//			
//			while(iter.hasNext()) {
//				String name = iter.next();
//				
//				List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
//				for (MultipartFile multipartFile : list) {
//					System.out.println("============>file name : " + multipartFile.getOriginalFilename());
//					System.out.println("============>file size : " + multipartFile.getSize());
//					System.out.println("============>file type : " + multipartFile.getContentType());
//				}
//			}
//		}
		
		// 1. 파일 저장
		List<FileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);

		// 2. 디비 저장
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.boardFileInsert(list);
		}

	}

	@Override
	public BoardDto boardDetail(int boardIdx) {
		BoardDto board = boardMapper.boardDetail(boardIdx);
		boardMapper.updateHit(boardIdx);
		return board;
	}

	@Override
	public void boardUpdate(BoardDto board) {
		boardMapper.boardUpdate(board);
	}

	@Override
	public void boardDelete(int boardIdx) {
		boardMapper.boardDelete(boardIdx);
	}

}
