package com.gdu.cashbook.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.UpdateMemberPw;

@Mapper
public interface MemberMapper {
	//비밀번호 변경
	public int updateMemberByPw(UpdateMemberPw updateMemberPw);
	//비밀번호 업데이트 
	public int  updateMemberPw(Member member);
	//비밀번호 찾기
	public Member selectMemberByIdAndEmail(Member member);
	//아이디 찾기
	public String selectMemberIdByMember(Member member);
	//회원탈퇴
	public int deleteMember(Member member);
	//회원정보수정
	public int updateMember(Member member);
	//회원정보 비밀번호포함해서  가져오기
	public Member selectMemberIdOne(LoginMember loginMember);
	//회원정보
	public Member selectMemberOne(LoginMember loginMember);
	//아이디 중복 체크 
	public String selectMemberId(String memberIdCheck);
	//로그인 
	public LoginMember selectLoginMember(LoginMember loginMember);
	//회원가입
	public int insertMember(Member member);
	
}
