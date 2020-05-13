package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	//아이디 중복체크 액션
	@PostMapping("/checkMemberId")
	public String checkMemberId(HttpSession session, Model model, @RequestParam("memberIdCheck") String memberIdCheck) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
			return "redirect:/";
		}
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		System.out.println(confirmMemberId);
		if(confirmMemberId == null) {//검사받은 아이디가 중복이 되지 않을  경우
			System.out.print("아이디를 사용할 수 있습니다.");
			model.addAttribute("memberIdCheck", memberIdCheck);
		}else {//검사받은 아이디가 중복 할 경우
			System.out.print("아이디를 사용할 수 없습니다.");
			model.addAttribute("msg","사용중인 아이디입니다.");
		}
		return "addMember";
	}	
	//로그인 폼으로 이동
	@GetMapping("/login")
	public String login(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
			return "redirect:/";
		}
		//로그인되어 있지 않을 경우 login.html 이동
		return "login";
	}
	//로그인 액션
	@PostMapping("/login")
	public String login(HttpSession session, Model model,LoginMember loginMember) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
			return "redirect:/";
		}
		System.out.println(loginMember);
		LoginMember retrunLoginMember = memberService.login(loginMember);
		System.out.println("retrunLoginMember:"+retrunLoginMember);
		if(retrunLoginMember ==null) {//로그인 실패 경우
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			return "login";
		}else {//로그인 성공경우
			session.setAttribute("loginMember", retrunLoginMember);
			return "redirect:/";
		}
	}
	//로그인 아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		session.invalidate();
		return "redirect:/";
	}
	
	//회원가입 폼으로 이동
	@GetMapping("/addMember")
	public String addMemger(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
			return "redirect:/";
		}
		return "addMember";
	}
	//회원가입 액션
	@PostMapping("/addMember")
	public String addMember(HttpSession session,Member member) {//Commend 객체,도메인객체
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
			return "redirect:/";
		}
		System.out.println(member);
		memberService.addMember(member);
		return "redirect:/index";
	}
	
	
}
