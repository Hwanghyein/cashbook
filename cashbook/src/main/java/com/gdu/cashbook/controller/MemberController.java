package com.gdu.cashbook.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
import com.gdu.cashbook.vo.UpdateMemberPw;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	//회원정보 리스트 출력
	@GetMapping("/memberList")
	public String memberList(HttpSession session,Model model, @RequestParam(value="currentPage", defaultValue = "1") int currentPage) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		LoginMember loginMember = (LoginMember)session.getAttribute("loginMember");
	      if(loginMember.getMemberLevel()!=0) {
	         return "redirect:/";
	    }
	    	final int rowPerPage=5;
			List<Member> memberList = memberService.getMemberList(currentPage, rowPerPage);
			model.addAttribute("memberList", memberList);
			System.out.println(memberList+"<--memberList");
			int lastPage= memberService.getBoardLastPage(rowPerPage);
			model.addAttribute("lastPage", lastPage);
			model.addAttribute("currentPage", currentPage);
			return "memberList";
	}
	//비밀번호 변경
	@GetMapping("/updateMemberPw")
	public String updateMemberPw(HttpSession session, Model model) {
		//로그인 아닐일때
		if(session.getAttribute("loginMember")== null) {
				return "redirect:/";
		}
		return "updateMemberPw";
	}
	@PostMapping("/updateMemberPw")
	public String updateMemberPw(HttpSession session,UpdateMemberPw updateMemberPw) {
		//로그인 아닐일때
		if(session.getAttribute("loginMember")== null) {
				return "redirect:/";
		}
		System.out.println(updateMemberPw);
		memberService.modifyMemberByPw(updateMemberPw);
		return "redirect:/memberInfo";
	}
	
	//비밀번호 찾기 폼
	@GetMapping("/findMemberPw")
	public String finMemberPw(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
			return "redirect:/";
		}
		return "findMemberPw";
	}

	@PostMapping("/findMemberPw")
	public String finMemberPw(HttpSession session,Model model, Member member) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
				return "redirect:/";
		}
		int row=memberService.getMemberPw(member);
		String msg="아이디와 메일을 확인하세요";
		if(row==1) {
			msg="비밀번호를 입력한 메일로 전송하였습니다.";
		}
		model.addAttribute("msg",msg);
		return "memberPwView";
	}
	
	//아이디 찾기 폼
	@GetMapping("/findMemberId")
	public String finMemberId(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
				return "redirect:/";
		}
		return "findMemberId";
	}
	//아이디 찾기 액션
	@PostMapping("/findMemberId")
	public String finMemberId(HttpSession session,Model model, Member member) {
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
				return "redirect:/";
		}
		String memberIdPart=memberService.getselectMemberIdByMember(member);
		System.out.println(memberIdPart);
		model.addAttribute("memberIdPart", memberIdPart);
		return "memberIdView";
	}
	
	//회원탈퇴 폼
	@GetMapping("/removeMember")
	public String configure(HttpSession session,Model model) {
		//로그인 아닐때
		if(session.getAttribute("loginMember")== null) {
				return "redirect:/";
		}
		Member member = memberService.getMemberId((LoginMember)(session.getAttribute("loginMember")));
		model.addAttribute("member",member);
		return "removeMember";
	}
	//회원탈퇴 액션
	@PostMapping("/removeMember")
	public String configure(HttpSession session,LoginMember loginMember) {
		memberService.removeMember(loginMember);
		session.invalidate();
		return "redirect:/";
	}
	//회원 정보수정 폼
	@GetMapping("/modifyMember")
	public String modifyMember(HttpSession session, Model model,LoginMember loginMember) {
		//로그인 아닐때
		if(session.getAttribute("loginMember")== null) {
				return "redirect:/";
		}
		Member member = memberService.getMemberId((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member);
		model.addAttribute("member",member);
		return "modifyMember";
	}
	
	@PostMapping("/modifyMember")
	public String modifyMembmer(MemberForm memberForm, MultipartFile file ) {
		memberService.modifyMember(memberForm);
		return "redirect:/memberInfo";
	}
	//회원정보 폼으로 이동
	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		//로그인 아닐때
		if(session.getAttribute("loginMember")== null) {
				return "redirect:/";
		}
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member);
		model.addAttribute("member", member);
		return "memberInfo";
	}
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
			return "redirect:/home";
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
	public String addMember(HttpSession session,MemberForm memberForm) {//Commend 객체,도메인객체
		//로그인 중일때
		if(session.getAttribute("loginMember")!= null) {
			return "redirect:/";
		}
		System.out.println(memberForm+"<--memberForm");
		//파일은 png,jpg,gif만 업로드 가능
		//if(memberForm.getMemberPic() !=null) {
			//if(!memberForm.getMemberPic().getContentType().equals("image/png") || !memberForm.getMemberPic().getContentType().equals("image/jpg") || !memberForm.getMemberPic().getContentType().equals("image/gif")) {
				//return "redirect:/addMember";
			//}
		//}
		memberService.addMember(memberForm);
		return "redirect:/index";
	}
	
	
}
