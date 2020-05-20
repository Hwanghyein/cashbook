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
