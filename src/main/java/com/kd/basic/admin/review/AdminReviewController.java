package com.kd.basic.admin.review;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.admin.login.AdminLoginDTO;
import com.kd.basic.common.dto.ReviewDTO;
import com.kd.basic.common.dto.ReviewReplyDTO;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.common.utils.PageMaker;
import com.kd.basic.common.utils.SearchCriteria;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/admin/review/*")
@Controller
public class AdminReviewController {
	
	private final AdminReviewService adminReviewService;
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	@GetMapping("/review_list")
	public void review_list(@ModelAttribute("cri") SearchCriteria cri, @ModelAttribute("rev_rate") String rev_rate, @ModelAttribute("rev_content") String rev_content, Model model) throws Exception {
		
		List<ReviewDTO> review_list = adminReviewService.review_list(cri, rev_rate, rev_content);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(adminReviewService.review_count(cri, rev_rate, rev_content));
		model.addAttribute("pageMaker", pageMaker);
		
		model.addAttribute("review_list", review_list);
	}	
	
	@GetMapping("/review_info/{rev_code}")
	public ResponseEntity<Map<String, Object>> review_info(@PathVariable("rev_code") Integer rev_code) throws Exception{
		ResponseEntity<Map<String, Object>> entity = null;
		entity = new ResponseEntity<Map<String, Object>>(adminReviewService.review_info(rev_code), HttpStatus.OK);
		
		return entity;
	}
	
	@PostMapping(value = "/reply_insert",  consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> reply_insert(@RequestBody ReviewReplyDTO dto, HttpSession session) throws Exception{
		 ResponseEntity<String> entity = null;
		 AdminLoginDTO adminLoginDTO= (AdminLoginDTO) session.getAttribute("admin_auth");
		 dto.setManager_id(adminLoginDTO.getAd_id());
		 
		 adminReviewService.reply_insert(dto);
		 entity = new ResponseEntity<String>("success", HttpStatus.OK);
				
		 return entity;
	}
	
	// 상품후기 답변수정
	@PostMapping("/reply_modify")
	public ResponseEntity<String> reply_modify(ReviewReplyDTO dto) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		adminReviewService.reply_modify(dto);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;
	}
	
	// 상품후기 답변삭제
	@DeleteMapping("/reply_delete/{reply_id}")
	public ResponseEntity<String> reply_delete(@PathVariable("reply_id") Integer reply_id) throws Exception {
		ResponseEntity<String> entity = null;
		
		adminReviewService.reply_delete(reply_id);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;		
	}
	
	
	// 상품목록 이미지출력하기.. 클라이언트에서 보낸 파라미터명 스프링의 컨트롤러에서 받는 파라미터명이 일치해야 한다.
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	

}
