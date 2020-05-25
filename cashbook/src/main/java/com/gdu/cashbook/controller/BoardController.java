package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.BoardService;
import com.gdu.cashbook.service.CommentService;
import com.gdu.cashbook.vo.Board;
import com.gdu.cashbook.vo.Comment;
import com.gdu.cashbook.vo.LoginMember;



@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private CommentService commentService;
	//게시판 상세정보
	@GetMapping("/boardOne")
	public String selectBoardOne(HttpSession session,Model model,int boardNo,@RequestParam(value="currentPage", defaultValue = "1") int currentPage) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		
		}
		Board board = boardService.selectBoardOne(boardNo);
		model.addAttribute("board", board);
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		System.out.println(loginMemberId+"<--loginMemberId");
		final int rowPerPage = 5;
		List<Comment> commentList = commentService.getCommentList(currentPage, rowPerPage,boardNo);
		model.addAttribute("commentList", commentList);
		System.out.println(commentList+"<--commentList");
		int lastPage=commentService.getCommentLastPage(rowPerPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("currentPage", currentPage);
		return "boardOne";	
	}
	//댓글 입력
	@PostMapping("/addComment")
	public String addComment(HttpSession session,Comment comment) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		
		}
		commentService.addComment(comment);
		return "redirect:/boardList";
	}
	//댓글 삭제
	@GetMapping("/removeComment")
	public String removeComment(HttpSession session, int commentNo) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		
		}
		commentService.removeComment(commentNo);
		return "redirect:/boardList";
	}
	//게시판 수정
	@GetMapping("/modifyBoard")
	public String modifyBoard(HttpSession session,Model model,int boardNo) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		
		}
		Board board = boardService.selectBoardOne(boardNo);
		model.addAttribute("board", board);
		return "modifyBoard";	
	}
	@PostMapping("/modifyBoard")
	public String modifyBoard(HttpSession session,Board board) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		
		}
		boardService.modifyBoard(board);
		return "redirect:/boardList";
	}
	//게시판 삭제
	@GetMapping("/removeBoard")
	public String removeBoard(HttpSession session, Board board,int commentNo) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		
		}
		boardService.removeBoard(board);
		return "redirect:/boardList";
	}
	//게시판 입력
	@GetMapping("/addBoard")
	public String addBoard(HttpSession session) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		return "addBoard";
	}
	@PostMapping("/addBoard")
	public String addBoard(HttpSession session,Board board) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		boardService.addBoard(board);
		return "redirect:/boardList";
	}
	//게시판 리스트 출력
	@GetMapping("/boardList")
	public String boardList(HttpSession session,Model model, @RequestParam(value="currentPage", defaultValue = "1") int currentPage) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		System.out.println(loginMemberId+"<--loginMemberId");
		final int rowPerPage = 2;
		 List<Board> boardlist = boardService.getBoardList(currentPage, rowPerPage);
		 model.addAttribute("boardlist", boardlist);
		 System.out.println(boardlist+"<--list");
		 int lastPage=boardService.getBoardLastPage(rowPerPage);
		 model.addAttribute("lastPage", lastPage);
		 model.addAttribute("currentPage", currentPage);
		 return "boardList";
	}
}	
