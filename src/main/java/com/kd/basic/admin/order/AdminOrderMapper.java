package com.kd.basic.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.utils.SearchCriteria;

public interface AdminOrderMapper {
	List<Map<String, Object>> order_list(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date ,@ModelAttribute("pay_method") String pay_method,@ModelAttribute("or_status") String or_status,@ModelAttribute("pay_status") String pay_status);
	
	int getTotalcount(@Param("cri") SearchCriteria cri, @Param("period") String period, @Param("start_date") String start_date, @Param("end_date") String end_date ,@ModelAttribute("pay_method") String pay_method,@ModelAttribute("or_status") String or_status,@ModelAttribute("pay_status") String pay_status);

	void  order_status_change( @Param("or_code") Integer or_code,@Param("or_status") String or_status);
	
	//주문상세내역
	List<Map<String, Object>> orderdetail_info(Integer or_code);
	
	//주문내역 
	OrderDTO order_info(Integer or_code);
	//주문내역 개별상품 삭제
	void order_product_del(@Param("or_code") Integer or_code,@Param("item_num")  Integer item_num);
	//주문금액 일부 삭제
	void order_price_change(@Param("or_code") Integer or_code,@Param("unitprice")  int unitprice);
	//관리자 주문메세지 수정 
	void admin_order_message(@Param("or_code")  Integer or_code, @Param("or_message") String or_message); 
	//배송지 정보수정
	void order_info_edit(OrderDTO dto);
}
