package com.kd.basic.admin.order;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.admin.payment.AdminPaymentService;
import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.dto.PaymentDTO;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.common.utils.PageMaker;
import com.kd.basic.common.utils.SearchCriteria;
import com.kd.basic.member.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/order/*")
@Controller
public class AdminOrderController {
	private final AdminOrderService adminOrderService;
	private final AdminPaymentService adminPaymentService;
	
	private final MemberService memberService;
	
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	@Value("${com.kd.upload.ckeditor.path}")
	private String uploadCKPath;
	
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
	
	//주문상세 보기
	@GetMapping("orderdetail_info")
	public void orderdetail_info(Integer or_code,Model model ) throws Exception{
		//1) 주문상세내역
		 List<Map<String, Object>> orderdetail_info = adminOrderService.orderdetail_info(or_code);
		 model.addAttribute("order_product_info", orderdetail_info);
		 
		 //2)주문결제내역
		 PaymentDTO payment_info = adminPaymentService.payment_info(or_code);
		 model.addAttribute("payment_info", payment_info);
		//4)배송지 정보
		 OrderDTO order_info = adminOrderService.order_info(or_code);
		 model.addAttribute("order_info", order_info);
		 
		 //3)주문자정보
		 String mb_id = order_info.getMb_id();
		 MemberDTO memberDTO = memberService.login(mb_id);
		 
		 model.addAttribute("member_info", memberDTO);
	}
	//주문내역 개별 상품삭제
	@PostMapping("/order_product_del")
	public ResponseEntity<String> order_product_del(Integer or_code,Integer item_num,int unitprice) throws Exception{
		ResponseEntity<String> entity =null;

		//주문내역 일부 삭제 
		adminOrderService.order_product_del(or_code, item_num, unitprice);
		entity = new ResponseEntity<String>( "success", HttpStatus.OK);
		return entity;
	}
	
	//관리자가  주문메세지 저장(수정) 
	@PostMapping("/admin_order_message")
	public ResponseEntity<String> admin_ord_message(Integer or_code,String or_message) throws Exception{
		ResponseEntity<String> entity =null;
		//주문메세지수정
		adminOrderService.admin_order_message(or_code, or_message);
		entity = new ResponseEntity<String>( "success", HttpStatus.OK);
		return entity ;
	}
	
	//배송지정보 수정
	@PostMapping("/order_info_edit")
	public ResponseEntity<String> order_info_edit(OrderDTO dto) throws Exception{
		ResponseEntity<String> entity = null;
		adminOrderService.order_info_edit(dto);
		entity = new ResponseEntity<String>( "success", HttpStatus.OK);
		//배송지정보수정 작업
		return entity;
	}
	
	
	// 상품목록 이미지출력하기.. 클라이언트에서 보낸 파라미터명 스프링의 컨트롤러에서 받는 파라미터명이 일치해야 한다.
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}

}
