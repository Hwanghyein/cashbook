package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Board;



@Mapper
public interface BoardMapper {
	//게시판 정보 가져오기
	public Board selectBoardOne(int boardNo);
	//게시판 수정
	public int updateBoard(Board board);
	//게시판 삭제
	public int deleteBoardNo(Board board);
	//댓글 삭제
	public int deleteCommentNo(Board board);
	//게시판 입력
	public int insertBoard(Board board);
	//게시판 출력
	 public List<Board> selectBoardList(Map<String, Object>map);
	//총 합계
	 public int selectBoardCount();
}
