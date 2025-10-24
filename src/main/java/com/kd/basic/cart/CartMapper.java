package com.kd.basic.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CartMapper {
	//장바구니테이블 추가하기
		void cart_add(CartDTO dto);
		
	//장바구니 목록 
		List<Map<String, Object>> cart_list(String mb_id);
	//장바구니 총금액 
		Integer getCartTotal(String mb_id);
	//장바구니 비우기
		void cart_empty(String mb_id);
	//장바구니선택 삭제
		void  cart_sel_delete(HashMap<String, Object> map);
	//장바구니 수정	
		void cart_quantity_change(CartDTO dto);
		
}
