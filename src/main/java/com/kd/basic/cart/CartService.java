package com.kd.basic.cart;

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
}
