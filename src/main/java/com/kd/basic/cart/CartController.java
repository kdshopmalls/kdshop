package com.kd.basic.cart;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.common.domain.MemberDTO;
import com.kd.basic.common.utils.FileUtils;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/cart/*")
@Slf4j
@Controller
public class CartController {

	private final CartService cartService;
	
	@Value("${com.kd.upload.path}")
	private String uploadPath;
	
	@Value("${com.kd.upload.ckeditor.path}")
	private String uploadCKPath;
	
	@PostMapping("/cart_add")
	public ResponseEntity<String> cart_add(CartDTO dto, HttpSession session ) throws Exception {
		ResponseEntity<String> entity = null;
		String mb_id =((MemberDTO) session.getAttribute("login_auth")).getMb_id();
		dto.setMb_id(mb_id);
		cartService.cart_add(dto);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		return entity;
	}
	
	//장바구니 목록
	@GetMapping("/cart_list")
	public void cart_list(HttpSession session, Model model ) throws Exception{
		String mb_id =((MemberDTO) session.getAttribute("login_auth")).getMb_id();
		List<Map<String, Object>> cart_list = cartService.cart_list(mb_id) ;
		
		model.addAttribute("cart_list", cart_list);
		model.addAttribute("cart_total_price", cartService.getCartTotal(mb_id));
		
	}
	
	@GetMapping("/image_display")
	public ResponseEntity<byte[]> image_display(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + File.separator + dateFolderName, fileName);
	}
	

	
}
