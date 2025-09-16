package com.kd.basic.kakaopay;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.order.OrderService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/kakao/*")
public class KakaopayController {
	private final KakaopayService kakaopayService;
	private final OrderService orderService;
	private OrderDTO dto;
	
	@PostMapping("kakaopay") //1차결제
	public ResponseEntity<ReadyResponse> kakaopay(OrderDTO dto, String item_name,int quantity, HttpSession session ){
	
		String mb_id =((MemberDTO) session.getAttribute("login_auth")).getMb_id();
		dto.setMb_id(mb_id);
		this.dto = dto;
		ResponseEntity<ReadyResponse> entity = null;
		
		String partner_order_id = "KDMall-" + mb_id + "-" + new Date().toString();
		ReadyResponse readyResponse = kakaopayService.ready(partner_order_id, mb_id, item_name, quantity, dto.getOr_price(), 0);
		
		entity = new ResponseEntity<ReadyResponse>(readyResponse,HttpStatus.OK);
		
		return entity;
		
	}
	
	//카카오페이 API에 아래 매핑주소정보를 제공.
	//결제승인 및 성공 매핑주소
	@GetMapping("/approval")
	public String approval(String pg_token, RedirectAttributes rttr) {
		log.info("PG_토큰:"+ pg_token );
		String url = "";
		boolean isApprove = kakaopayService.approve(pg_token);
		if(isApprove == true) {
			orderService.order_process(dto, "kakaopay", pg_token, null);
			rttr.addAttribute("or_code", dto.getOr_code());
			rttr.addAttribute("or_mail", dto.getOr_mail());
			url = "/order/order_result";
		}else {
			url = "/order/order_falil_result";
		}
		
		
		return "redirect:" + url;
		
	}
	
	//결제취소 매핑주소
	@GetMapping("/cancel")
	public String cancel() {
		return "/order/order_cancel";
	}
	
	//결재실패 매칭주소
	@GetMapping("/fail")
	public String fail() {
		return "/order/order_fail";
	}

}
