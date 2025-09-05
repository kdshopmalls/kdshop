package com.kd.basic.admin.product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


import com.kd.basic.admin.category.AdCategoryService;
import com.kd.basic.common.constants.Constants;
import com.kd.basic.common.dto.ProductDTO;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.common.utils.PageMaker;
import com.kd.basic.common.utils.SearchCriteria;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//관리자 : 상품관리기능
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product/*")
public class AdProductController {

	

	private final AdProductService adProductService;
	private final AdCategoryService adCategoryService;
	
	// 상품이미지 관련작업기능
//	private final FileUtils fileUtils;
	
	/* 생성자 주입
	public AdProductController(FileUtils fileUtils) {
		this.fileUtils = fileUtils;
	}
	*/
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	@Value("${com.kd.upload.ckeditor.path}")
	private String uploadCKPath;
	
	// 상품등록 폼.  1차카테고리 정보를 출력.	
	@GetMapping("/pro_insert")
	public void pro_insert(Model model) {
		
		// 1차카테고리 목록
		model.addAttribute("fristCategoryList", adCategoryService.getFirstCategoryList());
	}
	
	// 상품등록(저장).  <input type="file" name="pro_img_upload">
	@PostMapping("/pro_insert")
	public String pro_insert(ProductDTO dto, MultipartFile item_img_upload) throws Exception {
		log.info("상품정보"+dto);
		//1)상품 이미지업로드 작업
		String datefolder = FileUtils.getDateFolder(); 
		String saveFileName = FileUtils.uploadFile(uploadPath, datefolder, item_img_upload);
		
		dto.setItem_up_folder(datefolder); //업로드되는 날짜 폴더명
		dto.setItem_img(saveFileName); // 실제업로드 된 파일 이름
		
		//2)상품테이블 데이타삽입 .
		adProductService.pro_insert(dto);
				
		return "redirect:/admin/product/pro_list";
	}
	
	// 상품등록시 CKEditor에서 사용하는 상품설명이미지 업로드작업
	// ckeditor 에서 사용하는 업로드. 클라이언트에서 보낸 파라미터명과 스프링에서 받는 파라미터명이 동일해야 한다.(규칙)
	@PostMapping("/imageupload")
	public void imageupload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) throws Exception {
		
	
		// 데이타를 바이트단위로 작업하는 출력스트림의 최상위클래스(추상)
		OutputStream out = null;
		PrintWriter printWriter = null; // 서버에서 클라이언트에게 응답정보를 보낼때 사용(업로드 파일정보)
		
		// 예외처리문법
		try {
			//1)CKeditor를 이용한 파일업로드 작업.
			String fileName = upload.getOriginalFilename(); // 클라이언트에서 업로드 파일명.   예>abc.gif
			byte[] bytes = upload.getBytes(); // 업로드하는 파일(abc.gif)을 나타내는 바이트배열
			
			// C:\\Dev\\upload\\ckeditor\\abc.gif
//			String ckUploadPath = uploadCKPath + "\\" + fileName;
//			ckUploadPath = ckUploadPath.replace("\\", File.separator);
			
			String realUploadPath = uploadCKPath + File.separator + fileName;
			
			//스트림 out객체생성이 되면, 해당 경로에 파일은 생성된다. 파일크기는 0byte
			out = new FileOutputStream(realUploadPath);
			
			out.write(bytes); // out스트림객체에 파일 byte배열을 채웠다.
			out.flush(); //out스트림객체에 존재하고 있는 byte배열을 빈파일에 쓰는 작업.
			
			//2)업로드한 파일정보를 클라이언트인 CKEditor로 보내주는 작업.
			// printWriter : 파일정보를 클라이언트쪽에 보낼때 사용하는 객체.
			printWriter = response.getWriter();
			
			// 매핑주소
			String fileUrl = "/admin/product/display/" + fileName;
			
			// ckeditor.js 4.12에서 파일정보를 아래와 같이 작업을 하도록 가이드
			// 파일정보를 JSON 데이타표현 형식 {"filename" : "abc.gif","uploaded":1,"url":"/display/abc.gif"} 
			printWriter.println("{\"filename\" :\"" + fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}"); // 스트림에 채움.
				
			printWriter.flush();
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			
			// 객체소멸은 객체생성의 역순으로 close()작업해준다.(이론)
			// out, printWriter 객체는 순서의 의미는 없다.
			if(out != null) {
				try {
					out.close(); // 메모리 소멸
				}catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
			if(printWriter != null) printWriter.close(); // 메모리 소멸
		}
	}
	
	//CKEditor에서 업로드된후 보여주는 기능
	// 이미지파일을 CKEditor를 통하여 화면에 출력하기
	@GetMapping("/display/{fileName}")
	public ResponseEntity<byte[]> getFile(@PathVariable("fileName") String fileName) {
		ResponseEntity<byte[]> entity = null;
		
		try {
			entity = FileUtils.getFile(uploadCKPath, fileName);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		
		return entity;
		
	}

	@GetMapping("/pro_list")
	public void pro_list(SearchCriteria cri, Model model) throws Exception {
	    cri.setPerPageNum(Constants.ADMIN_PRODUCT_LIST_COUNT);
		//1)상품목록
		List<ProductDTO> pro_list = adProductService.pro_list(cri);
		
		// 날짜폴더의 \를 /로 변환하는 작업
	
		
		model.addAttribute("pro_list", pro_list); // 타임리프 페이지서 사용이 가능
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setDisplayPageNum(Constants.ADMIN_PRODUCT_LIST_COUNT);
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adProductService.getTotalCount(cri));
		
		model.addAttribute("pageMaker", pageMaker); 
		
		model.addAttribute("cate_list", adCategoryService.getFirstCategoryList());
		
	}
	// 상품목록 이미지출력하기.. 클라이언트에서 보낸 파라미터명 스프링의 컨트롤러에서 받는 파라미터명이 일치해야 한다.
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	
}
