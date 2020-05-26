package com.gdu.cashbook.controller;



import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.CashService;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.LoginMember;

@Controller
public class CashController {
	@Autowired
	private CashService cashService;
	//년도 월별 비교
	@GetMapping("/getYearList")
	public String getYearList(HttpSession session,Model model,@RequestParam(value="day",required=false) @DateTimeFormat(pattern="yyyy")LocalDate day) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		
		Calendar yDay = Calendar.getInstance();
		
		
		
		String memberId=((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int year=yDay.get(Calendar.YEAR);
		int month=yDay.get(Calendar.MONTH)+1;
		
		List<Cash>  yearList = cashService.getYearList(memberId, year, month);
		model.addAttribute(" yearList", yearList);
		System.out.println(yearList+"<--yearList");
		return "getYearList";
	}
	//가계부 다이어리 삭제
	@GetMapping("/removeCash")
	public String removeCash(HttpSession session,int cashNo) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		cashService.removeCash(cashNo);
		return "redirect:/getCashListByDate";
	}
	//가계부 수정
	@GetMapping("/modifyCash")
	public String modifyCash(HttpSession session,Model model,int cashNo) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
	}
		Cash cash = cashService.getCash(cashNo);
		model.addAttribute("cash", cash);
		System.out.println(cash);
		return "modifyCash";
	}
	@PostMapping("/modifyCash")
	public String modifyCash(HttpSession session,Cash cash) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		cashService.modifyCash(cash);
		return "redirect:/getCashListByDate";
	}
	//가계부 입력
	@GetMapping("/addCash")
	public String addCash(HttpSession session,Model model) {
		
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		
		return "addCash";
	}
	@PostMapping("/addCash")
	public String add(HttpSession session,Cash cash,@RequestParam(value="day",required=false) @DateTimeFormat(pattern="yyyy-MM-dd")LocalDate day) {
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		cashService.addCash(cash);
		return "redirect:/getCashListByDate";
	}
	
	//달력 출력
	@GetMapping("/getCashListByMonth")
	public String getCashListByMonth(HttpSession session,Model model,@RequestParam(value="day",required=false) @DateTimeFormat(pattern="yyyy-MM-dd")LocalDate day) {
		
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		
		Calendar cDay = Calendar.getInstance();
		
		if(day ==null) {
			day=LocalDate.now();
		}else {
			cDay.set(day.getYear(),day.getMonthValue()-1,day.getDayOfMonth());
		}
		//일별 수입, 지출 총액 출력
		String memberId=((LoginMember)session.getAttribute("loginMember")).getMemberId();
		int year=cDay.get(Calendar.YEAR);
		int month=cDay.get(Calendar.MONTH)+1;
		List<DayAndPrice> dayAndPriceList= cashService.getCashandPriceList(memberId, year, month);
		model.addAttribute("dayAndPriceList", dayAndPriceList);
		System.out.println(dayAndPriceList+"<--DayandPriceList");
		model.addAttribute("day", day);
		//현재 월 구하기
		//model.addAttribute("month",cDay.get(Calendar.MONTH)+1);
		//마지막 일 
		model.addAttribute("lastDay", cDay.getActualMaximum(Calendar.DATE));
		
		Calendar firstDay=cDay;
		firstDay.set(Calendar.DATE, 1);//일만 1일로 변경
		model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK));
		//특정한 요일을 구하는 
		//firstDay.get(Calendar.DAY_OF_WEEK);
		return "getCashListByMonth";
	}
	//다이러리 출력
	@GetMapping("/getCashListByDate")
	public String getCashListByDate(HttpSession session,Model model,@RequestParam(value="day",required=false) @DateTimeFormat(pattern="yyyy-MM-dd")LocalDate day) {

		if(day ==null) {
			day=LocalDate.now();
		}
		System.out.println(day+"<--day");
		if(session.getAttribute("loginMember")== null) {
			return "redirect:/";
		}
		//로그인 아이디
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		System.out.println(loginMemberId+"<--loginMemberId");
		
		
		Cash cash = new Cash();
		cash.setMemberId(loginMemberId);
		cash.setCashDate(day.toString());
		System.out.println(cash);
		
		Map<String, Object> map =cashService.getCashListByDate(cash);
		model.addAttribute("cashList", map.get("cashList"));
		model.addAttribute("cashKindSum", map.get("cashKindSum"));
		model.addAttribute("day",day);
		
		return "getCashListByDate";
	}
}
