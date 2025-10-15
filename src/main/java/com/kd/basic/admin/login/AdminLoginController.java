package com.kd.basic.admin.login;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kd.basic.admin.statistics.StatisticsService;

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
	private final StatisticsService statisticsService;
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
	@GetMapping("/ad_menu")
	public void ad_menu(Model model) throws Exception{
		model.addAttribute("monthlyCount", statisticsService.getMonthlyCount());
		model.addAttribute("monthlySalesTotal", statisticsService.getMonthlySalesTotal());
		model.addAttribute("totalProductCount", statisticsService.getTotalProductCount());
		model.addAttribute("soldOutProductCount", statisticsService.getSoldOutProductCount());
		List<Map<String, Object>> monthlyOrderCategoryStats = statisticsService.getMonthlyOrderCategoryStats();

	    List<String> labels1 = monthlyOrderCategoryStats.stream()
	            .map((Map<String, Object> stat) -> (String) stat.get("primary_category_name"))
	            .collect(Collectors.toList());

	    List<Integer> data1 = monthlyOrderCategoryStats.stream()
	            .map((Map<String, Object> stat) -> {
	                Object value = stat.get("total_orders");
	                if (value instanceof Number) {
	                    return ((Number) value).intValue();
	                } else {
	                    return 0;
	                }
	            })
	            .collect(Collectors.toList());

	    model.addAttribute("labels1", labels1);
	    model.addAttribute("data1", data1);
	    
	    /*             */
	    
	    List<Map<String, Object>> thisWeekOrderAmount = statisticsService.getThisWeekOrderAmount();

	    List<String> labels2 = thisWeekOrderAmount.stream()
	            .map((Map<String, Object> stat) -> String.valueOf(stat.get("order_date")))
	            .collect(Collectors.toList());

	    List<Integer> data2 = thisWeekOrderAmount.stream()
	            .map((Map<String, Object> stat) -> {
	                Object value = stat.get("daily_total_price");
	                if (value instanceof Number) {
	                    return ((Number) value).intValue();
	                } else {
	                    return 0;
	                }
	            })
	            .collect(Collectors.toList());

	    model.addAttribute("labels2", labels2);
	    model.addAttribute("data2", data2);
		
	}	
}



	
