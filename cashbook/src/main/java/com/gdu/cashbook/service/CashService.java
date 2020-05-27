package com.gdu.cashbook.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.dayAndMonthAndYearAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired
	private CashMapper cashMapper;
	//달에 총 합계
	public Integer getMonthTotal(String memberId,int year, int month ) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		Integer monthSum = cashMapper.selectmonthSum(map);
		if(monthSum ==null) {
			monthSum=0;
		}
		return monthSum;
	}
	//총 월 합과 년도 월별 합계
	public Map<String, Object> selectYearListAndmonthtotal (String memberId,LocalDate day){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("day", day);
		List<dayAndMonthAndYearAndPrice> list = cashMapper.selectYearList(map);
		Integer monthtotal =cashMapper.monthtotal(map);
		if(monthtotal ==null) {
			monthtotal =0;
		}
		Map<String, Object> map2 = new HashMap<>();
		map2.put("list",list);
		map2.put("monthtotal",monthtotal);
		return map2;
	}
	//가계부 수정
	public int modifyCash(Cash cash){
		return cashMapper.updateCash(cash);
	}
	//가계부 정보 가져오기
	public Cash getCash(int cashNo) {
		return cashMapper.selectCash(cashNo);
	} 
	//가계부 다이어리 삭제
	public void removeCash(int cashNo) {
		cashMapper.deleteCash(cashNo);
	}
	//가계부 입력
	public int addCash(Cash cash) {
		return cashMapper.insertCash(cash);
	}
	//년와월 수입,지출 총액 출력
	public List<dayAndMonthAndYearAndPrice> getCashandPriceList(String memberId,int year,int month){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		return cashMapper.selectDayAndPirceList(map);
	}
	//가계부 출력
	public Map<String, Object> getCashListByDate(Cash cash){
		List<Cash> cashList =cashMapper.selectCashListByDate(cash);
		int cashKindSum=0;
		try {
			cashKindSum = cashMapper.selectCashKindSum(cash);
		}catch(Exception e){
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<>();
		map.put("cashList", cashList);
		map.put("cashKindSum", cashKindSum);
		return map;
	}
	
	
}
