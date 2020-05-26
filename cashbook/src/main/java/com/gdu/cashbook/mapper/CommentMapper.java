package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Comment;



@Mapper
public interface CommentMapper {
	//댓글 수정
	public int updateComment(Comment comment);
	//댓글 가져오기
	public Comment selectCommentOne(int commentNo);
	//댓글 삭제
	public int deleteComment(int commentNo);
	//댓글 리스트 출력
	 public List<Comment> selectCommentList(Map<String, Object>map);
	 //댓글 총 합계 구하기
	 public int selectCommentCount();
	 //댓글 입력
	 public int insertComment(Comment comment);
}
