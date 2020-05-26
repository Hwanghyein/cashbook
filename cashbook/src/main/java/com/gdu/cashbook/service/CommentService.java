package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CommentMapper;
import com.gdu.cashbook.vo.Comment;


@Service
@Transactional
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	//댓글 수정
	public int modifyComment(Comment comment) {
		return commentMapper.updateComment(comment);
	}
	//댓글 정보 가져오기
	public Comment getCommentOne(int commentNo) {
		return commentMapper.selectCommentOne(commentNo);
	}
	//댓글 삭제
	public int removeComment(int commentNo) {
		return commentMapper.deleteComment(commentNo);
	}
	//댓글 입력
	public int addComment(Comment comment) {
		return commentMapper.insertComment(comment);
	}
	//댓글 리스트 출력
	public List<Comment> getCommentList(int currentPage, int rowPerPage,int boardNo){
		Map<String, Object> map = new HashMap<>();
		int startRow=(currentPage-1)*rowPerPage;
		map.put("startRow",startRow);
		map.put("rowPerPage",rowPerPage);
		map.put("boardNo", boardNo);
		return commentMapper.selectCommentList(map);
	}
	//댓글 총 합계 구하기
	public int getCommentLastPage(int rowPerPage) {
		int count=commentMapper.selectCommentCount();
		int lastPage=count/rowPerPage;
		if(count%rowPerPage !=0) {
			lastPage+=1;
		}
		return lastPage;
	}
}
