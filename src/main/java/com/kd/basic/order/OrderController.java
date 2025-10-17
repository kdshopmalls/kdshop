package com.kd.basic.order;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kd.basic.cart.CartDTO;
import com.kd.basic.cart.CartService;
import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.dto.OrderDTO;
import com.kd.basic.common.utils.FileUtils;
import com.kd.basic.mail.EmailDTO;
import com.kd.basic.mail.EmailService;
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
	private final EmailService emailService;
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	
	//장바구니,바로구매 요청받앗는지 구분하기 위하여 ,type값이 cart or buy
	@GetMapping("/order_info")
	public void order_info(HttpSession session,CartDTO dto, String type, Model model) throws Exception{
		String mb_id =((MemberDTO) session.getAttribute("login_auth")).getMb_id();
		dto.setMb_id(mb_id);
		//바로구매 type 이 buy 이면 장바구니테이블에 저장
		if(type.equals("buy")) {
			cartService.cart_add(dto);
		}
		//주문상품정보 
		List<Map<String, Object>> orderDetails = cartService.cart_list(mb_id);
		model.addAttribute("orderDetails", orderDetails);
		//주문상품 이름,수량: 카카오페이 결제를 위한 정보
		String item_name="";
		if(orderDetails.size()==1) {
			item_name=(String) orderDetails.get(0).get("item_name");
		}else {
			item_name=(String) orderDetails.get(0).get("item_name")+ "외" + (orderDetails.size()-1)+"rjs";
		}
		model.addAttribute("item_name", item_name);
		model.addAttribute("quantity", orderDetails.size()) ;
		
		model.addAttribute("order_total_price", cartService.getCartTotal(mb_id));
				
		//주문자정보 
		MemberDTO memberDTO = memberService.modify(mb_id);
		model.addAttribute("memberDTO", memberDTO);
	}
	//주문결제 저장및 결제하기
	@PostMapping("/order_save")
	public String order_save(OrderDTO dto , String p_method, String account_transfer, String sender,  HttpSession session,RedirectAttributes rttr) throws Exception{
		String mb_id =((MemberDTO) session.getAttribute("login_auth")).getMb_id();
		dto.setMb_id(mb_id);
		log.info("주문정보" +dto);
		orderService.order_process(dto,p_method,account_transfer,sender);
		rttr.addAttribute("or_code", dto.getOr_code());
		rttr.addAttribute("or_mail", dto.getOr_mail());
		
		return "redirect:/order/order_result";
	}
	
	//주문처리결과 
	int order_total_price = 0;
	@GetMapping("/order_result")
	public void order_result(Integer or_code,String or_mail,Model model ) throws Exception {
		List<Map<String, Object>> order_info = orderService.getOrderInfoByOrd_code(or_code);
		model.addAttribute("order_info", order_info);
		
		order_info.forEach(o_info->{
			order_total_price +=(int) o_info.get("dt_amount") * (int) o_info.get("dt_price");
		});
		model.addAttribute("order_total_price", order_total_price);
		//주문내역을 내용으로 메일보내기
		EmailDTO dto = new EmailDTO("KDMail", "KDMail", or_mail, "주문내역", "주문내역");
		emailService.sendMail("mail/orderConfirmation", dto, order_info,order_total_price); 
		model.addAttribute("order_total_price", order_total_price);
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	
	// 주문 목록 페이지
	@GetMapping("/order_list")
	public void order_list(HttpSession session, Model model) throws Exception {
	    String mb_id = ((MemberDTO) session.getAttribute("login_auth")).getMb_id();
	    
	    List<OrderDTO> orderList = orderService.getOrderListByMemberId(mb_id);
	    model.addAttribute("orderList", orderList);
	}

	// 주문 상세 페이지
	@GetMapping("/order_detail")
	public void order_detail(Integer or_code, Model model) throws Exception {
	    // 주문 기본 정보
	    List<Map<String, Object>> orderInfo = orderService.getOrderInfoByOrd_code(or_code);
	    model.addAttribute("order_info", orderInfo);
	    
	    // 주문 상세 상품 정보
	    List<Map<String, Object>> orderDetails = orderService.getOrderDetailsByOrCode(or_code);
	    model.addAttribute("orderDetails", orderDetails);
	    
	    // 주문 총액 계산
	    int order_total_price = 0;
	    for(Map<String, Object> detail : orderDetails) {
	        order_total_price += (int) detail.get("dt_total_price");
	    }
	    model.addAttribute("order_total_price", order_total_price);
	}

}
