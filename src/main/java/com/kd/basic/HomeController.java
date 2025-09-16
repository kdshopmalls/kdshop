package com.kd.basic;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kd.basic.admin.category.AdCategoryService;
import com.kd.basic.common.dto.CategoryDTO;
import com.kd.basic.common.dto.ProductDTO;
import com.kd.basic.common.utils.Criteria;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.common.utils.SearchCriteria;
import com.kd.basic.product.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	private final TestMapper testMapper;
	private final AdCategoryService adCategoryService;
	private final ProductService productService;
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	@ResponseBody
	@GetMapping("/getTime")
	public String getTime() {
		return testMapper.getTime();
	}
	//기본페이지
	@GetMapping("/")
	public String home(Model model) {
		//1차 카테고리 작업
		List<CategoryDTO> firstCategoryList = adCategoryService.getFirstCategoryList();
		model.addAttribute("firstCategoryList", firstCategoryList);
		
		List<ProductDTO> mainList= productService.mainList();
		model.addAttribute("mainList",mainList);	
		
		return "index";
	}

	// 상품목록 이미지출력하기.. 클라이언트에서 보낸 파라미터명 스프링의 컨트롤러에서 받는 파라미터명이 일치해야 한다.
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
}
