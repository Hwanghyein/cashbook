package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.UpdateMemberPw;

@Mapper
public interface MemberMapper {
	//가계부 회원정보 총 합계
	public int selectMemberCount();
	//가계부 회원정보 리스트 출력
	public List<Member> selectMemberIdList(Map<String, Object>map);
	//회원사진 가져오기 
	public String selectMemberPic(String memberId);
	//비밀번호 변경
	public int updateMemberByPw(UpdateMemberPw updateMemberPw);
	//비밀번호 업데이트 
	public int  updateMemberPw(Member member);
	//비밀번호 찾기
	public Member selectMemberByIdAndEmail(Member member);
	//아이디 찾기
	public String selectMemberIdByMember(Member member);
	//회원탈퇴
	public int deleteMember(LoginMember loginMember);
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
