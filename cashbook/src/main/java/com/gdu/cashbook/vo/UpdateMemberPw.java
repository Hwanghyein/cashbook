package com.gdu.cashbook.vo;

public class UpdateMemberPw {
	private String memberId;
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	private String memberPw;
	private String memberPw2;
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberPw2() {
		return memberPw2;
	}
	public void setMemberPw2(String memberPw2) {
		this.memberPw2 = memberPw2;
	}
	@Override
	public String toString() {
		return "UpdateMemberPw [memberId=" + memberId + ", memberPw=" + memberPw + ", memberPw2=" + memberPw2 + "]";
	}
	
}
