package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	//로그인 폼으로 이동
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	//로그인 액션
	@PostMapping("/login")
	public String login(LoginMember loginMember,HttpSession session) {
		System.out.println(loginMember);
		LoginMember retrunLoginMember = memberService.login(loginMember);
		System.out.println("retrunLoginMember:"+retrunLoginMember);
		if(retrunLoginMember ==null) {//로그인 실패 경우
			return "redirect:/login";
		}else {//로그인 성공경우
			session.setAttribute("loginMember", retrunLoginMember);
			return "redirect:/";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	//회원가입 폼으로 이동
	@GetMapping("/addMember")
	public String addMemger() {
		return "addMember";
	}
	//회원가입 액션
	@PostMapping("/addMember")
	public String addMember(Member member) {//Commend 객체,도메인객체
		System.out.println(member);
		memberService.addMember(member);
		return "redirect:/index";
	}
	
	
}
