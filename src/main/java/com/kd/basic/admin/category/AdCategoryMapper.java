package com.kd.basic.admin.category;

import java.util.List;

import com.kd.basic.common.dto.CategoryDTO;

public interface AdCategoryMapper {
	//1차 카테고리 목록
	List<CategoryDTO> getFirstCategoryList(); 
	
	//1차를 참조하는 2차 카테고리 목록
	List<CategoryDTO> getSecondCategoryList(Integer firstCategoryCode); 
}
