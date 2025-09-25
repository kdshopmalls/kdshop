package com.kd.basic.admin.review;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/admin/review/*")
@RequiredArgsConstructor
public class AdminReviewController {

	private final AdminReviewService adminReviewService;
	
	@GetMapping("/review_list")
	public void review_list(@ModelAttribute("cri") SearchCriteria cri,@ModelAttribute("rev_rate") String rev_rate,@ModelAttribute("rev_content") String rev_content) throws Exception {
		
	}
	
	
}
