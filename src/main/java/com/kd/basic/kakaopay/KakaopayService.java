package com.kd.basic.kakaopay;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KakaopayService {
	@Value("${readUrl}")
	private String readUrl;
	
	@Value("${approveUrl}")
	private String approveUrl;
	
	@Value("${secretKey}")
	private String secretKey;
	
	@Value("${cid}")
	private String cid;
	
	@Value("${approval}")
	private String approval;
	
	@Value("${cancel}")
	private String cancel;
	
	@Value("${fail}")
	private String fail;
	
	String tid;
	String  partner_order_id;
	String  partner_user_id;
	
	public ReadyResponse ready(
			String partner_order_id,
			String partner_user_id,
			String item_name,
			Integer quantity,
			Integer total_amount,
			Integer tax_free_amount
			) {
		//1)header
		HttpHeaders headers = getHttpHeaders();
		//2)body  ReadyRequest 클래스가 빌더패텅이  적용되어 아래 구문을 사용할수 잇다
		
				ReadyRequest readyRequest = ReadyRequest.builder()
						.cid(cid)
						//커트롤러에서 전달 받는 작업
						.partner_order_id(partner_order_id)  // 주문번호
						.partner_user_id(partner_user_id)    // 아이디
						.item_name(item_name)                // 상품이름
						.quantity(quantity)                  // 수량
						.total_amount(total_amount)          // 전체금액
						.tax_free_amount(tax_free_amount)    // 부가세 0
						
						 
						.approval_url(approval)  // 결제성공 주소
						.cancel_url(cancel)    // 결제취소 주소
						.fail_url(fail)      // 결제실패 주소
						.build();
		//1)header + 2)body 를 클래스 HttpEntity 객체로 생성한다 
		HttpEntity<ReadyRequest> entityMap = new HttpEntity<ReadyRequest>(readyRequest, headers);
		//결제준비 요청 

		ResponseEntity<ReadyResponse> response = new RestTemplate().postForEntity(readUrl, entityMap, ReadyResponse.class);
		//응답데이터
		ReadyResponse readyResponse = response.getBody();
				
		//2차 결제요청에서 사용하기 위한 준비

		tid = readyResponse.getTid();
		this.partner_order_id = partner_order_id;
		this.partner_user_id = partner_user_id;
		return readyResponse;				
		
	}
	//2차 결제승인 요청 작업(approve)
	public boolean approve(String pg_token) { 
		//1)header
		HttpHeaders headers = getHttpHeaders();
		//2)body 
		ApproveRequest approveRequest = ApproveRequest.builder()
				.cid(cid)
				.tid(tid) // 1차 결제준비요청에  의하여  받는 파라미터 값
				.partner_order_id(partner_order_id) // 1차 결제준비요청에  보낸 동일한  값을 다시 보내야 한다
				.partner_user_id(partner_user_id) // 1차 결제준비요청에  보낸 동일한  값을 다시 보내야 한다
				.pg_token(pg_token)
				.build();
		
		//2차 승인 요청에 보낼 정보 
		HttpEntity<ApproveRequest> entityMap = new HttpEntity<>(approveRequest, headers);
		
		//결제 승인요청 
		ResponseEntity<ApproveResponse> response =new RestTemplate().postForEntity(approveUrl, entityMap, ApproveResponse.class);
		
		if(response.getStatusCode() == HttpStatus.OK) {
			return true;
		}else {
			return false;
		}

	}
	public HttpHeaders getHttpHeaders() {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "SECRET_KEY " + secretKey);
		headers.set("Content-type", "application/json;charset=utf-8");
		return headers;
	}
}
