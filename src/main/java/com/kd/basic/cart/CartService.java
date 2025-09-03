package com.kd.basic.cart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {
	
	private final CartMapper cartMapper;
	
	public void cart_add(CartDTO dto) {
		cartMapper.cart_add(dto);
	}

	public List<Map<String, Object>> cart_list(String mb_id){
		return cartMapper.cart_list(mb_id);
	}
	
	public Integer getCartTotal(String mb_id) {
		return cartMapper.getCartTotal(mb_id);
	}
	
	public void cart_empty(String mb_id) {
		cartMapper.cart_empty(mb_id);
	}
	
	public void  cart_sel_delete(int[] item_num,String mb_id) {
		HashMap<String, Object> map =new HashMap<>();
		map.put("item_num_arr", item_num);
		map.put("mb_id", mb_id);
		cartMapper.cart_sel_delete(map);
	}
	
	public void cart_quantity_change(CartDTO dto) {
		cartMapper.cart_quantity_change(dto);
	}
	
}
