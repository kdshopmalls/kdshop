package com.kd.basic.admin.order;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.common.utils.PageMaker;
import com.kd.basic.common.utils.SearchCriteria;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/order/*")
@Controller
public class AdminOrderController {
	private final AdminOrderService adminOrderService;
	
	@GetMapping("/order_list")
	public void order(@ModelAttribute("cri") SearchCriteria cri,
			@ModelAttribute("period") String period, @ModelAttribute("start_date") String start_date,
			@ModelAttribute("end_date") String end_date,  
			@ModelAttribute("pay_method") String pay_method,
			@ModelAttribute("or_status") String or_status, 
			@ModelAttribute("pay_status") String pay_status, 
			Model model ) throws Exception{
		
		log.info(period + "/" + start_date + "/" + end_date);
		cri.setPerPageNum(2);
		List<Map<String, Object>> order_list = adminOrderService.order_list(cri, period, start_date, end_date, pay_method, or_status, pay_status);
		
		model.addAttribute("order_list", order_list);
		
		int totalcount = adminOrderService.getTotalcount(cri, period, start_date, end_date, pay_method, or_status, pay_status);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(totalcount);
		
		model.addAttribute("pageMaker", pageMaker);
	}
	
	
	//주문상태편경
	@PostMapping("/order_status_change")
	public ResponseEntity<String> order_status_change(Integer or_code,String or_status) throws Exception{
		ResponseEntity<String> entity =null;
		adminOrderService.order_status_change(or_code, or_status);
		entity =new ResponseEntity<String>("success",HttpStatus.OK);
		return entity;
	}

}
