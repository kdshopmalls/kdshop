package com.kd.basic.product;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kd.basic.common.domain.ProductDTO;
import com.kd.basic.common.utils.Criteria;

public interface ProductMapper {
	List<ProductDTO> getProductListBysecondCategory(@Param("cri") Criteria cri, @Param("cate_code") Integer cate_code);

	int getProductListCountBysecondCategory(Integer cate_code);
}
