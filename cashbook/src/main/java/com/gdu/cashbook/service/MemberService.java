package com.gdu.cashbook.service;

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
