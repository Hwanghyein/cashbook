package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.BoardMapper;
import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.vo.Board;



@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private CommentMapper commentMapper;
	//게시판 정보 가져오기
	public Board  selectBoardOne(int boardNo) {
		return boardMapper.selectBoardOne(boardNo);
	}
	//게시판 수정
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
	//게시판 삭제
	public void removeBoard(Board board) {
		boardMapper.deleteCommentNo(board);
	 boardMapper.deleteBoardNo(board);
	}
	//게시판 입력
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
	//게시판 출력
	public List<Board> getBoardList(int currentPage, int rowPerPage){
		Map<String, Object> map =new HashMap<>();
		int startRow=(currentPage-1)*rowPerPage;
		map.put("startRow", startRow);
		map.put("rowPerPage", rowPerPage);
		return boardMapper.selectBoardList(map);
	}
	//페이지 총 합계 
	public int getBoardLastPage(int rowPerPage) {
		int count=boardMapper.selectBoardCount();
		int lastPage= count/rowPerPage;
		if(count%rowPerPage !=0) {
			lastPage+=1;
		}
		return lastPage;
	}
}
