package com.kd.basic.order;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.cart.CartService;
import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.member.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/order/*")
@Slf4j
@Controller
public class OrderController {
	
	private final OrderService orderService;
	private final CartService cartService;
	private final MemberService memberService;
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	@GetMapping("/order_info")
	public void order_info(HttpSession session ,Model model) throws Exception{
		String mb_id =((MemberDTO) session.getAttribute("login_auth")).getMb_id();
		//주문상품정보 
		List<Map<String, Object>> orderDetails = cartService.cart_list(mb_id);
		model.addAttribute("orderDetails", orderDetails);
		
		//주문자정보 
		MemberDTO memberDTO = memberService.modify(mb_id);
		model.addAttribute("memberDTO", memberDTO);
	}
	
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}

}
