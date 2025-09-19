package com.kd.basic.review;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/review/*")
public class ReviewController {
	private final ReviewService reviewService;
	
	@GetMapping("/rev_list/{item_num}/{page}")
	public ResponseEntity<Map<String, Object>> rev_list(@PathVariable("item_num") Integer item_num,@PathVariable("page") int page) throws Exception{
		ResponseEntity<Map<String, Object>> entity =null;
		entity = new ResponseEntity<Map<String,Object>>(reviewService.getReiviewList(item_num,page),HttpStatus.OK);
		return entity;
	}
}
