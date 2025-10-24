package com.kd.basic.member;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kd.basic.common.dto.MemberDTO;
import com.kd.basic.kakaologin.KakaoLoginDto;
import com.kd.basic.kakaologin.KakaoLoginService;
import com.kd.basic.mail.LoginDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member/*")
@Controller
public class MemberController {

	private final MemberService memberService;
	private final PasswordEncoder passwordEncoder;
	private final KakaoLoginService kservice;
	
	@Value("${kakao.authorize}")
	private String authorize;
	
	@Value("${kakao.client_id}")
    private String client_id;

    @Value("${kakao.redirect_uri}")
    private String redirect_uri;
    
    @Value("${kakao.client_secret}")
    private String client_secret;

	@GetMapping("/join")
	public void join() {

	}

	// 아이디 중복체크 ajax요청
	@GetMapping("/idCheck")
	public ResponseEntity<String> idCheck(String mbsp_id) throws Exception {
		ResponseEntity<String> entity = null;
		log.info("아이디: " + mbsp_id);
		String isUse = "";
		if (memberService.idCheck(mbsp_id) != null) {
			isUse = "no"; // 아이디 사용불가능
		} else {
			isUse = "yes"; // 아이디 사용가능
		}

		entity = new ResponseEntity<String>(isUse, HttpStatus.OK);
		return entity;

	}

	// 회원정보저장
	@PostMapping("/join")
	public String join(MemberDTO dto) {
		log.info("회원정보: " + dto);

		// 비밀번호 암호화작업
		String enc_password = passwordEncoder.encode(dto.getMb_pw());
		dto.setMb_pw(enc_password);
		memberService.join(dto);
		return "redirect:/member/login";
	}
	/*
	@GetMapping("/login")
	public void loginForm() {

	}
	*/
	@GetMapping("/login")
	public void loginForm(Model model) {
		  String location = authorize + "?response_type=code&client_id="+client_id+"&redirect_uri="+redirect_uri + "&prompt=login";
	      model.addAttribute("location", location);
	}
	

	// 로그인 처리.
	@PostMapping("/login")
	public String loginProcess(LoginDTO dto, HttpSession session, RedirectAttributes rttr) throws Exception {
		// 아이디가 존재하면 vo객체안에서 아이디에 해당하는 회원정보가 저장된다.
		// 아이디가 존재하지 않으면 vo객체는 null이 된다
		MemberDTO vo = memberService.login(dto.getMb_id());
		String url = "";// 로그인지 주소이동
		String status = "";// 로그인 성공 ,실패에 따른 메세지용
		if (vo != null) {
			log.info("회원아이디: " + vo);
			if (passwordEncoder.matches(dto.getMb_pw(), vo.getMb_pw())) {
				
				memberService.lastlogin(vo.getMb_id());
				
				// 서버측의 메모리에 인증된 상태라는 의미의 정보를 세션형태로 저장한다.
				vo.setMb_pw("");// 비밀번호를 공백처리
				session.setAttribute("login_auth", vo);

				url = "/"; // 메인페이지 주소
				status = "success";
			} else {

				url = "/member/login"; // 비밀번호가 틀려서 다시 로그인 주소로 이동
				status = "pwFail";
			}
		} else {
			url = "/member/login"; // 아이디가 틀려서 다시 로그인 주소로 이동
			status = "idFail";
		}
		rttr.addFlashAttribute("status", status); // 리다이렉트 페이지에서 출력
		return "redirect:" + url;
	}
	// 로그인한 상태에서 접근하는 기능의 주소에 해당하는 메소드는 HttpSession session 매개변수로 가지고 있어야 한다.
	// HttpSession session 매개변수가 필요하는 하는 기능 - 예> 로그아웃,회원수정, 마이페이지,비밀번호 변경하기 등등

	// 로그아웃
	/*
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); // 세션으로 관리되는 서버측의 모든 메모리 소멸

		return "redirect:/"; // 메인 주소로 이동
	}
	*/
	
	@GetMapping("/logout")
	public String logout(HttpSession session) throws JsonProcessingException {
		
	
		String kakao_login_auth =  (String) session.getAttribute("kakao_login_auth");
		
		if(kakao_login_auth != null && !"".equals(kakao_login_auth)) {
			String accessToken =  (String) session.getAttribute("accessToken");
			
			// 카카오 로그아웃 API 호출
			kservice.kakaologout(accessToken);
			
			// 카카오 로그인 인증정보를 세션으로 소멸
			session.removeAttribute("kakao_login_auth");
			session.removeAttribute("accessToken");

		}
		// 현재 프로젝트에서 일반로그인상태를 로그아웃 처리.
		session.invalidate();
		
		
		return "redirect:/";  // 메인주소로 이동.
	}

	// 회원수정 폼. select문 회원정보를 읽어오기.
	@GetMapping("/modify")
	public String modify(HttpSession session, Model model) throws Exception {
		String url = "";
		log.info("modify 호출");
		MemberDTO dto = (MemberDTO) session.getAttribute("login_auth");
		if (dto != null) {
			// 로그인시 저장한 구문. session.setAttribute("login_auth", userInfo);
			String mb_id = ((MemberDTO) session.getAttribute("login_auth")).getMb_id();

			MemberDTO vo = memberService.modify(mb_id);

			log.info("회원수정정보" + vo);

			model.addAttribute("memberDTO", vo);
			url = "/member/modify";
		} else {
			url = "redirect:/member/login";

		}
		return url;
	}

	// 회원정보수정
	@PostMapping("/modify")
	public String modify(MemberDTO dto, RedirectAttributes rttr) throws Exception {
		log.info("회원수정데이타" + dto);
		memberService.modify_save(dto);
		rttr.addFlashAttribute("status", "modify");
		return "redirect:/";
	}
	
	// 카카오 로그인 API서버에서 호출받게되는 주소.
	// 카카오 디벨로퍼 사이트에서 로그인작업을 위하여 미리 설정해둠.
	@GetMapping(value="/kakao/callback")
    public String loginKakaoRedirect(KakaoLoginDto dto, MemberDTO mdto, Model model, HttpSession session) throws Exception {
    	
		// code 라는 파라미터명으로 토큰요청인가코드를 보내준다.
		// cIZpq5QRFhTLQKoijLXd3Z4oKJGTveb1kyedVAccWbEb_GJohpQ9vAAAAAQKDRSjAAABmaPlSxNtZc76WqiBKA
		System.out.println("토큰요청인가코드: "+dto.getCode());
		
    	
    	
    	// 인증토큰 받기 
    	String accessToken = kservice.getAccessTokenFromKakao(client_id, dto.getCode(), redirect_uri, client_secret);
    	
    	// 카카오서버로부터 개인회원정보 받기
    	dto = kservice.getUserInfo(accessToken, dto);
    	
    	log.info("KakaoLoginDto: " + dto);
		  
    	// 회원존재확인
    		
		/*
    	if(kservice.kakaoOne(dto) != null) {
			//by pass
		}else {
			kservice.kakaoInsert(dto);
		}
		KakaoLoginDto rtId = kservice.kakaoOne(dto);
		httpSession.setAttribute("sessNameUsr", rtId.getMemberName());
		httpSession.setAttribute("sessSeqUsr", rtId.getMemberSeq());

        model.addAttribute("info", dto);
        */
    	
    	// 카카오 로그인 인증정보저장.
    	session.setAttribute("kakao_login_auth", dto.getEmail()); // 일반 로그인상태인지 카카오로그인상태인지 구분
    	session.setAttribute("accessToken", accessToken); // 인증토큰을 카카오 로그아웃에 사용하기위하여 세션으로 저장.
    	
        
        return "redirect:/";
    }
	
	
	
	@GetMapping("/mypage")
	public String mypage(HttpSession session, Model model) {
		String url = "";
		
		MemberDTO dto = (MemberDTO) session.getAttribute("login_auth");
		if (dto != null) {
			MemberDTO member = memberService.mypage(dto.getMb_id());
			model.addAttribute("memberDTO", member);
			url = "/member/mypage";
		} else {
			url = "redirect:/member/login";		}
		
		return url;
	}
}
