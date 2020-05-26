package com.gdu.cashbook.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook.mapper.CashMapper;
import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;

@Service
@Transactional
public class CashService {
	@Autowired
	private CashMapper cashMapper;
	
	public List<Cash> getYearList(String memberId,int year,int month){
		Map<String, Object> map = new HashMap<>();
		map.put("memberId", memberId);
		map.put("year", year);
		map.put("month", month);
		return cashMapper.selectYearList(map);
	}
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
	public List<DayAndPrice> getCashandPriceList(String memberId,int year,int month){
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
