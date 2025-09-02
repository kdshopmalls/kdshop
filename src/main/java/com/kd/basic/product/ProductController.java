package com.kd.basic.product;

import java.io.File;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.admin.category.AdCategoryService;
import com.kd.basic.common.domain.ProductDTO;
import com.kd.basic.common.utils.Criteria;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.common.utils.PageMaker;
import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/product/*")
@Controller
public class ProductController {
		private final ProductService productService;
		private final AdCategoryService adCategoryService;
		@Value("${com.kd.upload.path}")
		private String uploadPath;
		
		@Value("${com.kd.upload.ckeditor.path}")
		private String uploadCKPath;
		@GetMapping("/pro_list")
		public void pro_list(SearchCriteria cri ,@ModelAttribute("cate_name") String cate_name, @ModelAttribute("cate_code") Integer cate_code, Model model ) throws Exception{ 
			model.addAttribute(cate_name, adCategoryService.getFirstCategoryList());
			cri.setPerPageNum(6);
			
			List<ProductDTO> secodCateGoryList = productService.getProductListBysecondCategory(cri, cate_code);
			model.addAttribute("secodCateGoryList", secodCateGoryList);	
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			
			pageMaker.setTotalCount(productService.getProductListCountBysecondCategory(cate_code));
			model.addAttribute("pageMaker", pageMaker);
		}
		
		// 상품목록 이미지출력하기.. 클라이언트에서 보낸 파라미터명 스프링의 컨트롤러에서 받는 파라미터명이 일치해야 한다.
		@GetMapping("/image_display")
		public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
			
			return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
		}

}
