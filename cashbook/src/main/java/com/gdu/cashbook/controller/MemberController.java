package com.gdu.cashbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	//회원가입폼
	@GetMapping("/addMember")
	public String addMemger() {
		return "addMember";
	}
	//회원가입액션
	@PostMapping("/addMember")
	public String addMember(Member member) {//Commend 객체,도메인객체
		System.out.println(member);
		memberService.addMember(member);
		return "redirect:/index";
	}
	
	
}
