package com.kd.basic.admin.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.common.dto.CategoryDTO;

import lombok.RequiredArgsConstructor;
@RequestMapping("/admin/category/*")
@RequiredArgsConstructor
@Controller
public class AdCategoryController {
	private final AdCategoryService adCategoryService;
	
	@GetMapping("/secondCategoryList/{firstCategoryCode}")//경로형태의 주소
	public ResponseEntity<List<CategoryDTO>> 
	secondCategoryList(@PathVariable("firstCategoryCode") Integer firstCategoryCode) throws Exception{
		
		ResponseEntity<List<CategoryDTO>> entity = null;
		
		List<CategoryDTO> subCategoryList = adCategoryService.getSecondCategoryList(firstCategoryCode);
		
		entity = new ResponseEntity<List<CategoryDTO>>(subCategoryList, HttpStatus.OK);
		return entity;
	}

}
