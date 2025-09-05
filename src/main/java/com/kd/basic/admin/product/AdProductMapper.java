package com.kd.basic.admin.product;

import java.util.List;

import com.kd.basic.common.dto.ProductDTO;
import com.kd.basic.common.utils.SearchCriteria;

public interface AdProductMapper {
	
	void pro_insert(ProductDTO dto); 
	
	List<ProductDTO> pro_list(SearchCriteria cri);
	
	int getTotalCount(SearchCriteria cri);
	
}
