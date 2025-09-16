package com.kd.basic.order;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kd.basic.cart.CartMapper;
import com.kd.basic.common.dto.DeliveryDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.dto.PaymentDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class OrderService {
	 private final OrderMapper orderMapper;
	 private final PaymentMapper paymentMapper;
	 private final DeliveryMapper deliveryMapper;
	 private final CartMapper cartMapper;
	 
	 @Transactional
	 public void order_process(OrderDTO dto, String p_method, String account_transfer, String sender) {
		//주문
			orderMapper.order_insert(dto);
			log.info("주문번호"+dto.getOr_code());
			
			//결제
			String p_method_info ="";
			String payment_status ="";
			if(!p_method.equals("kakaopay")) { //무통장입금시
				p_method_info = p_method + "/" + account_transfer + "/" + sender; 
				payment_status = "입금미납";
				
			}else {
				p_method_info = p_method; 
				payment_status = "입금완료";
			}
			//주문상세:장바구니테이블의 데이타를 참고하여 ,구성
			dto.setOr_status(payment_status);
			orderMapper.order_detail_insert(dto.getOr_code(), dto.getMb_id());
			
			PaymentDTO paymentDTO = new PaymentDTO();
			paymentDTO.setMb_id(dto.getMb_id());
			paymentDTO.setOr_code(dto.getOr_code());
			paymentDTO.setPay_price(dto.getOr_price());
			paymentDTO.setPay_method(p_method_info);
			paymentDTO.setPay_status(payment_status);
			paymentMapper.payment_insert(paymentDTO);
			
			
			//배송
			DeliveryDTO deliveryDTO = new DeliveryDTO();
			deliveryDTO.setOr_code(dto.getOr_code());
			deliveryDTO.setShip_zipcode(dto.getOr_addr_zipcode());
			deliveryDTO.setShip_addr(dto.getOr_addr_basic());
			deliveryDTO.setShip_deaddr(dto.getOr_addr_detail());
			deliveryMapper.delivery_insert(deliveryDTO);
			
			//장바구니
			cartMapper.cart_empty(dto.getMb_id());
	 }
	//현재주문내역
		public List<Map<String,Object>> getOrderInfoByOrd_code(Integer or_code){
			return orderMapper.getOrderByOr_code(or_code);
		}
}
