package com.gdu.cashbook.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook.mapper.MemberMapper;
import com.gdu.cashbook.mapper.MemberidMapper;
import com.gdu.cashbook.vo.Member;
import com.gdu.cashbook.vo.MemberForm;
import com.gdu.cashbook.vo.Memberid;
import com.gdu.cashbook.vo.UpdateMemberPw;
import com.gdu.cashbook.vo.LoginMember;

@Service
@Transactional
public class MemberService {
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberidMapper memberidMapper;
	@Autowired
	private JavaMailSender javaMailSender;
	@Value("D:\\git-cashbook\\cashbook\\cashbook\\src\\main\\resources\\static\\upload")
	private String path;
	//비밀번호 변경
	public int  modifyMemberByPw(UpdateMemberPw updateMemberPw) {
		return memberMapper.updateMemberByPw(updateMemberPw);
	}
	//비밀번호 찾기 
	public int getMemberPw(Member member) {
		//pw추가
		UUID uuid=UUID.randomUUID();		
		String memberPw=uuid.toString().substring(0,8);
		member.setMemberPw(memberPw);
		int row = memberMapper.updateMemberPw(member);
		//메일로 update성공한 랜덤 pw를 전송
		//메일객체 new JavaMailSender();
		if(row ==1) {
			System.out.println(memberPw+"<--update memberPw");
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(member.getMemberEmail());
			simpleMailMessage.setFrom("ghkdsla159@gmail.com");
			simpleMailMessage.setSubject("cashbook 비밀번호찾기 메일");
			simpleMailMessage.setText("변경된 비밀번호는"+memberPw+"입니다.");
			javaMailSender.send(simpleMailMessage);
		}
		return row;
	}
	//아이디 찾기 
	public String getselectMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
	}
	//회원탈퇴 
	public void removeMember(LoginMember loginMember) {
		//1.멤버 사진 삭제하기
		//1_1파일 이름 select member_pic from member;
		String memberPic= memberMapper.selectMemberPic(loginMember.getMemberId());
		File file = new File(path+memberPic);
		if(file.exists()) {
			file.delete();
		}
		//2.탈퇴한 아이디 저장
		Memberid memberid= new Memberid();
		memberid.setMemberId(loginMember.getMemberId());
		memberidMapper.insertMemberid(memberid);
		//3.탈퇴하기
		memberMapper.deleteMember(loginMember);
		
	}
	//회원정보수정
	public int modifyMember(MemberForm memberForm) {
		String memberId=memberForm.getMemberId();
		String memberPw= memberForm.getMemberPw();
		LoginMember loginMember =new LoginMember();
		loginMember.setMemberId(memberId);
		loginMember.setMemberPw(memberPw);
		Member member = memberMapper.selectMemberOne(loginMember);
		
		MultipartFile mf = memberForm.getMemberPic();
		String originName= mf.getOriginalFilename();
		System.out.println(originName);
		if(originName.equals("")) {
			System.out.println("사진 없음");
		}else {
			if(member.getMemberPic().equals("dafauit.jpg")) {
				System.out.println(member.getMemberPic()+"기존이미지");
			}else {
				File update = new File(path +"//"+member.getMemberPic());
	            try {
	               update.delete();
	            }catch(Exception e){
	               e.printStackTrace();
	            }
			}
			int lastDot=originName.lastIndexOf(".");
			String extension = originName.substring(lastDot);
			String memberPic = memberForm.getMemberId()+extension;
			member.setMemberPic(memberPic);
			File file = new File(path+"\\"+memberPic);
			try {
				mf.transferTo(file);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
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
	public int addMember(MemberForm memberForm) {
		MultipartFile mf = memberForm.getMemberPic();
		//확장자 필요
		String originName= mf.getOriginalFilename();
		System.out.println(originName);
		//파일은 마지막 점 위치를 찾아주세요.
		int lastDot=originName.lastIndexOf(".");
		String extension = originName.substring(lastDot);
		
		//새로운 이름을 생성: UUID
		String memberPic = memberForm.getMemberId()+extension;
		//1.db에서 저장
		Member member= new Member();
		member.setMemberId(memberForm.getMemberId());
		member.setMemberPw(memberForm.getMemberPw());
		member.setMemberName(memberForm.getMemberName());
		member.setMemberAddr(memberForm.getMemberAddr());
		member.setMemberPhone(memberForm.getMemberPhone());
		member.setMemberEmail(memberForm.getMemberEmail());
		member.setMemberPic(memberPic);
		System.out.println(member+"<--MemberService.addMember:member");
		int row = memberMapper.insertMember(member);
		
		//2.파일 저장
		//빈 파일 만들어줌 
		File file = new File(path+"\\"+memberPic);
		try {
			mf.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			//Exception
			//1.예외처리를 해야만 문법적으로 이상없는 예외
			//2.예외처리를 코드에서 구현하지 않아도 아무문제없는 예외 RUNTIMException
		} 
		
		return row;
	} 
}
