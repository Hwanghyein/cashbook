package com.gdu.cashbook.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook.vo.Cash;
import com.gdu.cashbook.vo.DayAndPrice;
import com.gdu.cashbook.vo.dayAndMonthAndYearAndPrice;

@Mapper
public interface CashMapper {
	//달 합계
	public Integer selectmonthSum(Map<String,Object> map);
	//월별 총 구하기
	public Integer monthtotal(Map<String,Object> map);
	//년도 월별 비교하기
	public List<dayAndMonthAndYearAndPrice> selectYearList(Map<String,Object> map);
	//가계부 정보 가져오기
	public Cash  selectCash(int cashNo);
	//가계부 입력폼
	public int  insertCash(Cash cash);
	//년도와 월별 로 가계부 출력
	public List<dayAndMonthAndYearAndPrice> selectDayAndPirceList(Map<String,Object> map);
	//가계부 수정
	public int  updateCash(Cash cash);
	//가계부 삭제
	public int  deleteCash(int cashNo);
	//로그인 사용자의 오늘 날짜를 cash 목록
	public List<Cash> selectCashListByDate(Cash cash);
	//총합계
	public int selectCashKindSum(Cash cash);
}

