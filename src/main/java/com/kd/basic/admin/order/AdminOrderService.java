package com.kd.basic.admin.order;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.kd.basic.admin.delivery.AdminDeliveryMapper;
import com.kd.basic.admin.payment.AdminPaymentMapper;
import com.kd.basic.admin.payment.AdminPaymentService;
import com.kd.basic.common.dto.DeliveryDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminOrderService {
	private final AdminOrderMapper adminOrderMapper;
	private final AdminPaymentMapper adminPaymentMapper;
	private final AdminDeliveryMapper adminDeliveryMapper;
	
	public List<Map<String, Object>> order_list(SearchCriteria cri, String period, String start_date, String end_date ,String pay_method, String or_status, String pay_status){
		return adminOrderMapper.order_list(cri, period, start_date, end_date, pay_method, or_status, pay_status);
	}
	
	public int getTotalcount( SearchCriteria cri,  String period, String start_date,  String end_date , String pay_method, String or_status, String pay_status) {
		return adminOrderMapper.getTotalcount(cri, period, start_date, end_date, pay_method, or_status, pay_status);
	}
	
	@Transactional
	public void  order_status_change( Integer or_code, String or_status) {
		adminOrderMapper.order_status_change(or_code, or_status);
		   if(or_status.equals("입금완료")) {
			   //결제테이블이 결제상태도 입금완료 변경작업 
			   adminPaymentMapper.payment_status_change(or_code, or_status);
		   }
	}
	
	//주문상세내역
	public  List<Map<String, Object>> orderdetail_info(Integer or_code){
		return adminOrderMapper.orderdetail_info(or_code);
	}
	
	public OrderDTO order_info(Integer or_code) {
		return adminOrderMapper.order_info(or_code);
	}

	@Transactional
	public  void order_product_del(Integer or_code, Integer item_num, int unitprice ) {
		adminOrderMapper.order_product_del(or_code, item_num);
		//주문테이블의 전체금액- 개별상품금액 빼기(삭제상품금액)
		adminOrderMapper.order_price_change(or_code, unitprice);
		//결제테이블의 전체금액 - 개별상품금액 빼기 (상품 금액)
		adminPaymentMapper.payment_price_change(or_code, unitprice);
	}
	public  void admin_order_message(Integer or_code,String or_message){
		adminOrderMapper.admin_order_message(or_code, or_message);
	}

	public void order_info_edit(OrderDTO dto) {
		//주문테이블 배송지정보 수정
		adminOrderMapper.order_info_edit(dto);
		//배송테이블 배송지정보 수정 
		DeliveryDTO deliveryDTO = new DeliveryDTO();
		deliveryDTO.setOr_code(dto.getOr_code());
		deliveryDTO.setShip_addr(dto.getOr_addr_basic());
		deliveryDTO.setShip_deaddr(dto.getOr_addr_detail());
		deliveryDTO.setShip_zipcode(dto.getOr_addr_zipcode());
		adminDeliveryMapper.delivery_info_edit(deliveryDTO);
	}
}
