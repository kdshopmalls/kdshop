package com.kd.basic.admin.member;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.common.dto.ProductDTO;
import com.kd.basic.common.utils.SearchCriteria;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/admin/member/*")
@RequiredArgsConstructor
@Slf4j
@Setter
@Getter
@Controller
public class AdMemberController {
	private final AdMemberService adMemberService;

	@GetMapping("/mem_list")
	public void mem_list(SearchCriteria cri, Model model) throws Exception {
		log.info("회원정보: " + cri);
		List<MemberDTO> mem_list = adMemberService.mem_list(cri);
		
		model.addAttribute("mem_list" , mem_list );
		
	}
}
