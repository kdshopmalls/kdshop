package com.kd.basic.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.dto.ProductDTO;
import com.kd.basic.common.utils.Criteria;

public interface ProductMapper {
	List<ProductDTO> getProductListBysecondCategory(@Param("cri") Criteria cri, @Param("cate_code") Integer cate_code);

	List<ProductDTO> mainList();

	
	int getProductListCountBysecondCategory(Integer cate_code);
	
	ProductDTO pro_detail(Integer item_num);

}

