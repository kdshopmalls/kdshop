package com.kd.basic.admin.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kd.basic.common.domain.CategoryDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdCategoryService {
	private final AdCategoryMapper adCategoryMapper;
	
	public List<CategoryDTO> getFirstCategoryList(){
		return adCategoryMapper.getFirstCategoryList();
	}
	
	public List<CategoryDTO> getSecondCategoryList(Integer firstCategoryCode){
		return adCategoryMapper.getSecondCategoryList(firstCategoryCode);
	}

}
