package com.kd.basic.admin.login;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/*")
@Controller
public class AdminLoginController {
	
	private final AdminLoginService adminLoginService;
	private final PasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public String ad_login() {
		return "/admin/ad_login";
	}
	
	@PostMapping("/admin_ok")
	public String admin_ok(AdminLoginDTO dto, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		AdminLoginDTO vo = adminLoginService.admin_ok(dto.getAd_id());
		
		String url = "";
		String msg = "";
		
		if(vo != null) {
			if(passwordEncoder.matches(dto.getAd_pw(), vo.getAd_pw())) {
				vo.setAd_pw("");
				session.setAttribute("admin_auth", vo);
				
				url = "/admin/ad_menu";
				msg = "success";
			}else {
				url = "/admin/";
				msg = "pwfail";
			}
		}else {
			url = "/admin/";
			msg = "idfail";
		}
		
		// 2번째 msg변수의 내용을 타임리프레이지에 msg으로 사용하게 하는 구문.
		rttr.addFlashAttribute("msg", msg);
			
		return "redirect:" + url;
	}
	@GetMapping("ad_menu")
	public String menu() {
		
		return "admin/ad_menu";
	}
	
		
}



	
