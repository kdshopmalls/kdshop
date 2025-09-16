package com.kd.basic.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.kd.basic.common.utils.SearchCriteria;

public interface AdminOrderMapper {
	List<Map<String, Object>> order_list(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date ,@ModelAttribute("pay_method") String pay_method,@ModelAttribute("or_status") String or_status,@ModelAttribute("pay_status") String pay_status);
	
	int getTotalcount(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date ,@ModelAttribute("pay_method") String pay_method,@ModelAttribute("or_status") String or_status,@ModelAttribute("pay_status") String pay_status);

	void  order_status_change( @Param("or_code") Integer or_code,@Param("or_status") String or_status);
}
