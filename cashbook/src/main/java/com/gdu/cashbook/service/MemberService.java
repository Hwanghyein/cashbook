package com.gdu.cashbook.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.Memberid;
import com.gdu.cashbook.vo.LoginMember;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberidMapper memberidMapper;
	//비밀번호 찾기 
	public int getMemberPw(Member member) {
		//pw추가
		UUID uuid=UUID.randomUUID();		
		String memberPw=uuid.toString().substring(0,8);
		member.setMemberPw(memberPw);
		int row = memberMapper.updateMemberPw(member);
		if(row ==1) {
			System.out.println(memberPw+"<--update memberPw");
			//메일로 update성공한 랜덤 pw를 전송
			//메일객체 new JavaMailSender();
		}
		return row;
	}
	//아이디 찾기 
	public String getselectMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	//회원탈퇴 
	public void removeMember(Member member) {
		Memberid memberid= new Memberid();
		memberid.setMemberId(member.getMemberId());
		memberidMapper.insertMemberid(memberid);
		memberMapper.deleteMember(member);
	}
	//회원탈퇴
	//public int removeMember(Member member) {
		//return memberMapper.deleteMember(member);
	//}
	//회원정보수정
	public int modifyMember(Member member) {
		return memberMapper.updateMember(member);
	} 
	//회원정보 가져오기
	public Member getMemberId(LoginMember loginMember) {
		return memberMapper.selectMemberIdOne(loginMember);
	}
	//회원정보
	public Member getMemberOne(LoginMember loginMember) {
		return memberMapper.selectMemberOne(loginMember);
	}
	//아이디 중복 체크 
	public String checkMemberId(String memberIdCheck) {
		return memberMapper.selectMemberId(memberIdCheck);
	}
	
	//로그인
	public LoginMember login(LoginMember loginMember) {
		return memberMapper.selectLoginMember(loginMember);
	}
	//회원가입
	public int addMember(Member member) {
		return memberMapper.insertMember(member);
	} 
}
