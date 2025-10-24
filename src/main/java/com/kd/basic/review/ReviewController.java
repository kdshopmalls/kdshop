package com.kd.basic.review;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.dto.ReviewDTO;
import com.kd.basic.product.ProductService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/review/*")
public class ReviewController {
	private final ReviewService reviewService;
	private final ProductService productService; 
	@GetMapping("/rev_list/{item_num}/{page}")
	public ResponseEntity<Map<String, Object>> rev_list(@PathVariable("item_num") Integer item_num,@PathVariable("page") int page) throws Exception{
		ResponseEntity<Map<String, Object>> entity =null;
		entity = new ResponseEntity<Map<String,Object>>(reviewService.getReiviewList(item_num,page),HttpStatus.OK);
		return entity;
	}
	
	//상품후기 저장 
	@PostMapping(value = "/review/review_save", consumes="application/json", produces = {MediaType.TEXT_PLAIN_VALUE} )
		public ResponseEntity<String> review_save(@RequestBody ReviewDTO dto, HttpSession session) throws Exception{
		ResponseEntity<String> entity = null;
		String mb_id = ((MemberDTO) session.getAttribute("login_auth")).getMb_id();
		dto.setMb_id(mb_id);
		log.info("상품후기"+ dto);
		reviewService.review_save(dto);
		int review_count = productService.getProductReviewCountByItem_num(dto.getItem_num());

		entity = new ResponseEntity<String>( String.valueOf(review_count), HttpStatus.OK);
		return entity;
	}
	
	// 상품후기삭제
	@DeleteMapping("/review_delete/{rev_code}")
	public ResponseEntity<String> review_delete(@PathVariable("rev_code") Integer rev_code) throws Exception{
		ResponseEntity<String> entity = null;
		reviewService.review_delete(rev_code);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;
	}
	//상품후기 수정폼 
	@GetMapping(value = "/review_info/{rev_code}")
	public ResponseEntity<ReviewDTO> review_info(@PathVariable("rev_code") Integer rev_code) throws Exception{
		log.info("후기코드 : " +rev_code);
		ResponseEntity<ReviewDTO> entity = null;
		
		entity = new ResponseEntity<ReviewDTO>(reviewService.review_info(rev_code),  HttpStatus.OK);
		
		return entity;
	}
	//상품후기 수정
	@PutMapping(value = "/review_modify", consumes="application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_modify(@RequestBody ReviewDTO dto) throws Exception{
		ResponseEntity<String> entity = null;
		reviewService.review_modify(dto);
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;

	}
}
