package com.kd.basic.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminOrderService {
	private final AdminOrderMapper adminOrderMapper;
	
	public List<Map<String, Object>> order_list(SearchCriteria cri, String period, String start_date, String end_date ,String pay_method, String or_status, String pay_status){
		return adminOrderMapper.order_list(cri, period, start_date, end_date, pay_method, or_status, pay_status);
	}
	
	public int getTotalcount( SearchCriteria cri,  String period, String start_date,  String end_date , String pay_method, String or_status, String pay_status) {
		return adminOrderMapper.getTotalcount(cri, period, start_date, end_date, pay_method, or_status, pay_status);
	}

	public void  order_status_change( Integer or_code, String or_status) {
		adminOrderMapper.order_status_change(or_code, or_status);
	}
	 
}
