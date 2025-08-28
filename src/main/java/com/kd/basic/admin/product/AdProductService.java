package com.kd.basic.admin.product;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdProductService {
	
	private final AdProductMapper adProductMapper;
	
	public void pro_insert(ProductDTO dto) {
		adProductMapper.pro_insert(dto);
	}
	
	public List<ProductDTO> pro_list(SearchCriteria cri){
		return adProductMapper.pro_list(cri);
	}
}
